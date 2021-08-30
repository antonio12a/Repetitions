package com.example.ripetizioni.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.io.Serializable;
import java.util.List;

public class RepositoryPrenotazioni implements Serializable {
    /* fare dei metodi per
     * modifica dello stato delle prenotazioni
     * MUTABLELIVEDATA
     */


    private DatabaseDao databaseDao;


    private LiveData<List<Corso>> corsi;
    private LiveData<List<PrenotazioniUtente>> prenotazioni;




    public RepositoryPrenotazioni(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        databaseDao = db.databaseDao();
        corsi = databaseDao.getCorsi();
    }

    public void insertUtente(Utente utente){
        new RepositoryPrenotazioni.insertUtenteAsyncTask(databaseDao).execute(utente);
    }

    public LiveData<List<Corso>> getCorsi() {
        return corsi;
    }

    public LiveData<List<PrenotazioniUtente>> getPrenotazioniUtente(int i) {
        prenotazioni = databaseDao.getPrenotazioniUtente(i);
        return prenotazioni;
    }

    public void disdiciPrenotazione(int userId, int corso_id) {
        new RepositoryPrenotazioni.deletePrenotazioneAsyncTask(databaseDao).execute(userId, corso_id);
    }

    public void insertPrenotazione(Prenotazioni p) {
        new RepositoryPrenotazioni.insertPrenotazioneAsyncTask(databaseDao).execute(p);
    }

    private static class deletePrenotazioneAsyncTask extends AsyncTask<Integer, Void, Void> {

        private DatabaseDao mAsyncTaskDao;

        deletePrenotazioneAsyncTask(DatabaseDao dao) {
            mAsyncTaskDao = dao;
        }
        //per cose che si inseriscono
        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deletePrenotazione(params[0], params[1]);
            return null;
        }
    }

    private static class insertUtenteAsyncTask extends AsyncTask<Utente, Void, Void> {

        private DatabaseDao mAsyncTaskDao;

        insertUtenteAsyncTask(DatabaseDao dao) {
            mAsyncTaskDao = dao;
        }
        //per cose che si inseriscono
        @Override
        protected Void doInBackground(final Utente... params) {
            mAsyncTaskDao.insertAllUtenti(params[0]);
            return null;
        }
    }
    private static class insertPrenotazioneAsyncTask extends AsyncTask<Prenotazioni, Void, Void> {

        private DatabaseDao mAsyncTaskDao;

        insertPrenotazioneAsyncTask(DatabaseDao dao) {
            mAsyncTaskDao = dao;
        }
        //per cose che si inseriscono
        @Override
        protected Void doInBackground(final Prenotazioni... params) {
            mAsyncTaskDao.insertAllPrenotazioni(params[0]);
            return null;
        }
    }


}
