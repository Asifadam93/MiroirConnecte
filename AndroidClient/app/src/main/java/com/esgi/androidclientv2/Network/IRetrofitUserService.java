package com.esgi.androidclientv2.Network;

import com.esgi.androidclientv2.Model.Module;
import com.esgi.androidclientv2.Model.TokenResponse;
import com.esgi.androidclientv2.Model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Asifadam93 on 02/07/2017.
 */

public interface IRetrofitUserService {

    @POST("auth-tokens")
    Call<TokenResponse> login(@Body Map<String, String> user);

    @POST("user")
    Call<User> registerUser(@Body Map<String, String> user);

    @DELETE("user/{id}")
    Call<Void> deleteUser(
            @Header("X-Auth-Token") String userToken,
            @Path("id") int id
    );

    @PATCH("user/{id}")
    Call<User> updateUser(
            @Header("X-Auth-Token") String userToken,
            @Path("id") int id,
            @Body Map<String, String> updateMap
    );

    @GET("/user/{id}")
    Call<User> getUserModules(
            @Header("X-Auth-Token") String userToken,
            @Path("id") int id
    );
}
