package com.esgi.androidclientv2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Asifadam93 on 16/06/2017.
 */

public class TokenResponse implements Parcelable {

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

    public User getUser() {
        return userList;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", userList=" + userList +
                '}';
    }

    protected TokenResponse(Parcel in) {
        token = in.readString();
        createdTime = in.readString();
        userList = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<TokenResponse> CREATOR = new Creator<TokenResponse>() {
        @Override
        public TokenResponse createFromParcel(Parcel in) {
            return new TokenResponse(in);
        }

        @Override
        public TokenResponse[] newArray(int size) {
            return new TokenResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(token);
        parcel.writeString(createdTime);
        parcel.writeParcelable(userList, i);
    }
}
