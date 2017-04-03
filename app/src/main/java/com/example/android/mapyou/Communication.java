package com.example.android.mapyou;

import com.google.android.gms.maps.model.LatLng;

import java.util.Vector;

/**
 * Created by kirill on 30.03.17.
 */

public class Communication {

    public void setMyLocation() {

    }

    public Vector<Building> getBuildings(LatLng point1, LatLng point2, int bufferSize) {
        Vector<Building> temp = new Vector<>();
        Vector<LatLng> points = new Vector<>();
        points.add(new LatLng(59.880149, 29.825008));
        points.add(new LatLng(59.880206, 29.825067));
        points.add(new LatLng(59.880184, 29.825145));
        points.add(new LatLng(59.880373, 29.825304));
        points.add(new LatLng(59.880389, 29.825242));
        points.add(new LatLng(59.880447, 29.825293));
        points.add(new LatLng(59.880633, 29.824448));
        points.add(new LatLng(59.880577, 29.824397));
        points.add(new LatLng(59.880592, 29.824333));
        points.add(new LatLng(59.880413, 29.824165));
        points.add(new LatLng(59.880390, 29.824235));
        points.add(new LatLng(59.880331, 29.824184));
        temp.add(new Building(points, bufferSize, 100)); //!!!!!!
        return  temp;
    }
}
