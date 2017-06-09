package com.example.asif.androidclient.Model;

/**
 * Created by Asif on 09/06/2017.
 */

public class User {

    private String firstNam;
    private String lastName;

    public String getFirstNam() {
        return firstNam;
    }

    public void setFirstNam(String firstNam) {
        this.firstNam = firstNam;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstNam='" + firstNam + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
