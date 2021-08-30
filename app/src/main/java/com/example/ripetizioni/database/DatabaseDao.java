package com.example.ripetizioni.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DatabaseDao {

    @Query("SELECT * FROM utente")
    LiveData<List<Utente>> getUtenti();

    @Query("SELECT * FROM prenotazioni")
    List<Prenotazioni> getPrenotazioni();

    @Query("SELECT * FROM corso")
    LiveData<List<Corso>> getCorsi();

    @Query("SELECT * FROM utente WHERE username = :user")
    public LiveData<Utente> findUser(String user);
    @Insert
    void insertAllUtenti(Utente... utenti);

    @Insert
    void insertAllDocenti(Docente... docenti);

    @Insert
    void insertAllCorsi(Corso... corsi);

    @Query("DELETE FROM utente")
    void deleteAllUtenti();

    @Query("DELETE FROM prenotazioni")
    void deleteAllPrenotazioni();

    @Query("DELETE FROM docente")
    void deleteAllDocenti();

    @Query("DELETE FROM corso")
    void deleteAllCorsi();

    @Query("DELETE FROM prenotazioni WHERE prenotazioni.corso_id = :corsoId and prenotazioni.userId = :userId")
    void deletePrenotazione(int userId, int corsoId);

    @Insert
    void insertAllPrenotazioni(Prenotazioni... prenotazioni);

    @Query("SELECT Docente.nome , Docente.cognome , Corso.titolo_corso , Corso.giorno, Corso.ora_inizio, Corso.ora_fine, Prenotazioni.status, Prenotazioni.userId, Prenotazioni.corso_id " +
            "FROM Docente JOIN Corso JOIN  Prenotazioni WHERE Prenotazioni.corso_id = Corso.corso_id and Docente.docente_id = Corso.docente_corso and " +
            " Prenotazioni.userId = :userId ORDER BY Corso.titolo_corso, Corso.giorno")
   LiveData<List<PrenotazioniUtente>> getPrenotazioniUtente(int userId);

    @Query("SELECT Docente.nome, Docente.cognome ,Corso.titolo_corso, Corso.giorno , Corso.ora_inizio, Corso.ora_fine , Corso.corso_id FROM Corso JOIN Docente WHERE Corso.docente_corso = Docente.docente_id ORDER BY Corso.titolo_corso, Corso.giorno")
    LiveData<List<CorsoCompleto>> getCorsiCompleti();

    @Query("SELECT utente.username FROM utente WHERE utente.utenteId = :session")
    String findUserById(int session);
}