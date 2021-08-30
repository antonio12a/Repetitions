package com.example.ripetizioni.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.RoomDatabase;

import java.io.Serializable;
import java.util.List;

public class RepositoryLogin implements Serializable {

    /* fare dei metodi per
    * recuperare la lista delle prenotazioni utente
    * recuperare la lista dei corsi disponibili
    * popolamento db
    * modifica dello stato delle prenotazioni
    * MUTABLELIVEDATA
     */


    private DatabaseDao databaseDao;

   // private MutableLiveData<List<PrenotazioniUtente>> prenotazioniUtente;

    private LiveData<List<Utente>> utenti;

    private LiveData<List<Corso>> corsi;
    private LiveData<Utente> utente;




    RepositoryLogin(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        databaseDao = db.databaseDao();
        Log.d("CIAO", "OKKK");
        utenti = databaseDao.getUtenti();
        //prenotazioniUtente = databaseDao.getPrenotazioniUtente(idUser);
    }

    public LiveData<List<Utente>> getUtenti(){
        return utenti;
    }


    /*
    public LiveData<List<PrenotazioniUtente>> getPrenotazioniUtente() {
        return prenotazioniUtente;
    }*/
    //per cose che si inseriscono
    public void insertUtente(Utente utente){
        new insertAsyncTask(databaseDao).execute(utente);
    }

    public LiveData<Utente> getUtente(String username){
       return databaseDao.findUser(username);
    }

    private static class insertAsyncTask extends AsyncTask<Utente, Void, Void> {

        private DatabaseDao mAsyncTaskDao;

        insertAsyncTask(DatabaseDao dao) {
            mAsyncTaskDao = dao;
        }
        //per cose che si inseriscono
        @Override
        protected Void doInBackground(final Utente... params) {
            mAsyncTaskDao.insertAllUtenti(params[0]);
            return null;
        }
    }
}
