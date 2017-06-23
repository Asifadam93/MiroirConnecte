package com.example.asif.androidclient.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Asifadam93 on 22/06/2017.
 */

public class UserParcelable implements Parcelable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("photo_name")
    @Expose
    private String photoName;
    @SerializedName("modules")
    @Expose
    private List<ModuleParcelable> modules = null;


   /* public final static Parcelable.Creator<UserParcelable> CREATOR = new C*/



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
