package com.example.ripetizioni.ui.corsil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ripetizioni.R;
import com.example.ripetizioni.database.Corso;
import com.example.ripetizioni.database.CorsoCompleto;
import com.example.ripetizioni.database.PrenotazioniUtente;
import com.example.ripetizioni.ui.prenotazioni.PrenotazioniAdapter;

import java.util.List;

public class CorsiFragment extends Fragment {
    private CorsiViewModel corsiViewModel;
    private static RecyclerView recyclerView;
    private static TextView textUser;
    private int id;
    public static CorsiAdapter corsiAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        corsiViewModel =
                new ViewModelProvider(this).get(CorsiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_corsi, container, false);
        recyclerView = root.findViewById(R.id.corsiList);
        Bundle bundle = getArguments();
        id = bundle.getInt("idSession");
        Log.d("SES", String.valueOf(id));
        corsiAdapter = new CorsiAdapter(corsiViewModel, id);
        recyclerView.setAdapter(corsiAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        corsiViewModel.getListCorsi().observe(getViewLifecycleOwner(), new Observer<List<CorsoCompleto>>() {
            @Override
            public void onChanged(List<CorsoCompleto> corsi) {
                corsiAdapter.setCorsiCompleti(corsi);
            }
        });
        return root;
    }
}
