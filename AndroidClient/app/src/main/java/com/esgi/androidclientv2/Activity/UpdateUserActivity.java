package com.esgi.androidclientv2.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.androidclientv2.Model.TokenResponse;
import com.esgi.androidclientv2.Model.User;
import com.esgi.androidclientv2.Network.IServiceResultListener;
import com.esgi.androidclientv2.Network.RetrofitUserService;
import com.esgi.androidclientv2.Network.ServiceResult;
import com.esgi.androidclientv2.R;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserActivity extends Activity {

    private EditText editTextNom, editTextPrenom, editTextEmail,
            editTextPhotoCode;
    private Button buttonUpdate;
    private User user;
    private TokenResponse tokenResponse;
    private RetrofitUserService retrofitUserService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        // get user token model from loginFragment
        Intent intent = getIntent();
        if (intent != null) {
            tokenResponse = intent.getParcelableExtra("UserInfo");
            user = tokenResponse.getUser();
            Log.i("UserActivity", "Token : " + tokenResponse.getToken()); // test
        } else {
            Log.i("UserActivity", "Error data transmission");
            return;
        }

        editTextNom = (EditText) findViewById(R.id.update_nom);
        editTextPrenom = (EditText) findViewById(R.id.update_prenom);
        editTextEmail = (EditText) findViewById(R.id.update_email);
        editTextPhotoCode = (EditText) findViewById(R.id.update_photo_code);
        buttonUpdate = (Button) findViewById(R.id.update_button);

        editTextNom.setText(user.getLastName());
        editTextPrenom.setText(user.getFirstName());
        editTextEmail.setText(user.getEmail());
        editTextPhotoCode.setText(user.getPhotoName());

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateVerification();
            }
        });
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

    private void updateUser(Map<String, String> updateMap) {

        getRetrofitUserService().update(tokenResponse.getToken(), user.getId(), updateMap,
                new IServiceResultListener<User>() {
                    @Override
                    public void onResult(ServiceResult<User> result) {

                        User user = result.getData();

                        if (user != null) {
                            Toast.makeText(getBaseContext(), "Mise à jour réussi", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateUserActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public RetrofitUserService getRetrofitUserService() {
        if (retrofitUserService == null) {
            retrofitUserService = new RetrofitUserService();
        }
        return retrofitUserService;
    }
}
