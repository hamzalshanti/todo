package com.example.todo;

public class ParentTask {
    String title;
    int tasksCount;

    public ParentTask() {
    }

    public ParentTask(String title, int tasksCount) {
        this.title = title;
        this.tasksCount = tasksCount;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        title = this.title;
    }

    public int getCount() {
        return this.tasksCount;
    }

    public void setCount(int tasksCount) {
        this.tasksCount = tasksCount;
    }
}
