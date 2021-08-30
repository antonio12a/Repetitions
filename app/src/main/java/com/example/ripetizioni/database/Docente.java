package com.example.ripetizioni.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Docente {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "docente_id")
    public int docenteId;

    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name = "cognome")
    public String cognome;

    public Docente(int docenteId, String nome, String cognome) {
        this.docenteId = docenteId;
        this.nome = nome;
        this.cognome = cognome;
    }
}
