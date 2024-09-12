package com.example.appmynotes.db.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.appmynotes.db.entities.Note;
import com.example.appmynotes.db.util.Conexao;

import java.util.ArrayList;

public class NoteDAL {
    private Conexao con;
    private final String TABLE="notes";

    public NoteDAL(Context context) {
        con = new Conexao(context);
        try {
            con.conectar();
        }
        catch(Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public boolean salvar(Note n)
    {
        ContentValues dados=new ContentValues();
        dados.put("note_title",n.getTitle());
        dados.put("note_text",n.getText());
        dados.put("note_priority",n.getPriority());

        return con.inserir(TABLE,dados)>0;
    }
    public boolean alterar(Note n)
    {
        ContentValues dados=new ContentValues();
        dados.put("note_id",n.getId());
        dados.put("note_title",n.getTitle());
        dados.put("note_text",n.getText());
        dados.put("note_priority",n.getPriority());
        return con.alterar(TABLE,dados,"note_id="+n.getId())>0;
    }
    public boolean apagar(long chave)
    {
        return con.apagar(TABLE,"note_id="+chave)>0;
    }

    public Note get(int id)
    {   Note n = null;
        Cursor cursor=con.consultar("select * from "+TABLE+" where note_id="+id);
        if(cursor.moveToFirst())
            n=new Note(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getInt(3));
        cursor.close();;
        return n;
    }
    public ArrayList<Note> get(String filtro)
    {
        ArrayList <Note> notes = new ArrayList();
        String sql="select * from "+TABLE;
        if (!filtro.equals(""))
            sql += " ORDER BY " + filtro;

        Cursor cursor=con.consultar(sql);
        if(cursor.moveToFirst())
            while (!cursor.isAfterLast()) {
                notes.add(new Note(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                        cursor.getInt(3)));
                cursor.moveToNext();
            }
        cursor.close();;
        return notes;
    }
}
