package com.example.android.mapyou;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import com.example.android.mapyou20.R;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static com.example.android.mapyou20.R.id.friends;

/**
 * Created by kirill on 18.05.17.
 */

public class MyCallable implements Callable<Vector<Friend>> {

    private URL mUrl;
    private Vector<Friend> friends;
    private VKResponse response;
    private long mId;
    public MyCallable(VKResponse response1) {
        super();
        response = response1;
        friends = new Vector<>();
    }

    @Override
    public Vector<Friend> call() throws Exception {
        friends.clear();

        JSONObject jresponse = response.json;
        try {
            jresponse = jresponse.getJSONObject("response");
            JSONArray users = jresponse.getJSONArray("items");
            for (int i = 0; i < users.length(); ++i) {
                JSONObject user = users.getJSONObject(i);
                Friend newFriend = new Friend();
                newFriend.setId(user.getLong("id"));
                newFriend.setName(user.getString("first_name") + " " + user.getString("last_name"));
                URL mUrl = new URL(user.getString("photo_50"));
                HttpURLConnection urlConnection = (HttpURLConnection) mUrl.openConnection();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                newFriend.setImage(BitmapFactory.decodeStream(inputStream));
                friends.add(newFriend);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Vector<Friend> onlineFriends = (new Communication()).getFriends(friends);
        int idx = 0;
        for (int i = 0; i < friends.size(); ++i) {
            if (onlineFriends.get(idx).getId() == friends.get(i).getId()) {
                friends.get(i).setOnline();
                friends.get(i).setLocation(onlineFriends.get(idx).getLocation());
                idx++;
            }
            if (idx >= onlineFriends.size()) {
                break;
            }
        }
        return friends;
    }
}
