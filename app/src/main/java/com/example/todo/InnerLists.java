package com.example.todo;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InnerLists extends AppCompatActivity implements TaskAdapterEx.ListItemClickListener{
    static List<TaskItem> tasksList = new ArrayList<>();
    private FirebaseAuth mAuth;
    RecyclerView tasks_rv;
    TaskAdapterEx taskAdapter;
    Button addNewTask;
    EditText taskTitle, taskDescription;
    String categoryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inner_lists);

        Bundle extras = getIntent().getExtras();
        categoryId = extras.getString("CATEGORY_ID");

        addNewTask = findViewById(R.id.add_new_task);
        taskTitle = findViewById(R.id.task_title);
        taskDescription = findViewById(R.id.task_description);
        addNewTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                TaskItem newTask = new TaskItem();
                newTask.setTitle(taskTitle.getText().toString());
                newTask.setDescription(taskDescription.getText().toString());
                newTask.setIsChecked(false);
                String taskId = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").child(categoryId).child("tasks").push().getKey();
                newTask.setId(taskId);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").child(categoryId).child("tasks").child(taskId).setValue(newTask);
                Toast.makeText(InnerLists.this,"Task has been added successfully", Toast.LENGTH_SHORT).show();
                taskTitle.setText("");
                taskDescription.setText("");
            }
        });

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").child(categoryId).child("tasks")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tasksList.clear();
                        for(DataSnapshot snapshot: dataSnapshot.getChildren() ){
                            TaskItem taskItem =  snapshot.getValue(TaskItem.class);
                            tasksList.add(taskItem);
                        }
                        taskAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

        tasks_rv = findViewById(R.id.tasks_rv);
        tasks_rv.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapterEx(this , tasksList, this);
        tasks_rv.setAdapter(taskAdapter);
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(InnerLists.this, ViewTask.class);
        intent.putExtra("TASK_ID", tasksList.get(position).getId());
        intent.putExtra("Task_Title", tasksList.get(position).getTitle());
        intent.putExtra("Task_Description", tasksList.get(position).getDescription());
        startActivity(intent);
    }
}