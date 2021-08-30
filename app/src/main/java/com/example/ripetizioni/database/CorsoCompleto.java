package com.example.ripetizioni.database;

import androidx.room.ColumnInfo;

public class CorsoCompleto {
    public String nome;
    public String cognome;
    public String titolo_corso;
    public String giorno;
    public String ora_inizio;

    public String ora_fine;
    public int corso_id;


    public CorsoCompleto(String nome, String cognome, String titolo_corso, String giorno, String ora_inizio, String ora_fine, int corso_id) {
        this.nome = nome;
        this.cognome = cognome;
        this.titolo_corso = titolo_corso;
        this.giorno = giorno;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.corso_id = corso_id;
    }
}
