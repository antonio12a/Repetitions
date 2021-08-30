package com.example.ripetizioni.ui.prenotazioni;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ripetizioni.MainActivity;
import com.example.ripetizioni.database.Prenotazioni;
import com.example.ripetizioni.database.PrenotazioniUtente;
import com.example.ripetizioni.database.RepositoryPrenotazioni;

import java.util.List;

import Session.SessionManagement;


public class PrenotazioniViewModel extends AndroidViewModel {


    private LiveData<List<PrenotazioniUtente>> listPrenotazioni;
    private RepositoryPrenotazioni repositoryP;

    public PrenotazioniViewModel(Application application) {
        super(application);
        repositoryP = new RepositoryPrenotazioni(application);
    }

    public LiveData<List<PrenotazioniUtente>> getAllPrenotazioni(int idSession) {
        listPrenotazioni = repositoryP.getPrenotazioniUtente(idSession);
        return listPrenotazioni;
    }

    public void disdiciPren(int userId, int corso_id){
        repositoryP.disdiciPrenotazione(userId, corso_id);
    }
    public void insertPrenotazione(Prenotazioni p){
        repositoryP.insertPrenotazione(p);
    }


}
