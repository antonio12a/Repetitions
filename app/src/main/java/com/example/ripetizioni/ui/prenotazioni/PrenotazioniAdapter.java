package com.example.ripetizioni.ui.prenotazioni;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ripetizioni.R;
import com.example.ripetizioni.database.Prenotazioni;
import com.example.ripetizioni.database.PrenotazioniUtente;
import com.example.ripetizioni.ui.corsil.CorsiViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class PrenotazioniAdapter extends RecyclerView.Adapter<PrenotazioniAdapter.MyViewHolder> implements Filterable {

    private List<PrenotazioniUtente> prenotazioniUtente;
    private List<PrenotazioniUtente> prenotazioniUtenteFull;
    private PrenotazioniViewModel viewModel;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_prenotazioni, parent, false);
        return new MyViewHolder(v);
    }

    public PrenotazioniAdapter(PrenotazioniViewModel viewModel) {
        this.viewModel = viewModel;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PrenotazioniUtente prenotazione = prenotazioniUtente.get(position);
        holder.titolo.setText(prenotazione.titolo_corso);
        holder.ora.setText(prenotazione.giorno + " " + prenotazione.ora_inizio + "/" + prenotazione.ora_fine);
        holder.nomeDoc.setText(prenotazione.nome + " " + prenotazione.cognome);
        switch (prenotazione.titolo_corso) {
            case "Informatica":
                holder.image.setImageResource(R.drawable.informatica_corso);
                break;
            case "Matematica":
                holder.image.setImageResource(R.drawable.matematica_corso);
                break;
            case "Filosofia":
                holder.image.setImageResource(R.drawable.filosofia_corso);
                break;
            case "Biologia":
                holder.image.setImageResource(R.drawable.biologia_corso);
                break;
            case "Fisica":
                holder.image.setImageResource(R.drawable.fisica_corso);
                break;
            default:
                break;
        }
        switch (prenotazione.status) {
            case "a":
                holder.stato.setText("Attiva");
                holder.stato.setTextColor(Color.GREEN);
                holder.button.setText("Disdici");
                break;
            case "d":
                holder.stato.setText("Disdetta");
                holder.stato.setTextColor(Color.RED);
                holder.button.setText("Prenota");
                break;
            case "e":
                holder.stato.setText("Effettuata");
                holder.stato.setTextColor(Color.GRAY);
                holder.button.setVisibility(View.INVISIBLE);
                break;
        }
        holder.touch_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (prenotazione.status) {
                    case "a"://caso in cui disdico
                        viewModel.disdiciPren(prenotazione.userId, prenotazione.corso_id);
                        viewModel.insertPrenotazione(new Prenotazioni(prenotazione.userId, prenotazione.corso_id, "d"));
                        //elimino prenotazione
                        //aggiungo prenotazione
                        break;
                    case "d":// caso in cui vado a prenotare
                        viewModel.disdiciPren(prenotazione.userId, prenotazione.corso_id);
                        viewModel.insertPrenotazione(new Prenotazioni(prenotazione.userId, prenotazione.corso_id, "a"));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    void setPrenotazioniUtente(List<PrenotazioniUtente> pren) {
        prenotazioniUtente = pren;
        prenotazioniUtenteFull = new ArrayList<>(prenotazioniUtente);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (prenotazioniUtente != null)
            return prenotazioniUtente.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {


        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<PrenotazioniUtente> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(prenotazioniUtenteFull);
            } else {
                for (PrenotazioniUtente pren : prenotazioniUtenteFull) {
                    if (pren.titolo_corso.toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                            pren.nome.toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                            pren.cognome.toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                            pren.giorno.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(pren);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            prenotazioniUtente.clear();
            prenotazioniUtente.addAll((Collection<? extends PrenotazioniUtente>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView titolo;
        private TextView ora;
        private TextView nomeDoc;
        private TextView stato;
        private Button button;
        private RelativeLayout touch_layout;
        private ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titolo = itemView.findViewById(R.id.titolo);
            ora = itemView.findViewById(R.id.ora);
            nomeDoc = itemView.findViewById(R.id.nomeDoc);
            stato = itemView.findViewById(R.id.stato);
            touch_layout = itemView.findViewById(R.id.touch_layout);
            button = itemView.findViewById(R.id.buttonCardPrenotazione);
            image = itemView.findViewById(R.id.imageCorso);
        }
    }
}
