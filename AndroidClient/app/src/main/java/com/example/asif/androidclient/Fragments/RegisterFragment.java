package com.example.asif.androidclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.androidclient.Api.UserClient;
import com.example.asif.androidclient.Const;
import com.example.asif.androidclient.Model.User;
import com.example.asif.androidclient.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AAD on 15/06/2017.
 */

public class RegisterFragment extends Fragment {

    private View view;
    private EditText editTextNom, editTextPrenom, editTextEmail, editTextMdp, editTextConfirmMdp,
            editTextPhotoCode;
    private Button buttonInscription;
    private TextView textViewConnexion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_layout, container, false);
        initView();

        buttonInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userVerification();
            }
        });

        // goto login screen
        textViewConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginFragment();
            }
        });

        return view;
    }

    private void showLoginFragment() {
        getActivity().getFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, new LoginFragment(), "LoginFragment")
                .commit();
    }

    private void initView() {
        editTextNom = (EditText) view.findViewById(R.id.inscription_nom);
        editTextPrenom = (EditText) view.findViewById(R.id.inscription_prenom);
        editTextEmail = (EditText) view.findViewById(R.id.inscription_email);
        editTextMdp = (EditText) view.findViewById(R.id.inscription_mdp);
        editTextConfirmMdp = (EditText) view.findViewById(R.id.inscription_confirmation_mdp);
        editTextPhotoCode = (EditText) view.findViewById(R.id.inscription_photo_code);
        buttonInscription = (Button) view.findViewById(R.id.inscription_button);
        textViewConnexion = (TextView) view.findViewById(R.id.inscription_connexion);
    }

    private void userVerification() {

        // get user info from editTexts
        String nom = editTextNom.getText().toString();
        String prenom = editTextPrenom.getText().toString();
        String email = editTextEmail.getText().toString();
        String mdp = editTextMdp.getText().toString();
        String confirmMdp = editTextConfirmMdp.getText().toString();
        String photoCode = editTextPhotoCode.getText().toString();

        // empty field verification
        if (nom.isEmpty()) {
            editTextNom.setError(getString(R.string.champ_vide));
            return;
        }

        if (prenom.isEmpty()) {
            editTextPrenom.setError(getString(R.string.champ_vide));
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.champ_vide));
            return;
        }

        if (mdp.isEmpty()) {
            editTextMdp.setError(getString(R.string.champ_vide));
            return;
        }

        if (confirmMdp.isEmpty()) {
            editTextConfirmMdp.setError(getString(R.string.champ_vide));
            return;
        }

        if (photoCode.isEmpty()) {
            editTextPhotoCode.setError(getString(R.string.champ_vide));
            return;
        }

        // password comparison
        if (!mdp.equals(confirmMdp)) {
            editTextMdp.setError(getString(R.string.diff_mdp));
            return;
        }

        User user = new User(prenom, nom, email, mdp, photoCode);

        registerUser(user);

    }

    private void registerUser(User user) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserClient userClient = retrofit.create(UserClient.class);

        Call<User> userCall = userClient.registerUser(user);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                // isSuccess is true if response code => 200 and <= 300
                if (!response.isSuccessful()) {
                    // print response body if unsuccessful
                    try {
                        Log.i("registerFrag", response.errorBody().string());
                        Toast.makeText(getActivity(), "Error : " + response.code(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        // do nothing
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.inscription_ok, Toast.LENGTH_SHORT).show();
                    showLoginFragment();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Request failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}