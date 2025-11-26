package edu.charlotte.assignment15.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.charlotte.assignment15.Forum;
import edu.charlotte.assignment15.R;
import edu.charlotte.assignment15.databinding.FragmentForumsBinding;
import edu.charlotte.assignment15.databinding.ForumRowItemBinding;


public class ForumsFragment extends Fragment {

    private FirebaseAuth mAuth;
    ForumsAdapter adapter;
    ArrayList<Forum> forums = new ArrayList<>();

    public ForumsFragment() {
        // Required empty public constructor
    }

    FragmentForumsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForumsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSignout();
            }
        });
        binding.buttonCreateForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddForum();
            }
        });
        adapter = new ForumsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        getData();
    }

    ForumsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ForumsListener) {
            mListener = (ForumsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ForumsListener");
        }
    }

    public interface ForumsListener{
        void gotoAddForum();
        void onSignout();
        void onForumClicked(Forum forum);
    }

    private void getData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forums")
                .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        forums.clear();
                        for (QueryDocumentSnapshot document : value) {
                            Forum forum = document.toObject(Forum.class);
                            forum.setId(document.getId());
                            forums.add(forum);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    class ForumsAdapter extends RecyclerView.Adapter<ForumsAdapter.ForumViewHolder> {

        @NonNull
        @Override
        public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ForumRowItemBinding itemBinding = ForumRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ForumViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
            holder.bind(forums.get(position));
        }

        @Override
        public int getItemCount() { return forums.size(); }

        class ForumViewHolder extends RecyclerView.ViewHolder {
            ForumRowItemBinding itemBinding;

            public ForumViewHolder(ForumRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void bind(Forum forum) {
                itemBinding.textViewForumTitle.setText(forum.getTitle());
                itemBinding.textViewForumCreatedBy.setText(forum.getCreatedBy());
                itemBinding.textViewForumText.setText(forum.getText());

                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if (forum.getUserID().equals(currentUserId)) {
                    itemBinding.imageViewDelete.setVisibility(View.VISIBLE);
                } else {
                    itemBinding.imageViewDelete.setVisibility(View.GONE);
                }

                itemBinding.imageViewDelete.setOnClickListener(v -> {
                    FirebaseFirestore.getInstance()
                            .collection("forums")
                            .document(forum.getId())
                            .delete();
                });

                boolean liked = false;
                if (forum.getLikedBy() != null) {
                    if (forum.getLikedBy().contains(currentUserId)) {
                        liked = true;
                    }
                }
                if (liked) {
                    itemBinding.imageViewLike.setImageResource(R.drawable.like_favorite);
                } else {
                    itemBinding.imageViewLike.setImageResource(R.drawable.like_not_favorite);
                }

                String displayLikesDate = forum.getLikes() + " Likes | " + forum.getLikesAndDate();
                itemBinding.textViewForumLikesDate.setText(displayLikesDate);
                itemBinding.imageViewLike.setOnClickListener(v -> {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    boolean isLiked = false;
                    if (forum.getLikedBy() != null) {
                        if (forum.getLikedBy().contains(currentUserId)) {
                            isLiked = true;
                        }
                    }
                    if (isLiked) {
                        db.collection("forums")
                                .document(forum.getId())
                                .update(
                                        "likes", forum.getLikes() - 1,
                                        "likedBy", com.google.firebase.firestore.FieldValue.arrayRemove(currentUserId)
                                );
                    } else {
                        db.collection("forums")
                                .document(forum.getId())
                                .update(
                                        "likes", forum.getLikes() + 1,
                                        "likedBy", com.google.firebase.firestore.FieldValue.arrayUnion(currentUserId)
                                );
                    }
                });
                itemView.setOnClickListener(v -> {
                    if (mListener != null) mListener.onForumClicked(forum);
                });
            }



        }
    }

}