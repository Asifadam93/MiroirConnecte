package com.esgi.androidclientv2.Network;

import com.esgi.androidclientv2.Model.TokenResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Asifadam93 on 02/07/2017.
 */

public interface IRetrofitUserService {

    @POST("auth-tokens")
    Call<TokenResponse> login(@Body Map<String, String> user);


}
