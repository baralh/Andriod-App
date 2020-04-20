package com.alttab.vcu.storyboard.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;



public class Chunk {

    public static final String FIELD_TEXT = "text";
    public static final String FIELD_TIMESTAMP = "timestamp";
    public static final String FIELD_userId = "userId";

    private String text;
    private @ServerTimestamp
        Date timestamp;
    private String userId;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    private String uname;


    public Chunk() {
    }

    public Chunk(String text, Date timestamp, String userId) {
        this.text = text;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Chunk{"
                + "text='" + text + '\''
                + ", timestamp=" + timestamp
                + ", userId='" + userId + '\''
                + '}';
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}