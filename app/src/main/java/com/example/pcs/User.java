package com.example.pcs;

import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class User {
    private String date, description, pressure, temperature, time;
    public User() {
    }
    public User(String date, String description, String pressure, String temperature, String time) {
        this.date = date;
        this.description = description;
        this.pressure = pressure;
        this.temperature = temperature;
        this.time = time;
    }
    public String getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
    public String getPressure() {
        return pressure;
    }
    public String getTemperature() {
        return temperature;
    }
    public String getTime() {
        return time;
    }
}