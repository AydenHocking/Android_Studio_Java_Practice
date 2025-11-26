package edu.charlotte.evaluation03.fragments;

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
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.charlotte.evaluation03.R;
import edu.charlotte.evaluation03.databinding.FragmentEmployeesBinding;
import edu.charlotte.evaluation03.models.DataService;
import edu.charlotte.evaluation03.models.Employee;

public class EmployeesFragment extends Fragment {

    public EmployeesFragment() {
        // Required empty public constructor
    }

    FragmentEmployeesBinding binding;
    ArrayList<Employee> mFilteredEmployees = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEmployeesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<Employee> mEmployees = DataService.getEmployees();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Employees");

//        ArrayList<String> departments = DataService.getDepartments();

//        binding.recyclerViewFilter.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

//        FilterAdapter departmentsAdapter = new FilterAdapter(departments, selected -> {
//            if (mListener != null) {
//
//            }
//        });

        Eadapter adapter = new Eadapter(getContext(), mEmployees);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener((parent, view1, position, id) -> {
            Employee employee = mEmployees.get(position);
            mListener.onEmployeeSelected(employee);
        });

    }
    private static class Eadapter extends ArrayAdapter<Employee> {
        ArrayList<Employee> mEmployees;
        LayoutInflater inflater;
        public Eadapter(Context context, ArrayList<Employee> employees) {

            super(context, R.layout.list_item_employee, employees);
            this.mEmployees = employees;
            this.inflater = LayoutInflater.from(context);
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_employee, parent, false);
            }

            Employee employee = mEmployees.get(position);

            TextView textViewName = convertView.findViewById(R.id.textViewDetailName);
            TextView textViewDepartment = convertView.findViewById(R.id.textViewPriceAndQuantity);

            textViewName.setText(employee.getName());
            textViewDepartment.setText(employee.getDepartment());

            return convertView;
        }


    }
//    private static class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
//        private final ArrayList<String> items;
//        private final OnItemClickListener listener;
//
//        interface OnItemClickListener { void onItemClick(String selected); }
//
//        public FilterAdapter(ArrayList<String> items, OnItemClickListener listener) {
//            this.items = items;
//            this.listener = listener;
//        }
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            android.widget.TextView textView = new android.widget.TextView(parent.getContext());
//            textView.setPadding(16, 16, 16, 16);
//            textView.setTextSize(18);
//            textView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
//            return new ViewHolder(textView, listener);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            holder.textView.setText(items.get(position));
//        }
//
//        @Override
//        public int getItemCount() { return items.size(); }
//
//        static class ViewHolder extends RecyclerView.ViewHolder {
//            android.widget.TextView textView;
//            public ViewHolder(@NonNull android.view.View itemView, OnItemClickListener listener) {
//                super(itemView);
//                textView = (android.widget.TextView) itemView;
//                textView.setOnClickListener(v -> listener.onItemClick(textView.getText().toString()));
//            }
//        }
//    }


    EmployeesListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EmployeesListener) {
            mListener = (EmployeesListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EmployeesListener");
        }
    }

    public interface EmployeesListener {
        void onEmployeeSelected(Employee employee);
    }
}