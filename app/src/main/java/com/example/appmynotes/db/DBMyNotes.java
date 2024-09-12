package com.example.appmynotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBMyNotes extends SQLiteOpenHelper {
    private static final int VERSAO = 1;   
    public DBMyNotes(Context context) {
        super(context, "mynotes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notes( note_id INTEGER PRIMARY KEY AUTOINCREMENT, note_title TEXT, note_text TEXT, note_priority INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL ("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }
}

