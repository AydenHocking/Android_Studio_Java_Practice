package edu.charlotte.assignment10.fragments;

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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.charlotte.assignment10.R;
import edu.charlotte.assignment10.databinding.FragmentUsersBinding;
import edu.charlotte.assignment10.models.User;

public class UsersFragment extends Fragment {
    public UsersFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    UsersRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    FragmentUsersBinding binding;
    ArrayList<User> mUsers = new ArrayList<>();
    private String currentSortCriteria = null;
    private String currentFilterType = null;
    private String currentFilterValue = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Users");
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<User> users = mListener != null ? mListener.getAllUsers() : new ArrayList<>();
        adapter = new UsersRecyclerViewAdapter(users, mListener);
        recyclerView.setAdapter(adapter);
        if (currentSortCriteria != null) {
            applySort(currentSortCriteria);
        }

        binding.imageViewSort.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.gotoSortSelection();
            }
        });

        binding.imageViewFilter.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.gotoFilterSelection();
            }
        });
        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAll();
            }
        });

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddNew();
            }
        });
    }
    public void refreshUsers(ArrayList<User> updatedUsers) {
        if (adapter != null) {
            adapter.setUsers(updatedUsers);
        }
    }

    public void applySort(@Nullable String criteria) {
        currentSortCriteria = criteria;

        if (mListener == null || binding == null) return;

        ArrayList<User> usersToSort;
        if (currentFilterType != null && currentFilterValue != null) {
            usersToSort = new ArrayList<>();
            for (User user : mListener.getAllUsers()) {
                switch (currentFilterType) {
                    case "NAME":
                        if (user.getName().toLowerCase().startsWith(currentFilterValue.toLowerCase())) usersToSort.add(user);
                        break;
                    case "AGE":
                        if (user.getAgeGroup().equals(currentFilterValue)) usersToSort.add(user);
                        break;
                    case "FEELING":
                        if (user.getMood() != null && user.getMood().getName().equals(currentFilterValue)) usersToSort.add(user);
                        break;
                }
            }
        } else {
            usersToSort = new ArrayList<>(mListener.getAllUsers());
        }

        if (criteria == null) {
            refreshUsers(usersToSort);
            binding.textViewSort.setText("No Sort");
            return;
        }

        switch (criteria) {
            case "NAME_ASC":
                Collections.sort(usersToSort, Comparator.comparing(User::getName));
                binding.textViewSort.setText("Name (ASC)");
                break;
            case "NAME_DESC":
                Collections.sort(usersToSort, (a, b) -> b.getName().compareTo(a.getName()));
                binding.textViewSort.setText("Name (DESC)");
                break;
            case "AGE_ASC":
                Collections.sort(usersToSort, Comparator.comparingInt(u -> getAgeGroupOrder(u.getAgeGroup())));
                binding.textViewSort.setText("Age (ASC)");
                break;
            case "AGE_DESC":
                Collections.sort(usersToSort, (a, b) -> getAgeGroupOrder(b.getAgeGroup()) - getAgeGroupOrder(a.getAgeGroup()));
                binding.textViewSort.setText("Age (DESC)");
                break;
            case "FEELING_ASC":
                Collections.sort(usersToSort, Comparator.comparingInt(u -> getMoodOrder(u.getMood() != null ? u.getMood().getName() : null)));
                binding.textViewSort.setText("Feeling(ASC)");
                break;


            case "FEELING_DESC":
                Collections.sort(usersToSort, (a, b) -> getMoodOrder(b.getMood() != null ? b.getMood().getName() : null) - getMoodOrder(a.getMood() != null ? a.getMood().getName() : null)
                );
                binding.textViewSort.setText("Feeling (DESC)");
                break;

        }

        refreshUsers(usersToSort);
        applyFilter(currentFilterType, currentFilterValue);

    }

    public void applyFilter(@Nullable String type, @Nullable String value) {
        currentFilterType = type;
        currentFilterValue = value;

        ArrayList<User> baseList = new ArrayList<>(mListener.getAllUsers());
        ArrayList<User> filteredUsers = new ArrayList<>();

        if (type == null || value == null) {
            filteredUsers.addAll(baseList);
        } else {
            for (User user : baseList) {
                switch (type) {
                    case "NAME":
                        if (user.getName() != null && user.getName().toLowerCase().startsWith(value.toLowerCase())) {
                            filteredUsers.add(user);
                        }
                        break;
                    case "AGE":
                        if (user.getAgeGroup() != null && user.getAgeGroup().equals(value)) {
                            filteredUsers.add(user);
                        }
                        break;
                    case "FEELING":
                        if (user.getMood() != null && user.getMood().getName() != null &&
                                user.getMood().getName().equals(value)) {
                            filteredUsers.add(user);
                        }
                        break;
                }
            }
        }

        refreshUsers(filteredUsers);

        if (binding != null) {
            if (type == null || value == null) {
                binding.textViewFilter.setText("No Filter");
            } else {
                binding.textViewFilter.setText(type + " (" + value + ")");
            }
        }
    }

    public void clearFilter() {
        currentFilterType = null;
        currentFilterValue = null;

        ArrayList<User> allUsers = new ArrayList<>(mListener.getAllUsers());

        if (currentSortCriteria != null) {
            switch (currentSortCriteria) {
                case "NAME_ASC":
                    Collections.sort(allUsers, Comparator.comparing(User::getName));
                    break;
                case "NAME_DESC":
                    Collections.sort(allUsers, (a, b) -> b.getName().compareTo(a.getName()));
                    break;
                case "AGE_ASC":
                    Collections.sort(allUsers, Comparator.comparingInt(u -> getAgeGroupOrder(u.getAgeGroup())));
                    break;
                case "AGE_DESC":
                    Collections.sort(allUsers, (a, b) -> getAgeGroupOrder(b.getAgeGroup()) - getAgeGroupOrder(a.getAgeGroup()));
                    break;
                case "FEELING_ASC":
                    Collections.sort(allUsers, Comparator.comparingInt(u -> getMoodOrder(u.getMood() != null ? u.getMood().getName() : null)));
                    break;
                case "FEELING_DESC":
                    Collections.sort(allUsers, (a, b) -> getMoodOrder(b.getMood() != null ? b.getMood().getName() : null)
                            - getMoodOrder(a.getMood() != null ? a.getMood().getName() : null));
                    break;
            }
        }

        refreshUsers(allUsers);

        if (binding != null) {
            binding.textViewFilter.setText("No Filter");
        }
    }

    private int getAgeGroupOrder(String ageGroup) {
        switch (ageGroup) {
            case "Under 12 years old": return 0;
            case "12-17 years old": return 1;
            case "18-24 years old": return 2;
            case "25-34 years old": return 3;
            case "35-44 years old": return 4;
            case "45-54 years old": return 5;
            case "55-64 years old": return 6;
            case "65-74 years old": return 7;
            case "75 years or older": return 8;
            default: return Integer.MAX_VALUE;
        }

    }
    private int getMoodOrder(String moodName) {
        if (moodName == null) return Integer.MAX_VALUE;
        switch (moodName.trim().toLowerCase()) {
            case "not well": return 0;
            case "sad": return 1;
            case "ok": return 2;
            case "good": return 3;
            case "very good": return 4;
            default: return Integer.MAX_VALUE;
        }
    }

    UsersListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (UsersListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UsersListener");
        }
    }

    public interface UsersListener{
        void clearAll();
        void gotoAddNew();
        ArrayList<User> getAllUsers();
        void gotoUserProfile(User user);
        void gotoSortSelection();
        void gotoFilterSelection();
        void deleteUser(User user);
    }

    private class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.UserViewHolder> {

        ArrayList<User> users;
        UsersListener mListener;

        public UsersRecyclerViewAdapter(ArrayList<User> users, UsersListener listener) {
            this.users = users;
            this.mListener = listener;
        }
        public void updateUserList(ArrayList<User> updatedUsers) {
            if (adapter != null) {
                adapter.setUsers(updatedUsers);
            }
        }

        public void setUsers(ArrayList<User> newUsers) {
            this.users = newUsers;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_user, parent, false);
            return new UserViewHolder(view, mListener);
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            User user = users.get(position);

            holder.textViewUserName.setText(user.getName());
            holder.textViewUserAgeGroup.setText(user.getAgeGroup());

            if (user.getMood() != null && user.getMood().getImageResourceId() != 0) {
                holder.imageViewUserMood.setImageResource(user.getMood().getImageResourceId());
            }

            holder.itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.gotoUserProfile(user);
                    applyFilter(currentFilterType, currentFilterValue);

                }
            });

            holder.imageViewDelete.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.deleteUser(user);
                    applyFilter(currentFilterType, currentFilterValue);
                }
            });
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public class UserViewHolder extends RecyclerView.ViewHolder {
            TextView textViewUserName, textViewUserAgeGroup;
            ImageView imageViewUserMood, imageViewDelete;

            public UserViewHolder(@NonNull View itemView, UsersListener listener) {
                super(itemView);
                textViewUserName = itemView.findViewById(R.id.textViewUserName);
                textViewUserAgeGroup = itemView.findViewById(R.id.textViewUserAgeGroup);
                imageViewUserMood = itemView.findViewById(R.id.imageViewUserMood);
                imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
            }
        }
    }

}