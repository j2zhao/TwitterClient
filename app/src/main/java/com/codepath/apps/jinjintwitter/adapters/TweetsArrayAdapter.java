package com.codepath.apps.jinjintwitter.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.jinjintwitter.ProfileActivity;
import com.codepath.apps.jinjintwitter.R;
import com.codepath.apps.jinjintwitter.RoundedTransformation;
import com.codepath.apps.jinjintwitter.fragments.CreateReplyFragment;
import com.codepath.apps.jinjintwitter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jinjinz on 6/27/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    ImageView profile;
    Tweet tweet;
    Button replyBtn;
    AppCompatActivity activity;


    public TweetsArrayAdapter(Context context, ArrayList<Tweet> tweets) {
        super(context, 0, tweets);
        activity = (AppCompatActivity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        tweet = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        profile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvRealName);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        replyBtn = (Button) convertView.findViewById(R.id.btnReply);
        replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateReplyFragment createFragment = CreateReplyFragment.newInstance(Long.toString(tweet.getId()), tweet.getUser().getScreenName());
                FragmentManager fm = activity.getSupportFragmentManager();
                createFragment.show(fm, "compose");

            }
        });

        String scrName = tweet.getUser().getScreenName();
        tvBody.setText(tweet.getBody());
        tvUserName.setText("@" + scrName);
        tvName.setText(tweet.getUser().getName());
        tvTime.setText(tweet.getCreatedAt());
        profile.setImageResource(0);
        profile.setTag(tweet);
        Picasso.with(getContext()).load(tweet.getUser().getProfileUrl())
                .transform(new RoundedTransformation(4, 0))
                .into(profile);

        setListener();
        //setBtnListener();
        return convertView;
    }

    private void setListener() {
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                //String screenName = getIntent().getStringExtra("screen_name");
                Tweet tweet = (Tweet) v.getTag();
                i.putExtra("screen_name", tweet.getUser().getScreenName());
                getContext().startActivity(i);
            }
        });
    }

}
