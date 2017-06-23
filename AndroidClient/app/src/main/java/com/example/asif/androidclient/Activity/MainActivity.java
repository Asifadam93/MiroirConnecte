package com.example.asif.androidclient.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.asif.androidclient.Fragments.LoginFragment;
import com.example.asif.androidclient.Fragments.ModuleFragment;
import com.example.asif.androidclient.R;

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
        Log.i("MainActivity", "onBackPressed");
        showLoginFragment();
    }

    private void showLoginFragment() {
        //getFragmentManager().beginTransaction().replace(R.id.frameContainer, loginFragment).commit();
        //// TODO: 18/06/2017 replace
       getFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, new ModuleFragment(), "ModuleFragment")
                .commit();
    }
}
