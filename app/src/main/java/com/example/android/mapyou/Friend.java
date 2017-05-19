package com.example.android.mapyou;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import static android.R.attr.id;

/**
 * Created by kirill on 18.05.17.
 */

public class Friend {
    private Bitmap mImage;
    private LatLng mLocation;
    private long mId;
    private String mName;
    private boolean isOnline = false;

    public void setImage(Bitmap image) {
        mImage = image;
    }

    public void setLocation(LatLng location) {
        mLocation = location;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setOnline() {
        isOnline = true;
    }

    public boolean getOnline() {
        return isOnline;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public LatLng getLocation() {
        return mLocation;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

}
