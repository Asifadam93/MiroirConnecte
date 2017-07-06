package com.esgi.androidclientv2.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.androidclientv2.Model.User;
import com.esgi.androidclientv2.Network.IServiceResultListener;
import com.esgi.androidclientv2.Network.RetrofitUserService;
import com.esgi.androidclientv2.Network.ServiceResult;
import com.esgi.androidclientv2.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AAD on 15/06/2017.
 */

public class RegisterFragment extends Fragment {

    private View view;
    private EditText editTextNom, editTextPrenom, editTextEmail, editTextMdp, editTextConfirmMdp,
            editTextPhotoCode;
    private Button buttonInscription;
    private TextView textViewConnexion;
    private LoginFragment loginFragment;
    private RetrofitUserService retrofitUserService;

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

        HashMap<String, String> hashMapUser = new HashMap<>();
        hashMapUser.put("first_name", prenom);
        hashMapUser.put("last_name", nom);
        hashMapUser.put("photo_name", photoCode);
        hashMapUser.put("email", email);
        hashMapUser.put("plain_password", mdp);

        registerUser(hashMapUser);
    }

    private void registerUser(Map<String, String> registerMap) {

        getRetrofitUserService().register(registerMap, new IServiceResultListener<User>() {
            @Override
            public void onResult(ServiceResult<User> result) {

                User user = result.getData();

                if (user != null) {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.inscription_ok), Toast.LENGTH_SHORT).show();
                    showLoginFragment();
                } else {
                    Toast.makeText(getActivity(), result.getErrorMsg(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showLoginFragment() {
        getActivity().getFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, getLoginFragment(), "LoginFragment")
                .commit();
    }

    public LoginFragment getLoginFragment() {
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
        }
        return loginFragment;
    }

    private RetrofitUserService getRetrofitUserService() {
        if (retrofitUserService == null) {
            retrofitUserService = new RetrofitUserService();
        }
        return retrofitUserService;
    }
}
