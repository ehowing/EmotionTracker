package com.example.emilyhowing.emotiontracker2;

/**
 * Created by emilyhowing on 4/28/17.
 */

public class Comment {
    private String text;
    private String date;
    private String time;
    private String userId;

    public Comment(String text, String date, String time, String userId) {
        this.text = text;
        this.date = date;
        this.time = time;
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUserId() {
        return userId;
    }
}
