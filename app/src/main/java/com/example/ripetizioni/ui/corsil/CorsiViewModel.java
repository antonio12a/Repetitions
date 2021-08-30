package com.example.ripetizioni.ui.corsil;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ripetizioni.database.CorsoCompleto;
import com.example.ripetizioni.database.DatabaseDao;
import com.example.ripetizioni.database.Prenotazioni;
import com.example.ripetizioni.database.PrenotazioniUtente;
import com.example.ripetizioni.database.RepositoryCorsi;
import com.example.ripetizioni.database.RepositoryPrenotazioni;

import java.util.List;

public class CorsiViewModel extends AndroidViewModel {
        private RepositoryCorsi repository;
        private LiveData<List<CorsoCompleto>> listCorsi;
        private LiveData<String> username;

        public CorsiViewModel(Application application) {
            super(application);
            repository = new RepositoryCorsi(application);
            listCorsi = repository.getCorsiCompleti();
        }

        public LiveData<List<CorsoCompleto>> getListCorsi() {
            return listCorsi;
        }


    public boolean insertPrenotazione(Prenotazioni p) {
            return repository.insertPrenotazione(p);
    }

}
