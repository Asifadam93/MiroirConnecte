package com.example.asif.androidclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asif.androidclient.Api.ApiService;
import com.example.asif.androidclient.Const;
import com.example.asif.androidclient.Model.TokenResponse;
import com.example.asif.androidclient.Model.User;
import com.example.asif.androidclient.R;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Asifadam93 on 18/06/2017.
 */

public class ModuleFragment extends Fragment {

    private View view;
    private TextView textViewUserMsg;
    private TokenResponse tokenResponse;
    private User user;
    private CircleImageView circleImageView;
    private Button buttonModule, buttonEditProfile, buttonDeleteProfile;
    private String token;
    private int id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.module_layout, container, false);



        return view;
    }

    private void initViews() {

    }

}