package com.example.watchmovie.model;

public class Comment {
    String displayName;
    String comment;
    String dateTime;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Comment(String displayName, String comment, String dateTime) {
        this.displayName = displayName;
        this.comment = comment;
        this.dateTime = dateTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
