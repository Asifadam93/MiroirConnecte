package com.esgi.androidclientv2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AAD on 21/06/2017.
 */

public class Module implements Parcelable {

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
        return "Module{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

    protected Module(Parcel in) {
        type = in.readString();
        name = in.readString();
        position = in.readString();
        timeZone = in.readString();
        cityName = in.readString();
        countryCode = in.readString();
    }

    public static final Creator<Module> CREATOR = new Creator<Module>() {
        @Override
        public Module createFromParcel(Parcel in) {
            return new Module(in);
        }

        @Override
        public Module[] newArray(int size) {
            return new Module[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(name);
        parcel.writeString(position);
        parcel.writeString(timeZone);
        parcel.writeString(cityName);
        parcel.writeString(countryCode);
    }
}
