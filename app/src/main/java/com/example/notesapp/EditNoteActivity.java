package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    private EditText etTitle, etText;
    private int position; // позиция заметки в списке

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        etTitle = findViewById(R.id.etTitle);
        etText = findViewById(R.id.etText);
        Button btnSave = findViewById(R.id.btnSave);

        // Получаем данные из Intent (если редактируем существующую заметку)
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");

        if (title != null) etTitle.setText(title);
        if (text != null) etText.setText(text);

        // Сохранение заметки
        btnSave.setOnClickListener(v -> {
            String newTitle = etTitle.getText().toString().trim();
            String newText = etText.getText().toString().trim();

            if (newTitle.isEmpty()) {
                etTitle.setError("Заголовок не может быть пустым");
                etTitle.requestFocus();
                return;
            }

            Intent result = new Intent();
            result.putExtra("title", newTitle);
            result.putExtra("text", newText);
            result.putExtra("position", position);

            setResult(RESULT_OK, result);
            finish();
        });
    }
}
