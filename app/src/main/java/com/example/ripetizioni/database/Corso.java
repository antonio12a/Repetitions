package com.example.ripetizioni.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Docente.class, parentColumns = "docente_id", childColumns = "docente_corso", onDelete = ForeignKey.CASCADE )})
public class Corso{


    @ColumnInfo(name = "corso_id")
    @PrimaryKey(autoGenerate = true)
    public int id_corso;
    @ColumnInfo(name = "titolo_corso")
    @NonNull public String titolo;


    @ColumnInfo(name = "docente_corso")
    @NonNull public int docente;

    @ColumnInfo(name = "giorno")
    public String giorno;

    @ColumnInfo(name = "ora_inizio")
    public String oraInizio;

    @ColumnInfo(name = "ora_fine")
    public String oraFine;

    public Corso(int id_corso, @NonNull String titolo, int docente, String giorno, String oraInizio, String oraFine) {
        this.id_corso = id_corso;
        this.titolo = titolo;
        this.docente = docente;
        this.giorno = giorno;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
    }
}