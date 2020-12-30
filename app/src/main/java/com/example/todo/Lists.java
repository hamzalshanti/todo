package com.example.todo;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Lists extends AppCompatActivity implements ListsAdapter.ListItemClickListener {
    static List<Category> categoriesList = new ArrayList<>();
    private FirebaseAuth mAuth;
    RecyclerView parent_task_rv;
    ListsAdapter taskAdapter;
    Button addNewCategory;
    EditText category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists);

        addNewCategory = findViewById(R.id.add_new_category);
        category = findViewById(R.id.category);


        addNewCategory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                Category newCategory = new Category();
                newCategory.setTitle(category.getText().toString());
                newCategory.setCount(0);
                String categoryId = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").push().getKey();
                newCategory.setId(categoryId);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").child(categoryId).setValue(newCategory);
                Toast.makeText(Lists.this,"Category has been added successfully", Toast.LENGTH_SHORT).show();
                category.setText("");
            }
        });

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        categoriesList.clear();
                        for(DataSnapshot snapshot: dataSnapshot.getChildren() ){
                            Category categoryItem =  snapshot.getValue(Category.class);
                            categoriesList.add(categoryItem);
                        }
                        taskAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
        parent_task_rv = findViewById(R.id.main_tasks_rv);
        parent_task_rv.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new ListsAdapter(categoriesList, this);
        parent_task_rv.setAdapter(taskAdapter);
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(Lists.this, InnerLists.class);
        intent.putExtra("CATEGORY_ID", categoriesList.get(position).getId());
        intent.putExtra("CATEGORY_TITLE", categoriesList.get(position).getTitle());
        startActivity(intent);
    }
}