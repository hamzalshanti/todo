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

import java.util.List;



class ParentTaskAdapterEX extends RecyclerView.Adapter<ParentTaskAdapterEX.ViewHolder> {
    List<ParentTask> tasks;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, count;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            title = itemView.findViewById(R.id.parent_task_title);
            count = itemView.findViewById(R.id.parent_task_count);
        }

        public void setData(ParentTask task) {
            title.setText(task.getTitle());
            count.setText("" + task.getCount());
        }
    }


    public ParentTaskAdapterEX(List<ParentTask> tasks) {
        this.tasks = tasks;
   }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_main_task, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your list at this position and replace the
        // contents of the view with that element
         viewHolder.setData(tasks.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tasks.size();
    }
}