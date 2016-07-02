package com.codepath.apps.jinjintwitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codepath.apps.jinjintwitter.TwitterApplication;
import com.codepath.apps.jinjintwitter.TwitterClient;
import com.codepath.apps.jinjintwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jinjinz on 7/1/16.
 */
public class SearchTimelineFragment extends TweetsFragment {
    TwitterClient client;
    String q;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        client = TwitterApplication.getRestClient();
        q = getArguments().getString("q");
        super.onCreate(savedInstanceState);
        populateTimeline();

    }

    public static SearchTimelineFragment newInstance( String query) {
        SearchTimelineFragment fragment = new SearchTimelineFragment();
        Bundle args = new Bundle();
        args.putString("q", query);
        fragment.setArguments(args);
        return fragment;
    }

    private void populateTimeline() {
        client.getSearch(q, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("statuses");
                    ArrayList<Tweet> tweets = Tweet.fromJSONARRAY(array);
                    addAll(tweets);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
