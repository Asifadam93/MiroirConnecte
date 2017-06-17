package com.example.asif.androidclient.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Asif on 09/06/2017.
 */

public class User {

    private Integer id;
    /*@SerializedName("message")
    private String errorMsg;*/
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("photo_name")
    private String photoName;
    @SerializedName("plain_password")
    private String password;

    public User(String firstName, String lastName, String email, String password, String photoName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.photoName = photoName;
    }

    public int getId() {
        return id;
    }

    /*public String getErrorMsg() {
        return errorMsg;
    }*/


    @Override
    public String toString() {
        return "User{" +
                /*"id=" + id +*/
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", photoName='" + photoName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
