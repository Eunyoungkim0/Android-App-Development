package com.example.room.model;

public class SleepQuality {

    public int int_quality;
    public String str_quality;

    public SleepQuality(int int_quality, String str_quality) {
        this.int_quality = int_quality;
        this.str_quality = str_quality;
    }

    @Override
    public String toString() {
        return str_quality + " (" + int_quality + ")";
    }
}
