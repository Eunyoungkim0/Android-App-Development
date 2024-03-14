package edu.uncc.weatherapp.models;

public class Forecast {
    String startTime;
    double temperature;
    RelativeHumidity relativeHumidity;
    String windSpeed;
    String shortForecast;
    String icon;

    public Forecast(String startTime, double temperature, RelativeHumidity relativeHumidity, String windSpeed, String shortForecast, String icon) {
        this.startTime = startTime;
        this.temperature = temperature;
        this.relativeHumidity = relativeHumidity;
        this.windSpeed = windSpeed;
        this.shortForecast = shortForecast;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public RelativeHumidity getRelativeHumidity() {
        return relativeHumidity;
    }

    public int getHumidity(){
        return (int) this.relativeHumidity.value;
    }

    public void setRelativeHumidity(RelativeHumidity relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getShortForecast() {
        return shortForecast;
    }

    public void setShortForecast(String shortForecast) {
        this.shortForecast = shortForecast;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "startTime='" + startTime + '\'' +
                ", temperature=" + temperature +
                ", relativeHumidity=" + relativeHumidity +
                ", windSpeed='" + windSpeed + '\'' +
                ", shortForecast='" + shortForecast + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    public class RelativeHumidity {
        public String unitCode;
        public double value;
    }
}
