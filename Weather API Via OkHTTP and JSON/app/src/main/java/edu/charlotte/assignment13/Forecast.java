package edu.charlotte.assignment13;

public class Forecast {
    private String dateTime;
    private double temp;
    private double temp_max;
    private double temp_min;
    private int humidity;
    private String description;
    private String icon;

    public Forecast(String dateTime, double temp, double temp_max, double temp_min, int humidity, String description, String icon) {
        this.dateTime = dateTime;
        this.temp = temp;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.humidity = humidity;
        this.description = description;
        this.icon = icon;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "dateTime='" + dateTime + '\'' +
                ", temp=" + temp +
                ", temp_max=" + temp_max +
                ", temp_min=" + temp_min +
                ", humidity=" + humidity +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
