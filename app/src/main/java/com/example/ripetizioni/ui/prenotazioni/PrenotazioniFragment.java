package com.example.ripetizioni.ui.prenotazioni;


import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ripetizioni.R;
import com.example.ripetizioni.database.Prenotazioni;
import com.example.ripetizioni.database.PrenotazioniUtente;

import java.util.List;

public class PrenotazioniFragment extends Fragment {

    private PrenotazioniViewModel prenotazioniViewModel;
    private static RecyclerView recyclerView;
    private int id;
    public static PrenotazioniAdapter prenotazioniAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        prenotazioniViewModel =
                new ViewModelProvider(this).get(PrenotazioniViewModel.class);
        View root = inflater.inflate(R.layout.fragment_prenotazioni, container, false);
        recyclerView = root.findViewById(R.id.prenotazioniList);
        Bundle bundle = getArguments();
        id = bundle.getInt("idSession");
        prenotazioniAdapter = new PrenotazioniAdapter(prenotazioniViewModel);
        recyclerView.setAdapter(prenotazioniAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Log.d("ID", String.valueOf(id));
        prenotazioniViewModel.getAllPrenotazioni(id).observe(getViewLifecycleOwner(), new Observer<List<PrenotazioniUtente>>() {
            @Override
            public void onChanged(List<PrenotazioniUtente> prenotazioniUtentes) {
                prenotazioniAdapter.setPrenotazioniUtente(prenotazioniUtentes);
            }
        });
        return root;
    }
}
