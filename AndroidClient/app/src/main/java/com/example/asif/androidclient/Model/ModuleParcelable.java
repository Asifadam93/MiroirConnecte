package com.example.asif.androidclient.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Asifadam93 on 22/06/2017.
 */

public class ModuleParcelable implements Parcelable {




    protected ModuleParcelable(Parcel in) {
    }

    public static final Creator<ModuleParcelable> CREATOR = new Creator<ModuleParcelable>() {
        @Override
        public ModuleParcelable createFromParcel(Parcel in) {
            return new ModuleParcelable(in);
        }

        @Override
        public ModuleParcelable[] newArray(int size) {
            return new ModuleParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
