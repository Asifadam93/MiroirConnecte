package com.esgi.androidclientv2.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esgi.androidclientv2.Fragments.LoginFragment;
import com.esgi.androidclientv2.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showLoginFragment();
    }

    private void showLoginFragment() {
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, new LoginFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        // disable back button
        //super.onBackPressed();
    }

    private void showModuleActivity() {
        startActivity(new Intent(this, ModuleActivity.class));
    }
}
