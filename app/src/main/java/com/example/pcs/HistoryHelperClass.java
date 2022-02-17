package com.example.pcs;

public class HistoryHelperClass {

    String Date, Time, Pressure, Temperature, Description;
    public HistoryHelperClass(){
    }

    public HistoryHelperClass(String date, String time, String pressure, String temperature, String description) {
        Date = date;
        Time = time;
        Pressure = pressure;
        Temperature = temperature;
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPressure() {
        return Pressure;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
