package com.example.ripetizioni.ui.corsil;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ripetizioni.R;
import com.example.ripetizioni.database.AppDatabase;
import com.example.ripetizioni.database.Corso;
import com.example.ripetizioni.database.CorsoCompleto;
import com.example.ripetizioni.database.DatabaseDao;
import com.example.ripetizioni.database.Prenotazioni;
import com.example.ripetizioni.database.PrenotazioniUtente;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CorsiAdapter extends RecyclerView.Adapter<CorsiAdapter.MyViewHolder> implements Filterable {


    Prenotazioni prenotazione;
    Fragment fragmentHome;
    CorsiViewModel viewModel;
    int idSession;
    private List<CorsoCompleto> corsi;
    private List<CorsoCompleto> corsiFull;

    public CorsiAdapter(CorsiViewModel viewModel, int idSession){
        this.viewModel = viewModel;
        this.idSession = idSession;
    }
    @NonNull
    @Override
    public CorsiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_corsi, parent, false);
        return new CorsiAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CorsiAdapter.MyViewHolder holder, int position) {
        CorsoCompleto corso = corsi.get(position);
        holder.titolo.setText(corso.titolo_corso);
        holder.ora.setText(corso.giorno + " " + corso.ora_inizio + "/" + corso.ora_fine);
        holder.nomeDoc.setText(corso.nome+ " " + corso.cognome);
        switch(corso.titolo_corso){
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
        holder.buttonPrenota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idSession == -2){
                    Snackbar.make(view, "REGISTRATI PER EFFETTUARE UNA PRENOTAZIONE",  Snackbar.LENGTH_SHORT).show();
                }
                else {
                    //verificare se già è presente nella lista prenotati
                    //se idSession > 0
                    Prenotazioni p = new Prenotazioni(idSession, corso.corso_id, "a");
                    viewModel.insertPrenotazione(p);
                    Snackbar.make(view, "PRENOTAZIONE EFFETTUATA",  Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        holder.touch_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void setCorsiCompleti(List<CorsoCompleto> corsi){
        this.corsi = corsi;
        this.corsiFull = new ArrayList<>(corsi);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(corsi != null)
            return corsi.size();
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
            List<CorsoCompleto> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(corsiFull);
            } else {
                for (CorsoCompleto cors : corsiFull) {
                    if (cors.titolo_corso.toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                            cors.nome.toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                            cors.cognome.toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                            cors.giorno.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(cors);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            corsi.clear();
            corsi.addAll((Collection<? extends CorsoCompleto>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView titolo;
        private TextView ora;
        private TextView nomeDoc;
        private RelativeLayout touch_layout;
        private Button buttonPrenota;
        private ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titolo = itemView.findViewById(R.id.titoloCorso);
            ora = itemView.findViewById(R.id.oraCorso);
            nomeDoc = itemView.findViewById(R.id.nomeDocCorso);
            touch_layout = itemView.findViewById(R.id.touch_layoutCorso);
            buttonPrenota = itemView.findViewById(R.id.buttonPrenota);
            image = itemView.findViewById(R.id.imageViewCorso);
        }
    }
}
