package com.example.android.mapyou;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.mapyou20.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


public class MapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private Communication mCommunication;
    private Communication mCommunicationGetFriends;
    private Communication mCommunicationGetPhoto;
    private Communication mCommunicationSendLocation;
    private Location mLastLocation;
    private Vector<Building> buildings;
    private long mId = -1;
    private Vector<Friend> friends;

    private String FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        friends = new Vector<>();
        mCommunication = new Communication();
        mCommunicationGetFriends = new Communication();
        mCommunicationGetPhoto = new Communication();
        mCommunicationSendLocation = new Communication();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        setId();
        initListener();
        new DreamBeforeDrawing().execute(0, 0);
        new UpdateFriendDream().execute(0, 0);
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

    private void drawMarkers() {
        try {
            if (friends != null) {
                for (int i = 0; i < friends.size(); ++i) {
                    if (friends.get(i).getOnline()) {
                        MarkerOptions options = new MarkerOptions().position(friends.get(i).getLocation());
                        options.title(friends.get(i).getName());

                        options.icon(BitmapDescriptorFactory.fromBitmap(friends.get(i).getImage()));
                        getMap().addMarker(options);
                    }
                }
            }
        } catch (Exception e) {
            Log.v("", "");
        }
        new UpdateFriendDream().execute(0, 0);
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

    private void setId() {
        final VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                "id"));
        request.secure = false;
        request.useSystemLanguage = false;
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                JSONObject object = response.json;
                try {
                    JSONArray array = object.getJSONArray("response");
                    JSONObject user = array.getJSONObject(0);
                    mId = user.getLong("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(VKError error) {
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
            }
        });
        return;
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

    private class DreamBeforeDrawing extends AsyncTask<Integer, Integer, Integer> {

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

    private class UpdateLocationDream extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                Thread.sleep(5000);
                if (mId != -1) {
                    mCommunicationSendLocation.setMyLocation(mCurrentLocation, mId);
                }

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

    private class UpdateFriendDream extends AsyncTask<Object, Object, Void> {

        @Override
        protected Void doInBackground(Object... params) {
            try {
                Thread.sleep(5000);
                VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS,
                        "id, photo_50"));
                request.secure = false;
                request.useSystemLanguage = false;
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        MyCallable callable = new MyCallable(response);
                        FutureTask<Vector<Friend>> futureTask = new FutureTask<Vector<Friend>>(callable);
                        ExecutorService executor = Executors.newFixedThreadPool(2);
                        executor.execute(futureTask);
                        while (!futureTask.isDone()) {}
                        try {
                            friends = futureTask.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    @Override
                    public void onError(VKError error) { }
                    @Override
                    public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) { }
                });


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            drawMarkers();
        }
    }

}
