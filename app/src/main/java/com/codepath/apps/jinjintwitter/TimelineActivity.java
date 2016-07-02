package com.codepath.apps.jinjintwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.jinjintwitter.fragments.CreateFragment;
import com.codepath.apps.jinjintwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.jinjintwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.jinjintwitter.fragments.TweetsFragment;
import com.codepath.apps.jinjintwitter.models.Tweet;
import com.codepath.apps.jinjintwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private TweetsFragment tweetsList;
    TwitterClient client;
    User self;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ViewPager vPager = (ViewPager) findViewById(R.id.viewpager);
        vPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vPager);
        client = TwitterApplication.getRestClient();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                self = User.fromJSON(response);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CreateFragment createFragment = CreateFragment.newInstance(self);
                        FragmentManager fm = getSupportFragmentManager();
                        createFragment.show(fm, "compose");

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(TimelineActivity.this, SearchActivity.class);
                i.putExtra("q", query);
                searchView.clearFocus();
                startActivity(i);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //launch profile view
    public void onProfileClick(MenuItem mi) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }
    //return order of fragments
    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String[] tabTitles = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        //which fragment
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                tweetsList = new HomeTimelineFragment();
                return tweetsList;
            }
            else if (position == 1) return new MentionsTimelineFragment();
            else return null;
        }

        //num of fragments
        @Override
        public int getCount() {
            return tabTitles.length;
        }

        //return tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    public void addTweet(Tweet tweet) {
        tweetsList.addStart(tweet);
    }

}