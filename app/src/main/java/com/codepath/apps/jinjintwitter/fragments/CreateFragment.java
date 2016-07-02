package com.codepath.apps.jinjintwitter.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.jinjintwitter.R;
import com.codepath.apps.jinjintwitter.TimelineActivity;
import com.codepath.apps.jinjintwitter.TwitterApplication;
import com.codepath.apps.jinjintwitter.TwitterClient;
import com.codepath.apps.jinjintwitter.models.Tweet;
import com.codepath.apps.jinjintwitter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CreateFragment extends DialogFragment {
    // TODO: Rename and change types of parameters
    private String newTweet;
    private TextInputEditText editText;
    TwitterClient client;
    TimelineActivity activity;
    //private User self;

    // TODO: Rename and change types and number of parameters
    public static CreateFragment newInstance(User user) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putParcelable("self", user);
        fragment.setArguments(args);
        return fragment;
    }
    public CreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        client = TwitterApplication.getRestClient();
        //self = getArguments().getParcelable("self");
        super.onCreate(savedInstanceState);
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
        return v;
    }

    /*private String body;
    private long id;
    private User user;
    private String createdAt;*/
    private void postTweet(final String text) {
        client.writeStatus(text, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //SearchActivity activity = (SearchActivity) getActivity();
                Tweet tweet = new Tweet();
                tweet.setBody(text);
                tweet.setCreatedAt("now");
                tweet.setUser((User) getArguments().getParcelable("self"));
                Log.i("PROFILE_URL", tweet.getUser().getProfileUrl());
                if (getActivity() instanceof TimelineActivity) {
                    activity = (TimelineActivity) getActivity();
                    activity.addTweet(tweet);
                }
                dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        } );
    }

}
