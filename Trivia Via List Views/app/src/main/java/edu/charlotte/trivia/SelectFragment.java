package edu.charlotte.trivia;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.charlotte.trivia.models.Data;
import edu.charlotte.trivia.models.Trivia;


public class SelectFragment extends Fragment {
    ListView listView;
    ArrayList<Trivia> trivias;
    SelectTriviaListener listener;


    public SelectFragment() {
        // Required empty public constructor
    }


    public static SelectFragment newInstance() {
        SelectFragment fragment = new SelectFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);

        trivias = Data.getAllTrivias();

        TriviaAdapter adapter = new TriviaAdapter(requireContext(), trivias);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view1, position, id) -> {
            Trivia selectedTrivia = trivias.get(position);
            listener.onTriviaSelected(selectedTrivia);
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (SelectTriviaListener) context;

    }

    public interface SelectTriviaListener {
        void onTriviaSelected(Trivia trivia);
    }

    private static class TriviaAdapter extends ArrayAdapter<Trivia> {
        public TriviaAdapter(@NonNull Context context, @NonNull ArrayList<Trivia> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.list_item_trivia, parent, false);
            }

            Trivia trivia = getItem(position);
            TextView title = convertView.findViewById(R.id.textViewTriviaTitle);
            TextView description = convertView.findViewById(R.id.textViewTriviaDescription);

            title.setText(trivia.getTitle());
            description.setText(trivia.getDescription());

            return convertView;
        }
    }

}