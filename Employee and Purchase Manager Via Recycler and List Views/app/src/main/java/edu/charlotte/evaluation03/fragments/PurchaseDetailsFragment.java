package edu.charlotte.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.charlotte.evaluation03.R;
import edu.charlotte.evaluation03.databinding.FragmentPurchaseDetailsBinding;
import edu.charlotte.evaluation03.models.Item;
import edu.charlotte.evaluation03.models.Purchase;

public class PurchaseDetailsFragment extends Fragment {
    private static final String ARG_PARAM_PURCHASE = "ARG_PARAM_PURCHASE";
    private Purchase mPurchase;
    ArrayList<Item> mItems = new ArrayList<>();


    public PurchaseDetailsFragment() {
        // Required empty public constructor
    }

    public static PurchaseDetailsFragment newInstance(Purchase purchase) {
        PurchaseDetailsFragment fragment = new PurchaseDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_PURCHASE, purchase);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPurchase = (Purchase) getArguments().getSerializable(ARG_PARAM_PURCHASE);
        }
    }

    FragmentPurchaseDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPurchaseDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Purchase Details");
        mItems.addAll(mPurchase.getItems());
        Iadapter adapter = new Iadapter(getContext(), mItems);
        binding.listView.setAdapter(adapter);
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelected();
            }
        });
        double total = mPurchase.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        binding.textViewTotal.setText(String.format("Total: $%.2f", total));
        int quantity = mPurchase.getItems().stream().mapToInt(Item::getQuantity).sum();
        binding.textViewQuantity.setText("Quantity: " + quantity + " items");

    }
    private static class Iadapter extends ArrayAdapter<Item> {
        private final ArrayList<Item> mItems;
        private final LayoutInflater inflater;

        public Iadapter(@NonNull Context context, @NonNull ArrayList<Item> items) {
            super(context, R.layout.list_item_purchase_details, items);
            this.mItems = items;
            this.inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_purchase_details, parent, false);
            }

            Item item = mItems.get(position);

            TextView textViewItemName = convertView.findViewById(R.id.textViewDetailName);
            TextView textViewPriceandQuantity = convertView.findViewById(R.id.textViewPriceAndQuantity);
            TextView textViewDetailPrice = convertView.findViewById(R.id.textViewDetailPrice);
            textViewItemName.setText(item.getItemName());
            textViewPriceandQuantity.setText(String.format("$%.2f x %d", item.getPrice(), item.getQuantity()));
            textViewDetailPrice.setText(String.format("$%.2f", item.getPrice() * item.getQuantity()));





            return convertView;
        }
    }

    PurchaseDetailsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PurchaseDetailsListener) {
            mListener = (PurchaseDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PurchaseDetailsListener");
        }
    }

    public interface PurchaseDetailsListener {
        void onCancelSelected();
    }

}