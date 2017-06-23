package com.example.asif.androidclient.Api;

import com.example.asif.androidclient.Const;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Asifadam93 on 22/06/2017.
 */

public class RestClient {

    private ApiService apiService;

    public RestClient() {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.addInterceptor(loggingInterceptor);

        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(Const.endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();

        apiService = retrofitBuilder.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
