package com.example.asif.androidclient;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginFragment = new LoginFragment();
        showLoginFragment();
    }

    @Override
    public void onBackPressed() {
        Log.i("MainActivity","onBackPressed");
        showLoginFragment();
    }

    private void showLoginFragment(){
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, loginFragment).commit();
    }
}
