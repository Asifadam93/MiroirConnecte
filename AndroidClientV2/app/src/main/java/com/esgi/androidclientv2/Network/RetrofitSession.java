package com.esgi.androidclientv2.Network;

import com.esgi.androidclientv2.Const;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Asif on 28/06/2017.
 */

public class RetrofitSession {

    // TODO: 02/07/2017 add @Exclude annotation
    private static Retrofit retrofit;

    public static Retrofit getInstance() {

        if (retrofit == null) {

            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY));

            retrofit = new Retrofit.Builder()
                    .baseUrl(Const.END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .build();
        }

        return retrofit;
    }
}
