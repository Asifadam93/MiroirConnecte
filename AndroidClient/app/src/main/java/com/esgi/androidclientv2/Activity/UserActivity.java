package com.esgi.androidclientv2.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.androidclientv2.Const;
import com.esgi.androidclientv2.Model.TokenResponse;
import com.esgi.androidclientv2.Model.User;
import com.esgi.androidclientv2.Network.IServiceResultListener;
import com.esgi.androidclientv2.Network.RetrofitUserService;
import com.esgi.androidclientv2.Network.ServiceResult;
import com.esgi.androidclientv2.R;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends Activity {

    private TextView textViewUserMsg;
    private TokenResponse tokenResponse;
    private User user;
    private CircleImageView circleImageView;
    private Button buttonModule, buttonEditProfile, buttonDeleteProfile;
    private RetrofitUserService retrofitUserService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // get user token model from loginFragment
        Intent intent = getIntent();
        if (intent != null) {
            tokenResponse = intent.getParcelableExtra("UserInfo");
            user = tokenResponse.getUser();
            Log.i("UserActivity", "Token : " + tokenResponse.getToken()); // test
            Log.i("UserActivity", "Id : " + user.getId()); // test
        } else {
            Log.i("UserActivity", "Error data transmission");
            return;
        }

        textViewUserMsg = (TextView) findViewById(R.id.user_name_text);
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        buttonModule = (Button) findViewById(R.id.user_edit_module);
        buttonEditProfile = (Button) findViewById(R.id.user_edit_profil);
        buttonDeleteProfile = (Button) findViewById(R.id.user_delete_profil);

        setUserInfo();

        buttonModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showModuleActivity();
            }
        });

        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateActivity();
            }
        });

        buttonDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteAlertDialog();
            }
        });

    }

    private void setUserInfo() {

        // set profile image
        setProfileImage(user.getPhotoName());

        // set welcome msg
        String msg = String.format(getString(R.string.welcome_msg), user.getFirstName());
        textViewUserMsg.setText(msg);
    }

    private void setProfileImage(String imgName) {
        String imageDownloadLink = Const.END_POINT + "/img/photos/" + imgName;
        Picasso.with(this).load(imageDownloadLink).into(circleImageView); //set profile image
    }

    private void showModuleActivity() {
        startActivity(new Intent(this, ModuleActivity.class).putExtra("UserInfo",tokenResponse));
    }

    private void showUpdateActivity() {
        startActivity(new Intent(this, UpdateUserActivity.class).putExtra("UserInfo",tokenResponse));
    }

    private void showDeleteAlertDialog() {
        final SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText("Suppression profile");
        dialog.setContentText(getString(R.string.confirm_delete));
        dialog.setConfirmText(getString(R.string.delete));
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                deleteUser();
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

    private void deleteUser() {

        if (tokenResponse != null) {

            String token = tokenResponse.getToken();
            final int userId = user.getId();

            getRetrofitUserService().delete(token, userId, new IServiceResultListener<String>() {
                @Override
                public void onResult(ServiceResult<String> result) {

                    if (result.getData() != null) {
                        Toast.makeText(getBaseContext(), "Compte de " + user.getFirstName() + " a été bien supprimé", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public RetrofitUserService getRetrofitUserService() {
        if (retrofitUserService == null) {
            retrofitUserService = new RetrofitUserService();
        }
        return retrofitUserService;
    }
}
