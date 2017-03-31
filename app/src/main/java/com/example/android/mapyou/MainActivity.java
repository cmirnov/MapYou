package com.example.android.mapyou;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import static com.example.android.mapyou.R.string.no_location_detected;

public class MainActivity extends AppCompatActivity  {


    private Communication mCommunication;
    private GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


   /* protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(Bundle bundle) {
        while (mLastLocation == null) {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        if (mLastLocation != null) {
            String s1 = String.valueOf(mLastLocation.getLatitude());
            String s2 = String.valueOf(mLastLocation.getLongitude());
            Toast.makeText(this, s1 + " " + s2, Toast.LENGTH_LONG);
        } else {
            Toast.makeText(this, no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }*/

}
