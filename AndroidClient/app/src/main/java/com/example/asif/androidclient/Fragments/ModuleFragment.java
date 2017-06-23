package com.example.asif.androidclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.asif.androidclient.Model.TokenResponse;
import com.example.asif.androidclient.Model.User;
import com.example.asif.androidclient.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Asifadam93 on 18/06/2017.
 */

public class ModuleFragment extends Fragment {

    private View view;
    private ImageButton imageButtonTopLeft, imageButtonTopRight, imageButtonCenterLeft,
            imageButtonCenterRight, imageButtonBottomLeft, imageButtonBottomRight;
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

        initViews();


        return view;
    }

    private void initViews() {
        imageButtonTopLeft = (ImageButton) view.findViewById(R.id.module_add_top_left);
        imageButtonTopRight = (ImageButton) view.findViewById(R.id.module_add_top_right);
        imageButtonCenterLeft = (ImageButton) view.findViewById(R.id.module_add_center_left);
        imageButtonCenterRight = (ImageButton) view.findViewById(R.id.module_add_center_right);
        imageButtonBottomLeft = (ImageButton) view.findViewById(R.id.module_add_bottom_left);
        imageButtonBottomRight = (ImageButton) view.findViewById(R.id.module_add_bottom_right);
    }

    private void setClickListener() {

        imageButtonTopLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }

}