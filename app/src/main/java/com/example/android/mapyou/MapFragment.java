package com.example.android.mapyou;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.Vector;


public class MapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private Communication mCommunication;
    private Communication mCommunicationSendLocation;
    private Location mLastLocation;
    private Vector<Building> buildings;

    private String FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mCommunication = new Communication();
        mCommunicationSendLocation = new Communication();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        initListener();
        new DreamBeforeDrawing().execute(0, 0);
    }


    private void initListener() {
        getMap().setOnMarkerClickListener(this);
        getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener(this);
        getMap().setOnMapClickListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        for (int i = 0; i < 10 && getContext().checkCallingOrSelfPermission(FINE_LOCATION) != PackageManager.PERMISSION_GRANTED; ++i) {
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                Log.v("MapFragment", "LocationPermissionError");
            }
        }
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mCurrentLocation != null) {
            initCamera(mCurrentLocation);
        } else {
            Location shitLocation = new Location("");
            shitLocation.setLatitude(0);
            shitLocation.setLongitude(0);
            initCamera(shitLocation);
        }
        sendLocation();
    }

    private void drawPolygon(LatLng point1, LatLng point2) {
        if (buildings != null) {
            Log.v("buildings", "are drawing");
            for (int i = 0; i < buildings.size(); ++i) {
                getMap().addPolygon(buildings.elementAt(i).getPolygon());
            }
        }
        new DreamBeforeDrawing().execute(0, 0);
    }

    private void initCamera(Location location) {
        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();
        getMap().animateCamera(CameraUpdateFactory.newCameraPosition(position));
        getMap().setMyLocationEnabled(true);
        getMap().getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    private void sendLocation() {
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        new UpdateLocationDream().execute(0, 0);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public class DreamBeforeDrawing extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                buildings = mCommunication.getBuildings(new LatLng(9, 0), new LatLng(9, 9), 0);
                Thread.sleep(5000);

            } catch (InterruptedException e) {

            }
            return params[0];
        }

        @Override
        protected void onPostExecute(Integer ans) {
            drawPolygon(new LatLng(59.808263, 29.536778), new LatLng(60.109892, 30.723302)); //Peterhof: (59.870843, 29.765774) (59.887411, 29.883190)
        }
    }

    public class UpdateLocationDream extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                Thread.sleep(500);
                mCommunicationSendLocation.setMyLocation(mCurrentLocation);

            } catch (InterruptedException e) {
                Log.v("error", "update");
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(Integer ans) {
            sendLocation();
        }
    }
}
