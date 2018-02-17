package com.example.emilyhowing.emotiontracker2;

import java.sql.Time;
import java.util.Date;

/**
 * Created by emilyhowing on 4/15/17.
 */

public class Emotion {

    private String date;
    private String time;
    private String emotion;
    private String color;

    public Emotion(String emotion, String date, String time, String color) {
        this.emotion = emotion;
        this.date = date;
        this.time = time;
        this.color = color;
    }

    public String getEmotion() {
        return emotion;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getColor(){
        return color;
    }
}
