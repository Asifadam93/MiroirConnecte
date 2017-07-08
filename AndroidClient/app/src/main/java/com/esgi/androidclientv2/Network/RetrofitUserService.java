package com.esgi.androidclientv2.Network;

import android.util.Log;

import com.esgi.androidclientv2.Model.TokenResponse;
import com.esgi.androidclientv2.Model.User;

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

        getRetrofitUserService().login(loginUserMap).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                Log.i("RetrofitUserService", "" + response.code());

                ServiceResult<TokenResponse> result = new ServiceResult<>();

                if (response.isSuccessful()) {
                    result.setData(response.body());
                } else {
                    // parsing server error msg
                    /*Converter<ResponseBody, RestError> converter = RetrofitSession.getInstance()
                            .responseBodyConverter(RestError.class, new Annotation[0]);
                    try {
                        RestError error = converter.convert(response.errorBody());
                        result.setRestError(error);
                    } catch (IOException e) {
                        result.setRestError(new RestError());
                    }*/

                    switch (response.code()) {

                        case 400:
                            result.setErrorMsg("Identifiant et/ou mot de passe incorrect");
                            break;

                        default:
                            result.setErrorMsg("Erreur de connexion");
                    }

                }

                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(result);
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

                Log.i("RetrofitUserService", "Error : " + t.getMessage());

                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(new ServiceResult<TokenResponse>(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void register(Map<String, String> registerUserMap, final IServiceResultListener<User> iServiceResultListener) {

        getRetrofitUserService().registerUser(registerUserMap).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                ServiceResult<User> result = new ServiceResult<User>();

                if (response.isSuccessful()) {
                    result.setData(response.body());
                } else {

                    switch (response.code()) {

                        case 400:
                            result.setErrorMsg("Utilisateur existe déjà");
                            break;

                        default:
                            result.setErrorMsg("Erreur d'inscription");

                    }
                }

                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(result);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(new ServiceResult<User>(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void delete(String token, int userId, final IServiceResultListener<String> iServiceResultListener) {

        getRetrofitUserService().deleteUser(token, userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                ServiceResult<String> result = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    result.setData("Deleted");
                } else {
                    result.setErrorMsg("Erreur : Suppression");
                }

                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(result);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }

            }
        });

    }

    @Override
    public void update(String token, int userId, Map<String, String> updateUserMap, final IServiceResultListener<User> iServiceResultListener) {

        getRetrofitUserService().updateUser(token, userId, updateUserMap).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                ServiceResult<User> result = new ServiceResult<User>();

                if (response.isSuccessful()) {

                    result.setData(response.body());

                } else {
                    result.setErrorMsg("Mise à jour impossible");
                }

                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(result);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(new ServiceResult<User>(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getUser(String token, int userId, final IServiceResultListener<User> iServiceResultListener) {

        getRetrofitUserService().getUser(token, userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                ServiceResult<User> result = new ServiceResult<User>();

                if (response.isSuccessful()) {
                    result.setData(response.body());
                } else {
                    result.setErrorMsg("Erreur : Chargement profil utilisateur");
                }

                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(result);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (iServiceResultListener != null) {
                    iServiceResultListener.onResult(new ServiceResult<User>(t.getMessage()));
                }
            }
        });

    }
}
