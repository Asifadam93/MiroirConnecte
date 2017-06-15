package com.example.asif.androidclient;

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

/**
 * Created by AAD on 14/06/2017.
 */

public class LoginFragment extends Fragment {

    private static EditText editTextUserName,editTextPassword;
    private static Button buttonLogin;
    private static TextView textViewRegister;

    private static View view;
    private static FragmentManager fragmentManager;
    private static LinearLayout loginLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.login_layout,container,false);
        initViews();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                if(name.length() == 0){
                    editTextUserName.setError(getString(R.string.champ_vide));
                    return;
                }

                if(password.length() == 0){
                    editTextPassword.setError(getString(R.string.champ_vide));
                    return;
                }

            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initViews(){

        fragmentManager = getActivity().getFragmentManager();

        editTextUserName = (EditText)view.findViewById(R.id.login_nom);
        editTextPassword = (EditText)view.findViewById(R.id.login_mdp);
        buttonLogin = (Button)view.findViewById(R.id.login_button);
        textViewRegister = (TextView)view.findViewById(R.id.login_inscription);
        loginLayout = (LinearLayout)view.findViewById(R.id.login_layout);

        // TODO: 14/06/2017  Setting text selector over textviews
    }

}