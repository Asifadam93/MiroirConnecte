package com.esgi.androidclientv2.Network;

import com.esgi.androidclientv2.Model.TokenResponse;
import com.esgi.androidclientv2.Model.User;

import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Asifadam93 on 02/07/2017.
 */

public interface IUserService {

    void login(Map<String, String> loginUserMap, IServiceResultListener<TokenResponse> iServiceResultListener);

    void register(Map<String, String> registerUserMap, IServiceResultListener<User> iServiceResultListener);

    void delete(String token, int userId, IServiceResultListener<String> iServiceResultListener);

    void update(String token, int userId, Map<String, String> updateUserMap, IServiceResultListener<User> iServiceResultListener);

    void getUserModules(String token, int userId, IServiceResultListener<User> iServiceResultListener);

    void addTimeModule(String token, int userId, Map<String, String> timeModuleMap, IServiceResultListener<ResponseBody> iServiceResultListener);

    void deleteTimeModule(String token, int userId, int moduleId, IServiceResultListener<ResponseBody> iServiceResultListener);

}
