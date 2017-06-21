package com.example.asif.androidclient.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Asif on 09/06/2017.
 */

public class User {

    private Integer id;
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
    @SerializedName("modules")
    private Modules[] modules;

    public User(String firstName, String lastName, String email, String password, String photoName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.photoName = photoName;
    }

    public User(String firstName, String lastName, String email, String photoName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photoName = photoName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoName() {
        return photoName;
    }

    public String getPassword() {
        return password;
    }

    public Modules[] getModules() {
        return modules;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", photoName='" + photoName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
