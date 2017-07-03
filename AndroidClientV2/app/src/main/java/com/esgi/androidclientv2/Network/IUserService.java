package com.esgi.androidclientv2.Network;

import com.esgi.androidclientv2.Model.TokenResponse;

import java.util.Map;

/**
 * Created by Asifadam93 on 02/07/2017.
 */

public interface IUserService {

    void login(Map<String,String> loginUserMap, IServiceResultListener<TokenResponse> iServiceResultListener);

}
