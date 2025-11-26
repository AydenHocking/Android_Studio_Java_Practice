package edu.charlotte.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.charlotte.evaluation03.R;
import edu.charlotte.evaluation03.databinding.FragmentPurchasesBinding;
import edu.charlotte.evaluation03.models.Employee;
import edu.charlotte.evaluation03.models.Item;
import edu.charlotte.evaluation03.models.Purchase;

public class PurchasesFragment extends Fragment {
    private static final String ARG_PARAM_EMPLOYEE = "ARG_PARAM_EMPLOYEE";
    ArrayList<Purchase> mPurchases = new ArrayList<>();

    Employee mEmployee;
    public PurchasesFragment() {
        // Required empty public constructor
    }

    public static PurchasesFragment newInstance(Employee employee) {
        PurchasesFragment fragment = new PurchasesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_EMPLOYEE, employee);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmployee = (Employee) getArguments().getSerializable(ARG_PARAM_EMPLOYEE);
        }
    }

    FragmentPurchasesBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPurchasesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Purchases of " + mEmployee.getName());
        mPurchases.addAll(mEmployee.getPurchases());
        Padapter adapter = new Padapter(getContext(), mPurchases);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener((parent, view1, position, id) -> {
            Purchase selectedPurchase = (Purchase) parent.getItemAtPosition(position);
            mListener.onPurchaseSelected(selectedPurchase);
        });

//        binding.listView.setOnItemClickListener((parent, view1, position, id) -> {
//            Purchase purchase = mEmployee.getPurchases().get(position);
//            mListener.onPurchaseSelected(purchase);
//        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelected();
            }
        });
        binding.imageViewAsc.setOnClickListener(v -> {
            Collections.sort(mPurchases, Comparator.comparingDouble(this::getPurchaseTotal));
            adapter.notifyDataSetChanged();
            binding.textViewSortedBy.setText("Sorted By: Total (ASC)");

        });

        binding.imageViewDesc.setOnClickListener(v -> {
            Collections.sort(mPurchases, (p1, p2) ->
                    Double.compare(getPurchaseTotal(p2), getPurchaseTotal(p1)));
            adapter.notifyDataSetChanged();
            binding.textViewSortedBy.setText("Sorted By: Total (DESC)");
        });
    }


    private double getPurchaseTotal(Purchase purchase) {
        double total = 0;
        for (Item item : purchase.getItems()) {
            total += item.getPrice() * item.getQuantity();
            Log.d("TAG", "getPurchaseTotal: " + total);
        }
        return total;
    }
    private static class Padapter extends ArrayAdapter<Purchase> {
        private final ArrayList<Purchase> mPurchases;
        private final LayoutInflater inflater;

        public Padapter(@NonNull Context context, @NonNull ArrayList<Purchase> purchases) {
            super(context, R.layout.list_item_purchase, purchases);
            this.mPurchases = purchases;
            this.inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_purchase, parent, false);
            }

            Purchase purchase = mPurchases.get(position);

            TextView textViewDate = convertView.findViewById(R.id.textViewDetailName);
            TextView textViewAmount = convertView.findViewById(R.id.textViewPriceAndQuantity);
            TextView textViewItems = convertView.findViewById(R.id.textViewDetailPrice);
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
            String formattedDate = sdf.format(purchase.getPurchaseDate());
            textViewDate.setText(formattedDate);
            textViewAmount.setText(String.format("$%.2f", purchase.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum()));
            int totalQuantity = purchase.getItems().stream().mapToInt(Item::getQuantity).sum();
            textViewItems.setText(totalQuantity + " items");



            return convertView;
        }
    }

    PurchasesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PurchasesListener) {
            mListener = (PurchasesListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PurchasesListener");
        }
    }

    public interface PurchasesListener {
        void onPurchaseSelected(Purchase purchase);
        void onCancelSelected();
    }

}