package com.example.appmynotes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appmynotes.db.dal.NoteDAL;
import com.example.appmynotes.db.entities.Note;
import com.google.android.material.snackbar.Snackbar;

public class NewNoteActivity extends AppCompatActivity {
    private EditText etNote, etTittle;
    private SeekBar sbPriority;
    private Button btNewNote;
    private ImageView btBack;
    private TextView tvPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.create_note);
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btNewNote = findViewById(R.id.btNewNote);
        btBack = findViewById(R.id.btBack);
        etNote = findViewById(R.id.etText);
        etTittle = findViewById(R.id.etTittle);
        sbPriority = findViewById(R.id.sbPriority);
        tvPriority = findViewById(R.id.tvPriority);
        btBack.setOnClickListener(e->{finish();});
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);
        if(id!=-1) {
            NoteDAL noteDAL = new NoteDAL(this);
            Note note = noteDAL.get(id);
            if (note != null) {
                etTittle.setText(note.getTitle());
                etNote.setText(note.getText());
                sbPriority.setProgress(note.getPriority());
                if(note.getPriority()==0)
                    tvPriority.setText("Baixa");
                else if(note.getPriority()==1)
                    tvPriority.setText("Normal");
                else
                    tvPriority.setText("Alta");
                btNewNote.setText("Atualizar Tarefa");
            }
        }

        btNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id!=-1)
                    UpdateNote(id);
                else
                    CreateNewNote();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        sbPriority.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i==0)
                    tvPriority.setText("Baixa");
                else if(i==1)
                    tvPriority.setText("Normal");
                else
                    tvPriority.setText("Alta");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void UpdateNote(int id) {
        NoteDAL noteDAL = new NoteDAL(this);
        Note note = noteDAL.get(id);
        if(!etTittle.getText().toString().isEmpty() && !etNote.getText().toString().isEmpty()) {
            note.setTitle(etTittle.getText().toString());
            note.setPriority(sbPriority.getProgress());
            note.setText(etNote.getText().toString());
            noteDAL.alterar(note);
            Intent intent = new Intent(NewNoteActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else
            Log.d("NewNoteActivity", "Preencha todos os campos!");
    }

    private void CreateNewNote() {
        NoteDAL noteDAL = new NoteDAL(this);
        Note note = new Note();
        if (!etTittle.getText().toString().isEmpty() && !etNote.getText().toString().isEmpty()) {
            note.setTitle(etTittle.getText().toString());
            note.setPriority(sbPriority.getProgress());
            note.setText(etNote.getText().toString());
            noteDAL.salvar(note);
            Intent intent = new Intent(NewNoteActivity.this, MainActivity.class);
            startActivity(intent);
        } else
            Log.d("NewNoteActivity", "Preencha todos os campos!");
    }
}
