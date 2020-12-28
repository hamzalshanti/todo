package com.example.todo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewTask extends AppCompatActivity {

    TextView title, description;
    String intentTitle, intentDescription, intentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task);

        Bundle extras = getIntent().getExtras();
        intentTitle = extras.getString("Task_Title");
        intentDescription = extras.getString("Task_Description");
        intentId = extras.getString("Task_ID");

        title = findViewById(R.id.view_task_title);
        description = findViewById(R.id.view_task_description);


        title.setText(intentTitle);
        description.setText(intentDescription);

    }
}
