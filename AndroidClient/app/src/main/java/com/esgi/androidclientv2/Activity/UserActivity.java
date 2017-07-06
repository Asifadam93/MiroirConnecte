package com.esgi.androidclientv2.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.esgi.androidclientv2.Model.TokenResponse;
import com.esgi.androidclientv2.Model.User;
import com.esgi.androidclientv2.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends Activity {

    private TextView textViewUserMsg;
    private TokenResponse tokenResponse;
    private User user;
    private CircleImageView circleImageView;
    private Button buttonModule, buttonEditProfile, buttonDeleteProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        textViewUserMsg = (TextView) findViewById(R.id.user_name_text);
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        buttonModule = (Button) findViewById(R.id.user_edit_module);
        buttonEditProfile = (Button) findViewById(R.id.user_edit_profil);
        buttonDeleteProfile = (Button) findViewById(R.id.user_delete_profil);



    }
}
