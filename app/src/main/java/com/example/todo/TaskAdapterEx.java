package com.example.todo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TaskAdapterEx extends RecyclerView.Adapter<TaskAdapterEx.TaskVh> {
    private FirebaseAuth mAuth;
    Context context;
    List<TaskItem> tasks;
    ListItemClickListener mListItemClickListener;
    String categoryId;

    public TaskAdapterEx(Context context, List<TaskItem> tasks, ListItemClickListener listItemClickListener, String categoryId) {
        this.context = context;
        this.tasks = tasks;
        this.mListItemClickListener = listItemClickListener;
        this.categoryId = categoryId;
    }
    @Override
    public int getItemCount() {
        return tasks.size();
    }


    class TaskVh extends RecyclerView.ViewHolder implements View.OnClickListener{
        ListItemClickListener listItemClickListener;
        TextView listType;
        CheckBox checkBox;

        public TaskVh(@NonNull View itemView, ListItemClickListener listItemClickListener) {
            super(itemView);
            this.listItemClickListener = listItemClickListener;
            checkBox = itemView.findViewById(R.id.checkbox);
            itemView.setOnClickListener(this);
        }

        public void setData(final TaskItem task) {
            checkBox.setText(task.getTitle());
            checkBox.setSelected(task.getIsChecked());
        }

        @Override
        public void onClick(View v) {
            listItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public TaskVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_task, parent, false); //view of item
        return new TaskVh(view, mListItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVh holder, int position) {
        holder.setData(tasks.get(position));
        TaskItem taskEntity = tasks.get(position);
        if(taskEntity.getIsChecked()){
            holder.checkBox.setChecked(true);
            holder.checkBox.setPaintFlags( holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {




                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();


                TaskItem newTask= new TaskItem();
                newTask.setTitle(taskEntity.getTitle());
                newTask.setIsChecked(isChecked);
                newTask.setId(taskEntity.id);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("category").child(categoryId).child("tasks").child(taskEntity.getId()).setValue(newTask);

                

                taskEntity.setIsChecked(isChecked);
                holder.checkBox.setSelected(isChecked);
                if(isChecked){
                    holder.checkBox.setText(taskEntity.getTitle());
                    holder.checkBox.setPaintFlags( holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else {
                    holder.checkBox.setText(taskEntity.getTitle());
                    holder.checkBox.setPaintFlags( holder.checkBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }

            }
        });
    }

    interface ListItemClickListener {
        void onListItemClick(int position);
    }

}