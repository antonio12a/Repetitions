package com.example.ripetizioni.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity(primaryKeys = {"userId", "corso_id"},
        foreignKeys = {@ForeignKey( entity = Utente.class, parentColumns = "utenteId", childColumns = "userId", onDelete = ForeignKey.CASCADE ),
        @ForeignKey( entity = Corso.class, parentColumns = "corso_id", childColumns = "corso_id")})
public class Prenotazioni {


    @ColumnInfo(name = "userId")
    @NonNull
    public int utenteId;

    @ColumnInfo(name = "corso_id")
    @NonNull
    public int corso_id;

    @ColumnInfo(name = "status")
    public String status;

    public Prenotazioni(int utenteId, int corso_id, String status) {
        this.utenteId = utenteId;
        this.corso_id = corso_id;
        this.status = status;
    }

}
