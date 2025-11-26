package edu.uncc.assignment06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

import edu.uncc.assignment06.databinding.FragmentCreateTaskBinding;


public class CreateTaskFragment extends Fragment {
    private String selectedDate = null;
    public void setSelectedDate(String date){
        this.selectedDate = date;
        if (binding != null) {
            binding.textViewDate.setText(date);
        }
    }

    public CreateTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentCreateTaskBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Task");
        binding.textViewDate.setText(selectedDate);
        if (selectedDate != null) {
            binding.textViewDate.setText(selectedDate);
        }
        binding.buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wListener.gotoSelectTaskDate();
            }
        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextTaskName.getText().toString();
                String date = binding.textViewDate.getText().toString();
                int checkedPriority = binding.radioGroup.getCheckedRadioButtonId();

                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Invalid Name Setup", Toast.LENGTH_SHORT).show();
                }else if (date.isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Date Setup", Toast.LENGTH_SHORT).show();
                }else if(checkedPriority == -1){
                    Toast.makeText(getActivity(), "Select a Priority", Toast.LENGTH_SHORT).show();
                }else{
                    RadioButton selectedPriority = binding.getRoot().findViewById(checkedPriority);
                    String priority = selectedPriority.getText().toString();
                    Task newTask = new Task(name, date, priority);
                    wListener.addTask(newTask);
                    selectedDate = null;
                    binding.editTextTaskName.setText("");
                    binding.textViewDate.setText("");
                    binding.radioGroup.check(R.id.radioButtonHigh);
                }
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wListener.gotoTasks();
                selectedDate = null;
                binding.editTextTaskName.setText("");
                binding.textViewDate.setText("");
                binding.radioGroup.clearCheck();
                binding.radioGroup.check(R.id.radioButtonHigh);

            }
        });

    }
    CreateTaskFragmentListener wListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        wListener = (CreateTaskFragmentListener) context;
    }

    interface CreateTaskFragmentListener {
        void gotoTasks();
        void gotoSelectTaskDate();
        void addTask(Task task);
    }

}