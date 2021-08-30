package com.example.ripetizioni.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity()
public class Utente{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "utenteId")
    public int utenteId;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    public Utente(int utenteId, String username, String password) {
        this.utenteId = utenteId;
        this.username = username;
        this.password = password;
    }
}
