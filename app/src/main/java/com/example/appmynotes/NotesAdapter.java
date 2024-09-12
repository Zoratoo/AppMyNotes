package com.example.appmynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appmynotes.db.dal.NoteDAL;
import com.example.appmynotes.db.entities.Note;

import java.util.List;

public class NotesAdapter extends ArrayAdapter<Note> {
    private int resource;
    public NotesAdapter(@NonNull Context context, int resource, @NonNull List<Note> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(this.resource,parent,false);
        }
        Note note = getItem(position);

        if(note.getPriority()==0) //prioridade baixa
            convertView.setBackgroundColor(0xFFFFF9C4);
        else if(note.getPriority()==1) //prioridade média
            convertView.setBackgroundColor(0xFFBBDEFB);
        else //prioridade alta
            convertView.setBackgroundColor(0XFFFFAAFF);

        TextView tvNotes = convertView.findViewById(R.id.tvTarefa);
        if (tvNotes != null) {
            tvNotes.setText(note.getTitle());
        }
        return convertView;
    }
}
