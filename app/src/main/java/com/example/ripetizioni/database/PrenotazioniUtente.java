package com.example.ripetizioni.database;

public class PrenotazioniUtente {
    public String nome; //nomeDocente
    public String cognome; //cognomeDocente
    public String titolo_corso; //titolo corso
    public String giorno;
    public String ora_inizio;
    public String ora_fine;
    public String status;
    public int userId;
    public int corso_id;

    public PrenotazioniUtente(String nome, String cognome, String titolo_corso, String giorno, String ora_inizio, String ora_fine, String status, int userId, int corso_id) {
        this.nome = nome;
        this.cognome = cognome;
        this.titolo_corso = titolo_corso;
        this.giorno = giorno;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.status = status;
        this.userId = userId;
        this.corso_id = corso_id;

    }
}
