package edu.charlotte.assignment15.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.charlotte.assignment15.Comment;
import edu.charlotte.assignment15.Forum;
import edu.charlotte.assignment15.databinding.CommentRowItemBinding;
import edu.charlotte.assignment15.databinding.FragmentForumBinding;

public class ForumFragment extends Fragment {
    private static final String ARG_FORUM = "ARG_FORUM";
    private Forum forum;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ForumAdapter adapter;
    ArrayList<Comment> comments = new ArrayList<>();

    public ForumFragment() {
        // Required empty public constructor
    }

    public static ForumFragment newInstance(Forum forum) {
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FORUM, forum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forum = (Forum) getArguments().getSerializable(ARG_FORUM);
        }
    }

    FragmentForumBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewForumTitle.setText(forum.getTitle());
        binding.textViewForumCreatedBy.setText(forum.getCreatedBy());
        binding.textViewForumText.setText(forum.getText());

        adapter = new ForumAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        db.collection("forums")
                .document(forum.getId())
                .collection("comments")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null) return;

                    comments.clear();
                    for (var document : value.getDocuments()) {
                        Comment comment = document.toObject(Comment.class);
                        comment.setId(document.getId());
                        comments.add(comment);
                    }

                    adapter.notifyDataSetChanged();
                    binding.textViewCommentsCount.setText(comments.size() + " Comments");
                });

        binding.buttonSubmitComment.setOnClickListener(v -> {
            String text = binding.editTextComment.getText().toString();
            if (text.isEmpty()) return;

            Comment comment = new Comment(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                    text,
                    DateFormat.getDateTimeInstance().format(new Date())
            );

            db.collection("forums")
                    .document(forum.getId())
                    .collection("comments")
                    .add(comment);

            binding.editTextComment.setText("");
        });
        binding.buttonBack.setOnClickListener(v -> {
            mListener.goBack();
        });
    }
    class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.CommentViewHolder> {

        @NonNull
        @Override
        public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CommentRowItemBinding itemBinding = CommentRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new CommentViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
            holder.bind(comments.get(position));
        }

        @Override
        public int getItemCount() { return comments.size(); }

        class CommentViewHolder extends RecyclerView.ViewHolder {
            CommentRowItemBinding itemBinding;
            public CommentViewHolder(CommentRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void bind(Comment comment) {
                itemBinding.textViewCommentCreatedBy.setText(comment.getUserName());
                itemBinding.textViewCommentText.setText(comment.getText());
                itemBinding.textViewCommentCreatedAt.setText(comment.getCreatedAt());

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if (comment.getUserId().equals(uid)) {
                    itemBinding.imageViewDelete.setVisibility(View.VISIBLE);
                    itemBinding.imageViewDelete.setOnClickListener(v -> {
                        db.collection("forums")
                                .document(forum.getId())
                                .collection("comments")
                                .document(comment.getId())
                                .delete();
                    });
                } else {
                    itemBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
    ForumListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ForumListener) {
            mListener = (ForumListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ForumListener");
        }
    }

    public interface ForumListener{
        void goBack();
    }
}