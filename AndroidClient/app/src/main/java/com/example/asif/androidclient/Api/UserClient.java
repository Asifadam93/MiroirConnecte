package com.example.asif.androidclient.Api;

import com.example.asif.androidclient.Model.TokenResponse;
import com.example.asif.androidclient.Model.TokenRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Asifadam93 on 16/06/2017.
 */

public interface UserClient {

    @POST("auth-tokens")
    Call<TokenResponse> loginUser(@Body TokenRequest user);


}
