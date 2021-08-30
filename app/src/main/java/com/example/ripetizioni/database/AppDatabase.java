package com.example.ripetizioni.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Prenotazioni.class, Utente.class, Corso.class, Docente.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DatabaseDao databaseDao();
    private static AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            Log.d("perfetto", "ok");
            new PopulateDbAsync(INSTANCE).execute();
        }
    };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final  DatabaseDao mDao;




        PopulateDbAsync(AppDatabase db) {
            mDao = db.databaseDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created

            mDao.deleteAllUtenti();
            mDao.deleteAllPrenotazioni();
            mDao.deleteAllDocenti();
            mDao.deleteAllCorsi();

            List<Utente> lista = new ArrayList<>();
            Utente utente1 = new Utente(1, "Marco", "ddccerc");
            Utente utente2 = new Utente(2, "ok", "ok");
            Utente utente3 = new Utente(3, "Maria", "Rosso");
            lista.add(utente1);
            lista.add(utente2);
            lista.add(utente3);
            final Utente[] users = lista.toArray(new Utente[lista.size()]);

            Docente docente1 = new Docente(1, "Francesco", "Salvi");
            Docente docente2 = new Docente(2, "Enrico", "Rossi");
            Docente docente3 = new Docente(3, "Maria", "Ferri");
            Docente docente4 = new Docente(4, "Roberto", "Moro");
            Docente docente5 = new Docente(5, "Fabrizio", "Caramia");
            List<Docente> listaDocenti = new ArrayList<>();
            listaDocenti.add(docente1);
            listaDocenti.add(docente2);
            listaDocenti.add(docente3);
            listaDocenti.add(docente4);
            listaDocenti.add(docente5);
            final Docente[] docenti = listaDocenti.toArray(new Docente[listaDocenti.size()]);


            List<Corso> listaCorsi = new ArrayList<>();
            Corso corso1 = new Corso(1, "Informatica", docente1.docenteId, "Martedì", "18:00", "20:00");
            Corso corso2 = new Corso(2, "Matematica", docente2.docenteId, "Martedì", "18:00", "20:00");
            Corso corso3 = new Corso(3, "Filosofia", docente3.docenteId, "Mercoledì", "14:00", "18:00");
            Corso corso4 = new Corso(4, "Fisica", docente4.docenteId, "Mercoledì", "14:00", "18:00");
            Corso corso5 = new Corso(5, "Biologia", docente5.docenteId, "Mercoledì", "14:00", "18:00");
            Corso corso6 = new Corso(6, "Informatica", docente2.docenteId, "Martedì", "18:00", "20:00");

            listaCorsi.add(corso1);
            listaCorsi.add(corso2);
            listaCorsi.add(corso3);
            listaCorsi.add(corso4);
            listaCorsi.add(corso5);
            listaCorsi.add(corso6);
            final Corso[] corsi = listaCorsi.toArray(new Corso[listaCorsi.size()]);


            List<Prenotazioni> listaPrenotazioni = new ArrayList<>();
            listaPrenotazioni.add(new Prenotazioni(utente2.utenteId, corso1.id_corso, "a"));
            listaPrenotazioni.add(new Prenotazioni(utente2.utenteId, corso2.id_corso, "e"));
            final Prenotazioni[] prenotazioni = listaPrenotazioni.toArray(new Prenotazioni[listaPrenotazioni.size()]);


            mDao.insertAllUtenti(users);
            mDao.insertAllDocenti(docenti);
            mDao.insertAllCorsi(corsi);
            mDao.insertAllPrenotazioni(prenotazioni);

            return null;
        }
    }
}
