package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Edit extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView title, description;
    String taskId, catId, date;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle extras = getIntent().getExtras();
        taskId = extras.getString("TASK_ID");
        catId = extras.getString("CATEGORY_ID");
        date = extras.getString("DATE");

        title = findViewById(R.id.e_title);
        title.setText(extras.getString("TASK_Title"));
        description = findViewById(R.id.e_description);
        description.setText(extras.getString("TASK_Description"));

        edit = findViewById(R.id.edit_task);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                System.out.println(taskId);
                System.out.println(catId);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").child(catId).child("tasks").child(taskId).child("title").setValue(title.getText().toString());
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").child(catId).child("tasks").child(taskId).child("description").setValue(description.getText().toString());
                Toast.makeText(Edit.this,"Modified successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Edit.this, Lists.class);
                startActivity(intent);
            }
        });



    }
}