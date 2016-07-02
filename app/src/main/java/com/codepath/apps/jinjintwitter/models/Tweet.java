package com.codepath.apps.jinjintwitter.models;

/**
 * Created by jinjinz on 6/27/16.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Tweet implements Parcelable {
    private String body;
    private long id;
    private User user;
    private String createdAt;
    private int favorited;
    private int retweet_count;
    private String media_url;

    public void setRetweet_count(int retweet_count) {
        this.retweet_count = retweet_count;
    }

    public void setFavorited(int favorited) {
        this.favorited = favorited;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(body);
        out.writeLong(id);
        out.writeParcelable(user, 0);
        out.writeString(createdAt);
        out.writeInt(favorited);
        out.writeInt(retweet_count);
        out.writeString(media_url);
    }
    public Tweet() {

    }
    private Tweet(Parcel in) {
        body = in.readString();
        id = in.readLong();
        user = in.readParcelable(User.class.getClassLoader());
        createdAt = in.readString();
        favorited = in.readInt();
        retweet_count = in.readInt();
        media_url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<Tweet> CREATOR
            = new Parcelable.Creator<Tweet>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

    public String getBody() {
        return body;
    }

    public long getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getMedia_url() {
        return media_url;
    }

    public boolean getFavorited() {
        if (favorited == 1)
        return true;
        else return false;
    }

    public int getRetweet_count() {
        return retweet_count;
    }

    private String changeCreatedAt(String str) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(str).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int length = relativeDate.indexOf(" ");
        String relativeDateTwitter = relativeDate.substring(0, length + 2);
        return relativeDateTwitter;

    }

    public User getUser() {
        return user;
    }

    public static Tweet fromJSON(JSONObject object) {
        Tweet t = new Tweet();
        try {
            t.body = object.getString("text");
            t.id = object.getLong("id");
            t.createdAt = t.changeCreatedAt(object.getString("created_at"));
            t.user = User.fromJSON(object.getJSONObject("user"));
            if(object.getBoolean("favorited")) t.favorited = 1;
            else t.favorited = 0;
            t.retweet_count = object.getInt("retweet_count");
            try {
                JSONArray media = object.getJSONObject("entities").getJSONArray("media");
                t.media_url = media.getJSONObject(0).getString("media_url_https");
            } catch (JSONException e) {
                t.media_url = null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static ArrayList<Tweet> fromJSONARRAY(JSONArray json) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        for (int i = 0; i < json.length(); i++) {
            try {
                tweets.add(Tweet.fromJSON(json.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tweets;
    }
}
