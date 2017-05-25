package com.example.android.mapyou;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.Vector;

//import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by kirill on 30.03.17.
 */

public class Building {
    private Vector<LatLng> mPoints;
    private int mNumOfUsers;
    private int mMaxNumOfUsers;
    private PolygonOptions mPolygon;

    public Building(Vector<LatLng> points, int numOfUsers, int maxNumOfUsers) {
        mPoints = points;
        mNumOfUsers = numOfUsers;
        mMaxNumOfUsers = maxNumOfUsers;
        createPolygon();
    }

    private void createPolygon() {
        mPolygon = new PolygonOptions();
        mPolygon.addAll(mPoints);
        mPolygon.fillColor((20 << 18) | getColor());
        mPolygon.strokeColor((20 << 18) | getColor());
        mPolygon.strokeWidth(10);
    }

    private int getColor() {
        int proportion = (int) Math.ceil(10 * mNumOfUsers / mMaxNumOfUsers);
        switch (proportion) {
            case 0:
            case 1:
            case 2:
            case 3:
                return Color.GREEN;
            case 4:
            case 5:
            case 6:
            case 7:
                return Color.YELLOW;
            case 8:
            case 9:
            case 10:
                return Color.RED;
            default:
                return Color.BLACK;
        }
    }

    public PolygonOptions getPolygon() {
        return mPolygon;
    }

}
