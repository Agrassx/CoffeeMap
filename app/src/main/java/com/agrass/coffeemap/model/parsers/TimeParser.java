package com.agrass.coffeemap.model.parsers;

public class TimeParser {

    private int hour;
    private int minute;
    private String spliter = ":";

    public TimeParser(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        String minute = String.valueOf(this.minute);
        String hour = String.valueOf(this.hour);

        if (this.minute < 10) {
            minute = "0" + this.minute;
        }

        if (this.hour < 10) {
            hour = "0" + this.hour;
        }

        return hour + spliter + minute;
    }
}
