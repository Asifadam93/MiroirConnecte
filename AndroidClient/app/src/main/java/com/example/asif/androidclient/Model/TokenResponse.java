package com.example.asif.androidclient.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Asifadam93 on 16/06/2017.
 */

public class TokenResponse implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("value")
    private String token;
    @SerializedName("created_at")
    private String createdTime;
    @SerializedName("user")
    private User userList;

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public User getUserList() {
        return userList;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }
}
