package com.esgi.androidclientv2.Network;

import android.util.Log;

import com.esgi.androidclientv2.Model.RestError;
import com.esgi.androidclientv2.Model.TokenResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
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

                ServiceResult<TokenResponse> result = new ServiceResult<>();

                if (response.isSuccessful()) {
                    result.setData(response.body());
                } else {
                    // parsing server error msg
                    Converter<ResponseBody, RestError> converter = RetrofitSession.getInstance()
                            .responseBodyConverter(RestError.class, new Annotation[0]);
                    try {
                        RestError error = converter.convert(response.errorBody());
                        result.setRestError(error);
                    } catch (IOException e) {
                        result.setRestError(new RestError());
                    }
                }

                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(result);
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
