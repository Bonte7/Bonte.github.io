package com.Bonte.MyJournal;

import com.google.firebase.Timestamp;

public class JournalEntry {
    private long id;
    private String title;
    private String content;
    private String date;
    private String time;
    private Timestamp timeAdded;
    private String imageUrl;
    private String userId;
    private String userName;

    JournalEntry(String title, String content, String date, String time){
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    JournalEntry(long id, String title, String content, String date, String time){
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }
    JournalEntry(){
       // empty constructor
    }

    public JournalEntry(String title, String content, Timestamp timeAdded, String imageUrl, String userId) {
        this.title = title;
        this.content = content;
        this.timeAdded = timeAdded;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }
}
