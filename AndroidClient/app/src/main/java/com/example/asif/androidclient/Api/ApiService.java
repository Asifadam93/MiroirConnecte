package com.example.asif.androidclient.Api;

import com.example.asif.androidclient.Model.TokenResponse;
import com.example.asif.androidclient.Model.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by Asifadam93 on 16/06/2017.
 */

public interface ApiService {

    @POST("auth-tokens")
    Call<TokenResponse> loginUser(
            @Body HashMap<String, String> user
    );

    @Headers({
            "Content-Type: application/json",
            "User-Agent: android_miroir_configurateur "
    })
    @POST("user")
    Call<User> registerUser(@Body User user);

    @DELETE("user/{id}")
    Call<Void> deleteUser(
            @Header("X-Auth-Token") String userToken,
            @Path("id") int id
    );

    @Headers({
            "Content-Type: application/json",
            "User-Agent: android_miroir_configurateur "
    })
    @PATCH("user/{id}")
    Call<User> updateUser(
            @Header("X-Auth-Token") String userToken,
            @Path("id") int id,
            @Body HashMap<String, String> updateMap
    );

    @GET("user/{id}")
    Call<User> getUser(
            @Header("X-Auth-Token") String userToken,
            @Path("id") int id
    );
}