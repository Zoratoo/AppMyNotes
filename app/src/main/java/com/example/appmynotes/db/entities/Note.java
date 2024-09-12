package com.example.appmynotes.db.entities;

import java.time.LocalDate;

public class Note {
    private int id;
    private String title;
    private String text;
    private int priority;

    public Note(int id, String title, String text, int priority) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.priority = priority;
    }

    public Note() {
        this(0,"","",0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
