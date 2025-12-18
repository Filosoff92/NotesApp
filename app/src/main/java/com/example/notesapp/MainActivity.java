package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EDIT = 1;

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(24));

        // Данные
        notes = new ArrayList<>();
        notes.add(new Note("Заметка 1", "Текст первой заметки"));
        notes.add(new Note("Заметка 2", "Текст второй заметки"));

        // Адаптер
        adapter = new NoteAdapter(notes, (note, position) -> {
            // Клик → редактирование заметки
            Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
            intent.putExtra("title", note.getTitle());
            intent.putExtra("text", note.getText());
            intent.putExtra("position", position);
            startActivityForResult(intent, REQUEST_EDIT);
        });

        recyclerView.setAdapter(adapter);

        // Кнопка добавления новой заметки
        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
            // Для новой заметки позиция = -1
            intent.putExtra("position", -1);
            startActivityForResult(intent, REQUEST_EDIT);
        });
    }

    // Получение результата из EditNoteActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("title");
            String text = data.getStringExtra("text");
            int position = data.getIntExtra("position", -1);

            if (title == null || title.isEmpty()) {
                Toast.makeText(this, "Заголовок пустой", Toast.LENGTH_SHORT).show();
                return;
            }

            if (position == -1) {
                // Новая заметка
                notes.add(new Note(title, text));
                adapter.notifyItemInserted(notes.size() - 1);
            } else {
                // Редактирование существующей заметки
                notes.set(position, new Note(title, text));
                adapter.notifyItemChanged(position);
            }
        }
    }
}
