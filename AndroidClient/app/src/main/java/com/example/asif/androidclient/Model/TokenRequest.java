package com.example.asif.androidclient.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Asifadam93 on 16/06/2017.
 */

public class TokenRequest {
    @SerializedName("login")
    private String login;
    @SerializedName("password")
    private String password;

    public TokenRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TokenRequest{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
