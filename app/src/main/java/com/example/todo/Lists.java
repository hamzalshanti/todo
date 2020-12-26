package com.example.todo;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Lists extends AppCompatActivity {
    static List<ParentTask> tasksList = new ArrayList<>();
    RecyclerView parent_task_rv;
    ParentTaskAdapterEX taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists);
        tasksList.add(new ParentTask("Study Programming",2));
        tasksList.add(new ParentTask("Study",3));
        tasksList.add(new ParentTask("Programming",4));


        parent_task_rv = findViewById(R.id.main_tasks_rv);
        parent_task_rv.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new ParentTaskAdapterEX(tasksList);
        parent_task_rv.setAdapter(taskAdapter);
    }

}