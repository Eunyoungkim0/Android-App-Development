package com.example.room.model;

import com.anychart.chart.common.dataentry.DataEntry;

import java.util.ArrayList;
import java.util.List;

public class Chart {
    String title, color;
    List<DataEntry> seriesData = new ArrayList<>();

    public Chart(String title, List<DataEntry> seriesData, String color) {
        this.title = title;
        this.seriesData = seriesData;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DataEntry> getSeriesData() {
        return seriesData;
    }

    public void setSeriesData(List<DataEntry> seriesData) {
        this.seriesData = seriesData;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
