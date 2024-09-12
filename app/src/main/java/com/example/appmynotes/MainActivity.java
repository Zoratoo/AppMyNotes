package com.example.appmynotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appmynotes.db.dal.NoteDAL;
import com.example.appmynotes.db.entities.Note;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvTarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvTarefas = findViewById(R.id.lvTarefas);
        if(lvTarefas!=null)
            loadNotes("");

        lvTarefas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = (Note) adapterView.getItemAtPosition(i);
                NoteDAL noteDAL = new NoteDAL(MainActivity.this);
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Quer apagar esta tarefa?", Snackbar.LENGTH_LONG);
                snackbar.setAction("Sim", view1 -> {
                    noteDAL.apagar(note.getId());
                    loadNotes("");
                });
                snackbar.show();
                return true;
            }
        });

        lvTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                Note note = (Note) adapterView.getItemAtPosition(i);
                intent.putExtra("id", note.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.itFechar)
            finishAffinity();
        else if (item.getItemId() == R.id.itOrdPrioridade) { // Ordenar por prioridade
            loadNotes("note_priority ASC");
        }
        else if (item.getItemId() == R.id.itOrdOrdem) { // Ordenar por ordem alfabética
            loadNotes("LOWER(note_title) ASC");
        }
        else if(item.getItemId() == R.id.itNovaAnotacao){ //criar anotação
            Intent intent = new Intent(this, NewNoteActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void loadNotes(String orderBy) {
        NoteDAL noteDAL = new NoteDAL(this);
        List<Note> notes = noteDAL.get(orderBy);
        lvTarefas.setAdapter(new NotesAdapter(this, R.layout.item_listview, notes));
    }
}