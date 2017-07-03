package com.esgi.androidclientv2.Network;

import android.util.Log;

import com.esgi.androidclientv2.Model.TokenResponse;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Asifadam93 on 02/07/2017.
 */

public class RetrofitUserService implements IUserService {

    private IRetrofitUserService retrofitUserService;

    private IRetrofitUserService getRetrofitUserService() {
        if (retrofitUserService == null) {
            retrofitUserService = RetrofitSession.getInstance().create(IRetrofitUserService.class);
        }
        return retrofitUserService;
    }

    @Override
    public void login(Map<String, String> loginUserMap, final IServiceResultListener<TokenResponse> iServiceResultListener) {

        Call<TokenResponse> tokenResponseCall = getRetrofitUserService().login(loginUserMap);

        tokenResponseCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                Log.i("RetrofitUserService", "" + response.code());

                ServiceResult<TokenResponse> serviceResult = new ServiceResult<>();

                if (response.isSuccessful()) {
                    serviceResult.setData(response.body());
                } else {
                    try {
                        serviceResult.setRestError(new RestError(response.code(), response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(serviceResult);
                }

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

                Log.i("RetrofitUserService", "Error : " + t.getMessage());

                ServiceResult<TokenResponse> serviceResult = new ServiceResult<>();
                serviceResult.setRestError(new RestError(t.getMessage()));

                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(serviceResult);
                }
            }
        });
    }
}
