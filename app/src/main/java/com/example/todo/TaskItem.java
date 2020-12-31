package com.example.todo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskItem {
    String id, title, Description;
    Date date;
    Boolean isChecked;

    public TaskItem() {
    }

    public TaskItem(String id, String title, String Description, Boolean isChecked) {
        this.id = id;
        this.title = title;
        this.Description = Description;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title;}

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) { this.Description = Description;}

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }
}