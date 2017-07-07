package com.esgi.androidclientv2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Asif on 09/06/2017.
 */

public class User implements Parcelable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("photo_name")
    private String photoName;
    @SerializedName("modules")
    private List<Module> modules = null;

    public User(String firstName, String lastName, String email, String password, String photoName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photoName = photoName;
    }

    public User(String firstName, String lastName, String email, String photoName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photoName = photoName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoName() {
        return photoName;
    }

    /*public List<Module> getModules() {
        return modules;
    }*/

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", photoName='" + photoName + '\'' +
                '}';
    }

    protected User(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        photoName = in.readString();
        modules = in.createTypedArrayList(Module.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(email);
        parcel.writeString(photoName);
        parcel.writeTypedList(modules);
    }
}
