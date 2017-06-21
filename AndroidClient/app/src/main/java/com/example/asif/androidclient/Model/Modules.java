package com.example.asif.androidclient.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AAD on 21/06/2017.
 */

public class Modules {

    @SerializedName("id")
    private Integer id;

    @SerializedName("type")
    private String type;

    @SerializedName("name")
    private String name;

    @SerializedName("position")
    private String position;

    @SerializedName("time_zone")
    private String timeZone;

    @SerializedName("city")
    private String cityName;

    @SerializedName("country")
    private String countryCode;


    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String toString() {
        return "Modules{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
