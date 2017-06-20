package com.example.asif.androidclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.androidclient.Api.UserClient;
import com.example.asif.androidclient.Const;
import com.example.asif.androidclient.Model.TokenRequest;
import com.example.asif.androidclient.Model.TokenResponse;
import com.example.asif.androidclient.Model.User;
import com.example.asif.androidclient.R;

import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AAD on 14/06/2017.
 */

public class LoginFragment extends Fragment {

    private static FragmentManager fragmentManager; // TODO: 20/06/2017 use parcelable instead
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;
    private View view;
    private LinearLayout loginLayout;
    public static TokenResponse tokenResponse; // used in UserFragment

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userVerification();
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.frameContainer, new RegisterFragment(), "RegisterFragment").commit();
            }
        });

        return view;
    }

    private void initViews() {

        fragmentManager = getActivity().getFragmentManager();

        editTextEmail = (EditText) view.findViewById(R.id.login_email);
        editTextPassword = (EditText) view.findViewById(R.id.login_mdp);
        buttonLogin = (Button) view.findViewById(R.id.login_button);
        textViewRegister = (TextView) view.findViewById(R.id.login_inscription);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        // TODO: 14/06/2017  Setting text selector over textviews
    }

    private void userVerification() {

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.length() == 0) {
            editTextEmail.setError(getString(R.string.champ_vide));
            return;
        }

        if (password.length() == 0) {
            editTextPassword.setError(getString(R.string.champ_vide));
            return;
        }

        //create user object
        TokenRequest user = new TokenRequest(email, password);

        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(Const.endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserClient userClient = retrofitBuilder.create(UserClient.class);

        final Call<TokenResponse> userCall = userClient.loginUser(user);

        userCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                int responseCode = response.code();

                switch (responseCode) {

                    case HttpsURLConnection.HTTP_CREATED:

                        tokenResponse = response.body();

                        //Const.token = response.body().getToken(); // store received token for future use
                        launchUserActivity();
                        break;

                    case HttpURLConnection.HTTP_BAD_REQUEST:
                        Toast.makeText(getActivity(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(getActivity(), "Response error : " + response.code(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Request failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchUserActivity() {

        Toast.makeText(getActivity(), R.string.conn_ok, Toast.LENGTH_SHORT).show();

        getActivity().getFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, new UserFragment(), "UserFragment")
                .commit();
    }
}