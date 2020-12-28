package com.example.todo;

import android.text.Editable;

public class Category {
    String id, title;
    int tasksCount;

    public Category() {
    }

    public Category(String id, String title, int tasksCount) {
        this.title = title;
        this.tasksCount = tasksCount;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return this.tasksCount;
    }

    public void setCount(int tasksCount) {
        this.tasksCount = tasksCount;
    }


}
