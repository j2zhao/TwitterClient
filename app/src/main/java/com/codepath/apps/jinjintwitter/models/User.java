package com.codepath.apps.jinjintwitter.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jinjinz on 6/27/16.
 */
public class User implements Parcelable{
    private String name;
    private long uid;
    private String screenName;
    private String profileUrl;
    private String description;
    private String backgrUrl;
    private int followersCount;
    private int followingCount;
    private int follow;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeLong(uid);
        out.writeString(screenName);
        out.writeString(profileUrl);
        out.writeString(description);
        out.writeString(backgrUrl);
        out.writeInt(followersCount);
        out.writeInt(followingCount);
        out.writeInt(follow);
    }

    private User(Parcel in) {
        name = in.readString();
        uid = in.readLong();
        screenName = in.readString();
        profileUrl = in.readString();
        description = in.readString();
        backgrUrl = in.readString();
        followersCount = in.readInt();
        followingCount = in.readInt();
        follow = in.readInt();
    }

    public String getName() {
        if (name == null) {
            return "Jinjin Zhao";
        }
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        if (name == null) {
            return "j2zhao";
        }
        return screenName;
    }

    public String getProfileUrl() {
        if (profileUrl == null) {
            return "http://pbs.twimg.com/profile_images/747526376920518657/6niF_Pun_normal.jpg";
        }
        return profileUrl;
    }


    public String getDescription() {
        return description;
    }

    public String getBackgrUrl() {
        return backgrUrl;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public Boolean getFollow() {
        if (follow == 0) return false;
        if (follow == 1) return true;
        else return null;
    }

    public User() {

    }
    public static User fromJSON(JSONObject object) {
        User u = new User();
        try {
            u.name = object.getString("name");
            u.uid = object.getLong("id");
            u.screenName = object.getString("screen_name");
            u.profileUrl = object.getString("profile_image_url");
            u.description = object.getString("description");
            u.backgrUrl = object.getString("profile_background_image_url");
            u.followersCount = object.getInt("followers_count");
            u.followingCount = object.getInt("friends_count");
            if (object.get("following")==null) {
                u.follow = -1;
            }
            else if (object.getBoolean("following"))  {
                u.follow = 1;
            }
            else if (!object.getBoolean("following")) {
                u.follow = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };



}
