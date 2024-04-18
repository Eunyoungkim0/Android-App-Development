package com.example.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userlog")
public class UserLog {
    @PrimaryKey(autoGenerate = true)
    public int wid;
    @ColumnInfo(name = "date")
    public Long date;
    @ColumnInfo(name = "sleepHours")
    public double sleepHours;
    @ColumnInfo(name = "sleepQuality")
    public int sleepQuality;
    @ColumnInfo(name = "exerciseHours")
    public double exerciseHours;
    @ColumnInfo(name = "weight")
    public double weight;

    public UserLog(){

    }

    public UserLog(Long date, double sleepHours, int sleepQuality, double exerciseHours, double weight) {
        this.date = date;
        this.sleepHours = sleepHours;
        this.sleepQuality = sleepQuality;
        this.exerciseHours = exerciseHours;
        this.weight = weight;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public double getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(double sleepHours) {
        this.sleepHours = sleepHours;
    }

    public int getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(int sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    public double getExerciseHours() {
        return exerciseHours;
    }

    public void setExerciseHours(double exerciseHours) {
        this.exerciseHours = exerciseHours;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "wid=" + wid +
                ", date=" + date +
                ", sleepHours=" + sleepHours +
                ", sleepQuality=" + sleepQuality +
                ", exerciseHours=" + exerciseHours +
                ", weight=" + weight +
                '}';
    }
}
