package com.codepath.apps.jinjintwitter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.jinjintwitter.DetailsActivity;
import com.codepath.apps.jinjintwitter.R;
import com.codepath.apps.jinjintwitter.adapters.TweetsArrayAdapter;
import com.codepath.apps.jinjintwitter.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinjinz on 6/28/16.
 */
public class TweetsFragment extends Fragment {
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter adapterT;
    private ListView lvHome;
    private SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets, container, false);
        lvHome = (ListView) v.findViewById(R.id.lvHomeTweet);
        lvHome.setAdapter(adapterT);
        lvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Clicked", "YES");
                Intent i = new Intent(getContext(), DetailsActivity.class);
                i.putExtra("tweet", tweets.get(position));
                getActivity().startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        tweets = new ArrayList<Tweet>();
        adapterT = new TweetsArrayAdapter(getActivity(), tweets);
        super.onCreate(savedInstanceState);

    }
    public void addAll(List<Tweet> tweets) {
        adapterT.addAll(tweets);
    }

    public void clear() {
        adapterT.clear();
    }

    public void addStart(Tweet tweet) {
        tweets.add(0, tweet);
        adapterT.notifyDataSetChanged();
    }
}

