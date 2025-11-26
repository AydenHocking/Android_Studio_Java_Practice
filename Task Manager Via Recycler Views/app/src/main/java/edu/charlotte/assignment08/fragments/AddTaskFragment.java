package edu.charlotte.assignment08.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.charlotte.assignment08.R;
import edu.charlotte.assignment08.models.Task;


public class AddTaskFragment extends Fragment {

    EditText editTextName;
    Button buttonSelectCategory, buttonSelectPriority, buttonSubmit;
    TextView textViewCategory, textViewPriority;
    AddTaskFragmentListener tListener;
    String selectedCategory = null;
    String selectedPriority = null;


    public AddTaskFragment() {
        // Required empty public constructor
    }


    public static AddTaskFragment newInstance() {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof AddTaskFragmentListener) {
            tListener = (AddTaskFragmentListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextName = view.findViewById(R.id.editTextName);
        buttonSelectCategory = view.findViewById(R.id.buttonSelectCategory);
        buttonSelectPriority = view.findViewById(R.id.buttonSelectPriority);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);
        textViewCategory = view.findViewById(R.id.textViewCategory);
        textViewPriority = view.findViewById(R.id.textViewPriority);
        editTextName.setText("");
        textViewCategory.setText("N/A");
        textViewPriority.setText("N/A");


        if (selectedCategory != null) {
            textViewCategory.setText(selectedCategory);
        }
        if (selectedPriority != null) {
            textViewPriority.setText(selectedPriority);
        }

        buttonSelectCategory.setOnClickListener(v -> {
            if (tListener != null) {
                tListener.selectCategory();
            }
        });
        buttonSelectPriority.setOnClickListener(v -> {
            if (tListener != null) {
                tListener.selectPriority();
            }
        });
        buttonSubmit.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String category = textViewCategory.getText().toString();
            String priorityStr = textViewPriority.getText().toString();
            int priority = getPriorityValue(priorityStr);
            if (!name.isEmpty() && !category.equals("N/A") && !priorityStr.equals("N/A")) {
                Task task = new Task(name, category, priorityStr, priority);
                if (tListener != null) {
                    tListener.addNewTask(task);
                }
            }else{
                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Name is required", Toast.LENGTH_SHORT).show();
                }
                if(category.equals("N/A")){
                    Toast.makeText(getActivity(), "Category is required", Toast.LENGTH_SHORT).show();
                }
                if(priorityStr.equals("N/A")){
                    Toast.makeText(getActivity(), "Priority is required", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private int getPriorityValue(String priority) {
        switch (priority) {
            case "Very High": return 5;
            case "High": return 4;
            case "Medium": return 3;
            case "Low": return 2;
            case "Very Low": return 1;
            default: return 0;
        }
    }
    public void setSelectedCategory(String category) {
        this.selectedCategory = category;

        if (textViewCategory != null) {
            textViewCategory.setText(category);
        }
    }
    public void setSelectedPriority(String priority) {
        this.selectedPriority = priority;
        if (textViewPriority != null) {
            textViewPriority.setText(priority);
        }
    }


    public interface AddTaskFragmentListener {
        void selectCategory();
        void selectPriority();
        void addNewTask(Task task);
    }
}