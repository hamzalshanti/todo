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
    TextView title, description, catTitle, delete;
    String intentTitle, intentDescription, intentId, intentCategory, intentCategoryId;
    Integer categoryCount;
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

        title = findViewById(R.id.view_task_title);
        description = findViewById(R.id.view_task_description);
        catTitle = findViewById(R.id.cat_title);
        delete = findViewById(R.id.delete);

        title.setText(intentTitle);
        description.setText(intentDescription);
        catTitle.setText(intentCategory);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").child(intentCategoryId).child("tasks").child(intentId).removeValue();




                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.
                                for(DataSnapshot snapshot: dataSnapshot.getChildren() ){
                                    Category categoryItem =  snapshot.getValue(Category.class);
                                    if(categoryItem.getId().compareTo(intentCategoryId) == 0 && flag){
                                        categoryCount = categoryItem.getCount();
                                        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").child(intentCategoryId).child("count").setValue(--categoryCount);
                                        flag = false;
                                        break;
                                    }

                                }

                                finish();


                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                            }
                        });


            }
        });

    }
}
