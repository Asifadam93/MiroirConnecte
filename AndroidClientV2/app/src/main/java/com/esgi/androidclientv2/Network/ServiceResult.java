package com.esgi.androidclientv2.Network;

/**
 * Created by Asifadam93 on 03/07/2017.
 */

public class ServiceResult<T> {

    private T data;
    private RestError restError;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public RestError getRestError() {
        return restError;
    }

    public void setRestError(RestError restError) {
        this.restError = restError;
    }
}
