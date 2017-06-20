package com.example.asif.androidclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asif.androidclient.Const;
import com.example.asif.androidclient.Model.TokenResponse;
import com.example.asif.androidclient.Model.User;
import com.example.asif.androidclient.R;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Asifadam93 on 18/06/2017.
 */

public class UserFragment extends Fragment {

    private View view;
    private TextView textViewUserMsg;
    private TokenResponse tokenResponse;
    private User user;
    private CircleImageView circleImageView;
    private Button buttonModule, buttonEditProfile, buttonDeleteProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.user_layout, container, false);

        tokenResponse = LoginFragment.tokenResponse; // get user details
        user = tokenResponse.getUser();

        initViews();

        setOnClickListeners();

        setUserInfo();

        return view;
    }

    private void initViews() {
        textViewUserMsg = (TextView) view.findViewById(R.id.user_name_text);
        circleImageView = (CircleImageView) view.findViewById(R.id.profile_image);
        buttonModule = (Button) view.findViewById(R.id.user_edit_module);
        buttonEditProfile = (Button) view.findViewById(R.id.user_edit_profil);
        buttonDeleteProfile = (Button) view.findViewById(R.id.user_delete_profil);
    }

    private void setUserInfo() {

        // set profile image
        setProfileImage(user.getPhotoName());

        // set welcome msg
        String msg = String.format(getString(R.string.welcome_msg), user.getFirstName());
        textViewUserMsg.setText(msg);
    }

    private void setProfileImage(String imgName) {
        String imageDownloadLink = Const.endPoint + "/img/photos/" + imgName;
        Picasso.with(getActivity()).load(imageDownloadLink).into(circleImageView); //set profile image
    }

    private void setOnClickListeners() {

        buttonModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SweetAlertDialog dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                dialog.setTitleText("Suppression");
                dialog.setContentText(getString(R.string.confirm_delete));
                dialog.setConfirmText(getString(R.string.delete));
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelText("Annuler");
                dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }
}
