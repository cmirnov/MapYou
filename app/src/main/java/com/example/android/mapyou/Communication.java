package com.example.android.mapyou;

import android.graphics.Point;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import static android.R.attr.data;
import static android.support.v7.widget.AppCompatDrawableManager.get;

/**
 * Created by kirill on 30.03.17.
 */

public class Communication {

    private static final String SERVER_REQUEST_URL = "http://172.20.5.185/mapyou/index.php?id=a16d723geqf&type=update_geo_position&latitude=59.856954&longitude=29.874587";
    private URL mUrl;

    public void setMyLocation(Location location) {
        if (location == null) {
            Log.v("Location", "is null");
            return;
        }
        try {
            mUrl = new URL(SERVER_REQUEST_URL);
        } catch (MalformedURLException e) {
            Log.v("ERROR", "Incorrect URL");
            e.printStackTrace();
        }
        String data = null;
        data = makeHttpsRequest(mUrl, location);
        try {
            JSONObject baseJsonResponse = new JSONObject(data);
            String isNotError = baseJsonResponse.getString("status");
            if (isNotError == "ok") {
                return;
            } else {
                Log.v("RESPONSE ERROR", baseJsonResponse.getString("message"));
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return;
    }


    public Vector<Building> getBuildings(LatLng point1, LatLng point2, int bufferSize) {
        try {
             mUrl = new URL("http://172.20.5.185/mapyou/?type=get_buildings_at_polygon&point1_latitude=59.856954&point1_longitude=29.874587&point2_latitude=60.856912&point2_longitude=30.874586");
        } catch (MalformedURLException e) {
            Log.v("ERROR", "Incorrect URL test1");
            e.printStackTrace();
        }
        String data = null;
        Location shitLocation = new Location("");
        shitLocation.setLatitude(0);
        shitLocation.setLongitude(0);
        data = makeHttpsRequest(mUrl, shitLocation);
        Log.v("output", data);
        return extractBuildingsFromJSON(data);
    }


    private String makeHttpsRequest(URL url, Location location) {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.v("URL ERROR", String.valueOf(urlConnection.getResponseCode()));
            }
        } catch (IOException e) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;

    }


    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    private Vector<Building> extractBuildingsFromJSON(String data) {
        try {
            JSONArray baseJsonArray = new JSONArray(data);
            Vector<Building> result = new Vector<>();
            for (int i = 0; i < baseJsonArray.length(); ++i) {
                JSONObject tempObj = baseJsonArray.getJSONObject(i);
                int currentLoad = Integer.valueOf(tempObj.getString("current_load"));
                int maxLoad = Integer.valueOf(tempObj.getString("max_load"));
                JSONArray JSONPoints = tempObj.getJSONArray("points");
                Vector<LatLng> points = new Vector<>();
                for (int j = 0; j < JSONPoints.length(); ++j) {
                    JSONObject tempPoint = JSONPoints.getJSONObject(j);
                    double x = tempPoint.getDouble("x");
                    double y = tempPoint.getDouble("y");
                    points.add(new LatLng(x, y));
                }
                Building temp = new Building(points, currentLoad, maxLoad);
                result.add(temp);
            }
            return  result;

        } catch (JSONException e) {
            Log.v("ERROR", "ExtractBuildingsFromJSON");
            return null;
        }
    }
}


