package com.example.hang.instagramclient;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {
    private final String url="https://api.instagram.com/v1/media/popular?client_id=e05c462ebd86446ea48a5af73769b602";

    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotoAdaptor aPhotos;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        photos = new ArrayList<>();
        aPhotos = new InstagramPhotoAdaptor(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aPhotos);
        fetchPopularPhotos();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void fetchPopularPhotos(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url,null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray jsonArray = null;
                aPhotos.clear();
                try{
                    jsonArray = response.getJSONArray("data");
                    for (int i =0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = jsonObject.getJSONObject("user").getString("username");
                        photo.caption = jsonObject.getJSONObject("caption").getString("text");
                        photo.imageUrl = jsonObject.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imgHeight = jsonObject.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = jsonObject.getJSONObject("likes").getInt("count");
                        photo.profileImageUrl = jsonObject.getJSONObject("user").getString("profile_picture");
                        photo.timestamp = jsonObject.getString("created_time");
                        photos.add(photo);
                    }
                } catch (JSONException je){
                    je.printStackTrace();
                }
                aPhotos.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
