package edu.charlotte.assignment13;

public class Weather {
    private double temp;
    private double temp_max;
    private double temp_min;
    private String description;
    private int humidity;
    private double speed;
    private double deg;
    private int all;
    private String icon;


    public Weather(double temperature, double temperatureMax, double temperatureMin, String description, int humidity, double windSpeed, double windDegree, int cloudiness, String iconCode) {
        this.temp = temperature;
        this.temp_max = temperatureMax;
        this.temp_min = temperatureMin;
        this.description = description;
        this.humidity = humidity;
        this.speed = windSpeed;
        this.deg = windDegree;
        this.all = cloudiness;
        this.icon = iconCode;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temperature=" + temp +
                ", temperatureMax=" + temp_max +
                ", temperatureMin=" + temp_min +
                ", description='" + description + '\'' +
                ", humidity=" + humidity +
                ", windSpeed=" + speed +
                ", windDegree=" + deg +
                ", cloudiness=" + all +
                ", iconCode='" + icon + '\'' +
                '}';
    }
}