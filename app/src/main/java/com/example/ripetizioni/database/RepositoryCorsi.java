package com.example.ripetizioni.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RepositoryCorsi implements Serializable {

    private DatabaseDao databaseDao;

    private static boolean res;
    private LiveData<List<CorsoCompleto>> corsi;

    public RepositoryCorsi(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        databaseDao = db.databaseDao();
        corsi = databaseDao.getCorsiCompleti();
    }

    public boolean insertPrenotazione(Prenotazioni prenotazione){
        List<Prenotazioni> p = new ArrayList<>();
        p.add(prenotazione);
        final Prenotazioni[] prenotazioni = p.toArray(new Prenotazioni[p.size()]);
        new RepositoryCorsi.insertAsyncTask(databaseDao).execute(prenotazioni).getStatus();
        return res;
    }

    public LiveData<List<CorsoCompleto>> getCorsiCompleti() {
        return corsi;
    }



    private static class insertAsyncTask extends AsyncTask<Prenotazioni, Void, Void> {

        private DatabaseDao mAsyncTaskDao;

        insertAsyncTask(DatabaseDao dao) {
            mAsyncTaskDao = dao;
        }

        //per cose che si inseriscono
        @Override
        protected Void doInBackground(final Prenotazioni... params) {
                try{
                    mAsyncTaskDao.insertAllPrenotazioni(params[0]);
                    res = true;
                }
                catch(Exception e){
                    res = false;
                }
                return null;
        }
    }
}
