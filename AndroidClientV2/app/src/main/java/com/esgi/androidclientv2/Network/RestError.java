package com.esgi.androidclientv2.Network;

/**
 * Created by Asifadam93 on 03/07/2017.
 */

public class RestError {
    private int code = 0;
    private String message;

    public RestError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestError(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
