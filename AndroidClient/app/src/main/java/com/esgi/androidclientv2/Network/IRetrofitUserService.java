package com.esgi.androidclientv2.Network;

import com.esgi.androidclientv2.Model.TokenResponse;
import com.esgi.androidclientv2.Model.User;

import java.util.Map;

import okhttp3.ResponseBody;
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

    @POST("/user/{id}/time")
    Call<ResponseBody> addTimeModule(
            @Header("X-Auth-Token") String userToken,
            @Path("id") int id,
            @Body Map<String, String> timeModuleMap
    );

    @PATCH("/user/{id}/time/{moduleId}")
    Call<ResponseBody> updateTimeModule(
            @Header("X-Auth-Token") String userToken,
            @Path("id") int id,
            @Path("moduleId") int moduleId,
            @Body Map<String, String> timeModuleMap
    );

    @DELETE("/user/{id}/time/{moduleId}")
    Call<ResponseBody> deleteTimeModule(
            @Header("X-Auth-Token") String userToken,
            @Path("id") int id,
            @Path("moduleId") int moduleId
    );

    @POST("/user/{id}/weather")
    Call<ResponseBody> addWeatherModule(
            @Header("X-Auth-Token") String userToken,
            @Path("id") int id,
            @Body Map<String, String> weatherModuleMap
    );

    @PATCH("/user/{id}/weather/{moduleId}")
    Call<ResponseBody> updateWeatherModule(
            @Header("X-Auth-Token") String userToken,
            @Path("id") int id,
            @Path("moduleId") int moduleId,
            @Body Map<String, String> weatherModuleMap
    );

    @DELETE("/user/{id}/weather/{moduleId}")
    Call<ResponseBody> deleteWeatherModule(
            @Header("X-Auth-Token") String userToken,
            @Path("id") int id,
            @Path("moduleId") int moduleId
    );
}
