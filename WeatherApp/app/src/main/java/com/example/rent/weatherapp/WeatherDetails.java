package com.example.rent.weatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by RENT on 2017-03-15.
 */

public class WeatherDetails {

    @SerializedName("location")
    private String location;

    @SerializedName("temperature")
    private String temperature;

    @SerializedName("skytext")
    private String skyText;

    @SerializedName("humidity")
    private String humidity;

    @SerializedName("wind")
    private String wind;

    @SerializedName("date")
    private String date;

    @SerializedName("day")
    private String day;

    public WeatherDetails(String location, String temperature, String skyText, String humidity, String wind, String date, String day) {
        this.location = location;
        this.temperature = temperature;
        this.skyText = skyText;
        this.humidity = humidity;
        this.wind = wind;
        this.date = date;
        this.day = day;
    }

    public String getLocation() {
        return location;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getSkyText() {
        return skyText;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind() {
        return wind;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherDetails weatherDetails = (WeatherDetails) o;
        return Objects.equals(location, weatherDetails.location) &&
                Objects.equals(temperature, weatherDetails.temperature) &&
                Objects.equals(skyText, weatherDetails.skyText) &&
                Objects.equals(humidity, weatherDetails.humidity) &&
                Objects.equals(wind, weatherDetails.wind) &&
                Objects.equals(date, weatherDetails.date) &&
                Objects.equals(day, weatherDetails.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, temperature, skyText, humidity, wind, date, day);
    }
}
