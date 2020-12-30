package com.example.todo;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
    TextView relateCategory, deleteList;
    String categoryId, categoryTitle;
    Integer categoryCount;
    boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inner_lists);

        Bundle extras = getIntent().getExtras();
        categoryId = extras.getString("CATEGORY_ID");
        categoryTitle = extras.getString("CATEGORY_TITLE");

        addNewTask = findViewById(R.id.add_new_task);
        taskTitle = findViewById(R.id.task_title);
        taskDescription = findViewById(R.id.task_description);
        deleteList = findViewById(R.id.delete_list);
        relateCategory = findViewById(R.id.relate_category);
        relateCategory.setText(categoryTitle);

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



                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category")
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for(DataSnapshot snapshot: dataSnapshot.getChildren() ){
                            Category categoryItem =  snapshot.getValue(Category.class);
                            if(categoryItem.getId().compareTo(categoryId) == 0 && flag){
                                categoryCount = categoryItem.getCount();
                                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").child(categoryId).child("count").setValue(++categoryCount);
                                flag = false;
                                break;
                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });

                // change count when add task

                Toast.makeText(InnerLists.this,"Task has been added successfully", Toast.LENGTH_SHORT).show();
                flag = true;
                taskTitle.setText("");
                taskDescription.setText("");
            }


        });

        deleteList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").child(categoryId).removeValue();
                Toast.makeText(InnerLists.this,"Delete Done", Toast.LENGTH_SHORT).show();
                finish();
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
        taskAdapter = new TaskAdapterEx(this , tasksList, this, categoryId);
        tasks_rv.setAdapter(taskAdapter);
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(InnerLists.this, ViewTask.class);
        intent.putExtra("TASK_ID", tasksList.get(position).getId());
        intent.putExtra("TASK_Title", tasksList.get(position).getTitle());
        intent.putExtra("TASK_Description", tasksList.get(position).getDescription());
        intent.putExtra("CATEGORY_TITLE", categoryTitle);
        intent.putExtra("CATEGORY_ID", categoryId);
        startActivity(intent);
    }
}