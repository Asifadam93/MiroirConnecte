package com.esgi.androidclientv2.Network;

import com.esgi.androidclientv2.Model.TokenResponse;
import com.esgi.androidclientv2.Model.User;

import java.util.Map;

/**
 * Created by Asifadam93 on 02/07/2017.
 */

public interface IUserService {

    void login(Map<String, String> loginUserMap, IServiceResultListener<TokenResponse> iServiceResultListener);

    void register(Map<String, String> registerUserMap, IServiceResultListener<User> iServiceResultListener);

}
