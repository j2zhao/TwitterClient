package com.codepath.apps.jinjintwitter;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codepath.apps.jinjintwitter.fragments.SearchTimelineFragment;

public class SearchActivity extends AppCompatActivity {
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        query = getIntent().getStringExtra("q");
        SearchTimelineFragment fragmentSearch = SearchTimelineFragment.newInstance(query);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContainer, fragmentSearch);
        ft.commit();
    }

}
