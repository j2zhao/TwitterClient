package com.codepath.apps.jinjintwitter.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codepath.apps.jinjintwitter.R;
import com.codepath.apps.jinjintwitter.TimelineActivity;
import com.codepath.apps.jinjintwitter.TwitterApplication;
import com.codepath.apps.jinjintwitter.TwitterClient;
import com.codepath.apps.jinjintwitter.models.Tweet;
import com.codepath.apps.jinjintwitter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jinjinz on 6/30/16.
 */
public class CreateReplyFragment extends CreateFragment {

    // TODO: Rename and change types of parameters
    private String newTweet;
    private TextInputEditText editText;
    TwitterClient client;
    Activity activity;
    //private User self;

    // TODO: Rename and change types and number of parameters
    public static CreateReplyFragment newInstance( String tweet_id, String scrName) {
        CreateReplyFragment fragment = new CreateReplyFragment();
        Bundle args = new Bundle();
        args.putString("tweet_id", tweet_id);
        args.putString("scrName", scrName);
        fragment.setArguments(args);
        return fragment;
    }
    public CreateReplyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        client = TwitterApplication.getRestClient();
        //self = getArguments().getParcelable("self");
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create, container, false);
        Button b = (Button) v.findViewById(R.id.btnCompose);

        editText = (TextInputEditText) v.findViewById(R.id.editText);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postTweet(editText.getText().toString());

            }
        });
        String scrName = getArguments().getString("scrName");
        editText.setText("@" + scrName + " ");
        int position = scrName.length() + 2;
        editText.setSelection(position);
        return v;
    }
    /*private String body;
    private long id;
    private User user;
    private String createdAt;*/
    private void postTweet(final String text) {
        client.writeStatus(text, getArguments().getString("tweet_id"), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //SearchActivity activity = (SearchActivity) getActivity();
                Tweet tweet = new Tweet();
                tweet.setBody(text);
                tweet.setCreatedAt("now");
                tweet.setUser(new User());
                if (activity instanceof TimelineActivity) {
                    ((TimelineActivity) activity).addTweet(tweet);
                }
                dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        } );
    }
}
