package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewTask extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView title, description, catTitle, delete, date, edit;
    String intentTitle, intentDescription, intentId, intentCategory, intentCategoryId, inDate;
    Count countInstance;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task);

        Bundle extras = getIntent().getExtras();
        intentTitle = extras.getString("TASK_Title");
        intentDescription = extras.getString("TASK_Description");
        intentId = extras.getString("TASK_ID");
        intentCategory = extras.getString("CATEGORY_TITLE");
        intentCategoryId = extras.getString("CATEGORY_ID");
        inDate = extras.getString("DATE");

        title = findViewById(R.id.view_task_title);
        description = findViewById(R.id.view_task_description);
        catTitle = findViewById(R.id.cat_title);
        date = findViewById(R.id.date);
        delete = findViewById(R.id.delete);
        edit = findViewById(R.id.edit);

        title.setText(intentTitle);
        description.setText(intentDescription);
        catTitle.setText(intentCategory);
        date.setText(inDate);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").child(intentCategoryId).child("tasks").child(intentId).removeValue();
                countInstance = new Count(uid, intentCategoryId, intentId);
                countInstance.setListsCount("-");
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(ViewTask.this, Edit.class);
                intent.putExtra("TASK_ID", intentId);
                intent.putExtra("TASK_Title", intentTitle);
                intent.putExtra("TASK_Description", intentDescription);
                intent.putExtra("CATEGORY_ID", intentCategoryId);
                startActivity(intent);
            }
        });

    }
}
