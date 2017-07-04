package com.esgi.androidclientv2.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Asifadam93 on 04/07/2017.
 */

public class RestError {

    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String errorDetails;

    public RestError() {
        this.code = 0;
        this.errorDetails = "Unknown error";
    }

    public RestError(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public int getCode() {
        return code;
    }

    public String getErrorDetails() {
        return errorDetails;
    }
}
