package com.example.ripetizioni.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelLogin extends AndroidViewModel {


    private RepositoryLogin repositoryLogin;
    private LiveData<List<Utente>> utenti;
    private LiveData<Utente> utente;


    public ViewModelLogin(@NonNull Application application) {
        super(application);
        repositoryLogin = new RepositoryLogin(application);
        utenti = repositoryLogin.getUtenti();
    }

    public LiveData<List<Utente>> getAllUtenti() {
        return utenti;
    }


    public boolean checkUtente(String username){
        for(Utente utent: utenti.getValue()){
            if(username.equals(utent.username))
                return true;
        }
        return false;
    }

    public int checkPassword(String username, String password){
        for(Utente utent: utenti.getValue()){
            if(username.equals(utent.username) && password.equals(utent.password))
                return utent.utenteId;
        }
        return 0;
    }

    public void addUtente(Utente utente) {
        repositoryLogin.insertUtente(utente);
    }

    public int getLastIndex() {
        return utenti.getValue().size();
    }
}
