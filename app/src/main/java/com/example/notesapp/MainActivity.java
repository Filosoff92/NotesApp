package com.example.notesapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Note> notes = new ArrayList<>();
        notes.add(new Note("Заметка 1", "Текст первой заметки"));
        notes.add(new Note("Заметка 2", "Текст второй заметки"));
        notes.add(new Note("Заметка 3", "Текст третьей заметки"));

        NoteAdapter adapter = new NoteAdapter(notes, note ->
                Toast.makeText(this, note.getTitle(), Toast.LENGTH_SHORT).show()
        );

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(24));
    }
}
