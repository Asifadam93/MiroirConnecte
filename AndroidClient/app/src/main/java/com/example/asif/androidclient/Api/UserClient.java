package com.example.asif.androidclient.Api;

import com.example.asif.androidclient.Model.TokenRequest;
import com.example.asif.androidclient.Model.TokenResponse;
import com.example.asif.androidclient.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by Asifadam93 on 16/06/2017.
 */

public interface UserClient {

    @POST("auth-tokens")
    Call<TokenResponse> loginUser(@Body TokenRequest user);

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
}
