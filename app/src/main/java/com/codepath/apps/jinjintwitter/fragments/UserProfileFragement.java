package com.codepath.apps.jinjintwitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.jinjintwitter.R;
import com.codepath.apps.jinjintwitter.RoundedTransformation;
import com.codepath.apps.jinjintwitter.TwitterApplication;
import com.codepath.apps.jinjintwitter.TwitterClient;
import com.codepath.apps.jinjintwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Created by jinjinz on 6/28/16.
 */
public class UserProfileFragement extends Fragment {
    private TextView body;
    private TextView realName;
    private TextView screenName;
    private ImageView profile;
    private User user;
    private TwitterClient client;
    private TextView follow;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        findUser(getArguments().getString("screenName"));
    }

    public static UserProfileFragement newInstance(String screenName) {
        UserProfileFragement userF = new UserProfileFragement();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);
        userF.setArguments(args);
        return userF;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_info, container, false);
        body = (TextView) v.findViewById(R.id.descript);
        realName = (TextView) v.findViewById(R.id.UserName);
        screenName = (TextView) v.findViewById(R.id.screenName);
        profile = (ImageView) v.findViewById(R.id.ivProfile);
        follow = (TextView) v.findViewById(R.id.follow);
        return v;
    }

    private void findUser(final String scrnName) {
        if(scrnName == null) {
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                    user = User.fromJSON(json);
                    body.setText(user.getDescription());
                    realName.setText(user.getName());
                    screenName.setText(user.getScreenName());
                    Picasso.with(getContext()).load(user.getProfileUrl())
                            .transform(new RoundedTransformation(4, 0))
                            .into(profile);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }

        else {
            client.getOtherUserInfo(scrnName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    body.setText(user.getDescription());
                    realName.setText(user.getName());
                    screenName.setText("@" + user.getScreenName());
                    Picasso.with(getContext()).load(user.getProfileUrl())
                            .transform(new RoundedTransformation(4, 0))
                            .into(profile);
                    follow.setText(user.getFollowersCount() + " Followers  " + user.getFollowingCount() + " Following");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                }
            });
        }
    }
}
