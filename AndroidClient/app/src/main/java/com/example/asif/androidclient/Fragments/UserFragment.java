package com.example.asif.androidclient.Fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asif.androidclient.Api.UserClient;
import com.example.asif.androidclient.Const;
import com.example.asif.androidclient.Model.TokenResponse;
import com.example.asif.androidclient.R;
import com.squareup.picasso.Picasso;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Asifadam93 on 18/06/2017.
 */

public class UserFragment extends Fragment {

    private View view;
    private TokenResponse tokenResponse;
    private CircleImageView circleImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.user_layout, container, false);

        tokenResponse = LoginFragment.tokenResponse; // get user details

        initViews();

        setProfileImage(tokenResponse.getUserList().getPhotoName());

        return view;
    }

    private void initViews(){
        circleImageView = (CircleImageView)view.findViewById(R.id.profile_image);
    }

    private void setProfileImage(String imgName){

        Picasso.with(getActivity()).load("http://i.imgur.com/DvpvklR.png").into(circleImageView);

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.endPoint)
                .build();

        UserClient userClient = retrofit.create(UserClient.class);
        Call<Bitmap> imgBitmap = userClient.getProfileImage(imgName);

        imgBitmap.enqueue(new Callback<Bitmap>() {
            @Override
            public void onResponse(Call<Bitmap> call, Response<Bitmap> response) {

                Log.i("UserFragment",""+response.code());

            }

            @Override
            public void onFailure(Call<Bitmap> call, Throwable t) {

            }
        });*/


        //circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.tutorial));
       // circleImageView.setImageURI(Uri.parse("http://vps220964.ovh.net/img/photos/220615175608.jpeg"));
    }

}
