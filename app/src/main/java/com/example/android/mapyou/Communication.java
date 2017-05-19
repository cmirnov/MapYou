package com.example.android.mapyou;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.android.mapyou.Building;
import com.example.android.mapyou20.R;
import com.google.android.gms.maps.model.LatLng;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Vector;

import static android.R.attr.bitmap;
import static android.R.attr.documentLaunchMode;
import static android.R.attr.id;
import static android.R.attr.type;
import static android.R.id.message;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.provider.Settings.Global.getString;
import static com.vk.sdk.api.model.VKApiPhotoSize.P;
import static java.lang.StrictMath.max;

/**
 * Created by kirill on 30.03.17.
 */

public class Communication {

    private String SERVER_REQUEST_URL =  "http://172.20.5.185:80/api/";//?id=a16d723&type=update_geo_position&latitude=59.856954&longitude=29.874587&security=sha1(id + )";
    private URL mUrl;


    public void setMyLocation(Location location, long id) {
        if (location == null) {
            Log.v("Location", "is null");
            return;
        }
        try {
            String url = createUpdateLocationUrl(location, id);
            mUrl = new URL(url);
        } catch (MalformedURLException e) {
            Log.v("ERROR", "Incorrect URL");
            e.printStackTrace();
        }
        String data = null;
        data = makeHttpsRequest(mUrl);
        try {
            JSONObject baseJsonResponse = new JSONObject(data);
            String isNotError = baseJsonResponse.getString("status");
            if (isNotError == "ok") {
                Log.v("ok", "ok");
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
            String url = creategetBuildingsAtPolygon(point1, point2);
             mUrl = new URL(url);//"http://92.42.30.155:8999/?method=get_buildings_at_polygon&point1_latitude=59.856954&point1_longitude=29.874587&point2_latitude=60.856912&point2_longitude=30.874586&security=ccbbf97c56701d56777b0ae2684857eedbd48585");
        } catch (MalformedURLException e) {
            Log.v("ERROR", "Incorrect URL test1");
            e.printStackTrace();
        }
        String data = null;
        data = makeHttpsRequest(mUrl);
        Log.v("output", data);
        return extractBuildingsFromJSON(data);
    }


    private String makeHttpsRequest(URL url) {
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
            JSONObject message = new JSONObject(data);
            JSONArray baseJsonArray =  message.getJSONArray("message");
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
            return result;

        } catch (JSONException e) {
            Log.v("ERROR", "JSON");
            return null;
        }
    }

    private String makeSHA1Hash(String input)
        throws NoSuchAlgorithmException, UnsupportedEncodingException {
        input += "$!ASD($@%!";
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.reset();
        byte[] buffer = input.getBytes("UTF-8");
        md.update(buffer);
        byte[] digest = md.digest();

        String hexStr = "";
        for (int i = 0; i < digest.length; i++) {
            hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return hexStr;
    }

    public Vector<Friend> getFriends(Vector<Friend> friends) {
        String listOfFriend = "";
        for (int i = 0; i < friends.size(); ++i) {
            listOfFriend += String.valueOf(friends.get(i).getId());
            if (i < friends.size() - 1) {
                listOfFriend += ",";
            }
        }
        String url = createGetFriendsUrl(listOfFriend);
        try {
            mUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String data = makeHttpsRequest(mUrl);
        Vector<Friend> result = new Vector<>();
        try {
            JSONObject response = new JSONObject(data);
            JSONArray users = response.getJSONArray("message");
            for (int i = 0; i < users.length(); ++i) {
                JSONObject user = users.getJSONObject(i);
                Friend temp = new Friend();
                temp.setId(user.getLong("id"));
                temp.setLocation(new LatLng(user.getDouble("x"), user.getDouble("y")));
                result.add(temp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String createGetFriendsUrl(String s) {
        String url = SERVER_REQUEST_URL;
        url += "get_friends/?";
        url += "list=";
        url += s;
        url += "&security=";
        try {
            url += makeSHA1Hash(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
    private String createUpdateLocationUrl(Location location, long id) {
        String url = SERVER_REQUEST_URL;
        url += "update_geo_position/?";
        url += "id=";
        url += String.valueOf(id);
        url += "&";
        url += "latitude=";
        url += String.valueOf(location.getLatitude());
        url += "&";
        url += "longitude=";
        url += String.valueOf(location.getLongitude());
        url += "&";
        url += "security=";
        try {
            url += makeSHA1Hash(String.valueOf(id) + String.valueOf(location.getLatitude()) + String.valueOf(location.getLongitude()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String creategetBuildingsAtPolygon(LatLng point1, LatLng point2) {
        String url = SERVER_REQUEST_URL;
        url += "get_buildings_at_polygon/";
        url += "?";
        url += "point1_latitude=";
        url += String.valueOf(point1.latitude);
        url += "&";
        url += "point1_longitude=";
        url += String.valueOf(point1.longitude);
        url += "&";
        url += "point2_latitude=";
        url += String.valueOf(point2.latitude);
        url += "&";
        url += "point2_longitude=";
        url += String.valueOf(point2.longitude);
        url += "&";
        url += "security=";
        try {
            url += makeSHA1Hash(String.valueOf(point1.latitude) + String.valueOf(point1.longitude) + String.valueOf(point2.latitude) + String.valueOf(point2.longitude));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

}


