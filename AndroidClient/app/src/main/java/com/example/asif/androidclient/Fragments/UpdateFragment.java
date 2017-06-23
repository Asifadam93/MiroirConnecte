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
import android.widget.Toast;

import com.example.asif.androidclient.Api.ApiService;
import com.example.asif.androidclient.Const;
import com.example.asif.androidclient.Model.TokenResponse;
import com.example.asif.androidclient.Model.User;
import com.example.asif.androidclient.R;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AAD on 15/06/2017.
 */

public class UpdateFragment extends Fragment {

    private View view;
    private EditText editTextNom, editTextPrenom, editTextEmail,
            editTextPhotoCode;
    private Button buttonUpdate;
    private User user;
    private TokenResponse tokenResponse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.update_layout, container, false);

        /*tokenResponse = LoginFragment.tokenResponse;
        user = tokenResponse.getUser();*/

        initView();
        setUserValues();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateVerification();
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
        editTextNom = (EditText) view.findViewById(R.id.update_nom);
        editTextPrenom = (EditText) view.findViewById(R.id.update_prenom);
        editTextEmail = (EditText) view.findViewById(R.id.update_email);
        editTextPhotoCode = (EditText) view.findViewById(R.id.update_photo_code);
        buttonUpdate = (Button) view.findViewById(R.id.update_button);
    }

    private void setUserValues() {
        editTextNom.setText(user.getLastName());
        editTextPrenom.setText(user.getFirstName());
        editTextEmail.setText(user.getEmail());
        editTextPhotoCode.setText(user.getPhotoName());
    }

    private void updateVerification() {

        // get user info from editTexts
        String nom = editTextNom.getText().toString();
        String prenom = editTextPrenom.getText().toString();
        String email = editTextEmail.getText().toString();
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

        if (photoCode.isEmpty()) {
            editTextPhotoCode.setError(getString(R.string.champ_vide));
            return;
        }

        HashMap<String, String> updateMap = new HashMap<>();
        updateMap.put("first_name", prenom);
        updateMap.put("last_name", nom);
        updateMap.put("email", email);
        updateMap.put("photo_name", photoCode);

        updateUser(updateMap);
    }

    private void updateUser(HashMap<String, String> updateMap) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<User> updateCall = apiService.updateUser(tokenResponse.getToken(), user.getId(), updateMap);

        updateCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // isSuccess is true if response code => 200 and <= 300
                if (!response.isSuccessful()) {
                    // print response body if unsuccessful
                    try {
                        Log.i("updateFrag", response.errorBody().string());
                        Toast.makeText(getActivity(), "Error : " + response.code(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        // do nothing
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.update_ok, Toast.LENGTH_SHORT).show();
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
