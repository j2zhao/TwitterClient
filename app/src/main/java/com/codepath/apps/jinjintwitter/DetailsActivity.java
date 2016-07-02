package com.codepath.apps.jinjintwitter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.jinjintwitter.fragments.CreateReplyFragment;
import com.codepath.apps.jinjintwitter.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends AppCompatActivity {
    Tweet tweet;
    ImageView tweetPic;
    ImageView ivProfile;
    TextView tvRealName;
    TextView tvUserName;
    TextView tvTime;
    TextView tvBody;
    TextView tvRetweet;
    Button btnReply;
    Button btnRetweet;
    Button btnFave;
    TwitterClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tweet = getIntent().getParcelableExtra("tweet");
        tweetPic = (ImageView) findViewById(R.id.tweetPic);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvRealName = (TextView) findViewById(R.id.tvRealName);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvBody = (TextView) findViewById(R.id.tvBody);
        btnReply = (Button) findViewById(R.id.btnReply);
        btnRetweet = (Button) findViewById(R.id.btnRetweet);
        btnFave = (Button) findViewById(R.id.btnFave);
        tvRetweet = (TextView) findViewById(R.id.tvRetweet);
        if(tweet.getMedia_url() != null ) {
            Picasso.with(this).load(tweet.getMedia_url()).into(tweetPic);
        }
        Picasso.with(this).load(tweet.getUser().getProfileUrl())
                .transform(new RoundedTransformation(4, 0))
                .into(ivProfile);
        tvRealName.setText(tweet.getUser().getName());
        tvUserName.setText("@" + tweet.getUser().getScreenName());
        tvTime.setText(tweet.getCreatedAt());
        tvBody.setText(tweet.getBody());
        tvRetweet.setText(Integer.toString(tweet.getRetweet_count()));
        client = TwitterApplication.getRestClient();
        if (tweet.getFavorited()) {
            btnFave.setBackground(getDrawable(R.drawable.ic_favorite_black_24dp));
        }

        else {
            btnFave.setBackground(getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateReplyFragment createFragment = CreateReplyFragment.newInstance(Long.toString(tweet.getId()), tweet.getUser().getScreenName());
                FragmentManager fm = getSupportFragmentManager();
                createFragment.show(fm, "compose");
            }
        });

        btnFave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tweet.getFavorited()) {
                    client.delFave(tweet.getId(), new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            btnFave.setBackground(getDrawable(R.drawable.ic_favorite_border_black_24dp));
                            tweet.setFavorited(0);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                }

                else {
                    client.addFave(tweet.getId(), new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            btnFave.setBackground(getDrawable(R.drawable.ic_favorite_black_24dp));
                            tweet.setFavorited(1);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        }
                    });
                }
            }
        });

        btnRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.writeRetweet(tweet.getId(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        tweet.setRetweet_count(tweet.getRetweet_count() + 1);
                        tvRetweet.setText(Integer.toString(tweet.getRetweet_count()));
                        Toast.makeText(getBaseContext(), "Retweeted",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });

    }

}
