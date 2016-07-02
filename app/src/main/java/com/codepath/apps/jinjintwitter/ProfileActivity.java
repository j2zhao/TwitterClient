package com.codepath.apps.jinjintwitter;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codepath.apps.jinjintwitter.fragments.UserProfileFragement;
import com.codepath.apps.jinjintwitter.fragments.UserTimelineFragment;
import com.codepath.apps.jinjintwitter.models.User;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Display fragments
        if (savedInstanceState == null) {
            //Display user timeline
            String screenName = getIntent().getStringExtra("screen_name");
            UserTimelineFragment fragmentUser = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUser);
            ft.commit();

            //Display user info
            UserProfileFragement fragmentProfile = UserProfileFragement.newInstance(screenName);
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
            ft2.replace(R.id.flInfoContainer, fragmentProfile);
            ft2.commit();
        }
    }
}
