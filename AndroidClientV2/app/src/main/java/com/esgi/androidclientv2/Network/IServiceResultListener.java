package com.esgi.androidclientv2.Network;

/**
 * Created by Asifadam93 on 03/07/2017.
 */

public interface IServiceResultListener<T> {

    void onResult(ServiceResult<T> result);

}
