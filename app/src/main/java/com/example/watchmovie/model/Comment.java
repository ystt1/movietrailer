package com.example.watchmovie.model;

public class Comment {
    String displayName;
    String comment;

    public Comment(String displayName, String comment) {
        this.displayName = displayName;
        this.comment = comment;
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
