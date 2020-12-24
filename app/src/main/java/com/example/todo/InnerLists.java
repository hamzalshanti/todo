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

public class InnerLists extends AppCompatActivity {
    static List<TaskItem> tasksList = new ArrayList<>();
    RecyclerView tasks_rv;
    TaskAdapterEx taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inner_lists);
        tasksList.add(new TaskItem("Study Programming",false));
        tasksList.add(new TaskItem("Study",true));
        tasksList.add(new TaskItem("Programming",true));


        tasks_rv = findViewById(R.id.tasks_rv);
        tasks_rv.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapterEx(this , tasksList);
        tasks_rv.setAdapter(taskAdapter);
    }

}