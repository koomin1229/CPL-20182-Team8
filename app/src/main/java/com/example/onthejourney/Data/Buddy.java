package com.example.onthejourney.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Buddy implements ClusterItem, Parcelable {
    private String _id = null;
    private LatLng active_location;
    private double latitude = 0;
    private double longitude = 0;
    private String name = null;
    private String mSnippet = null;
    private String buddy_id = null;
    private String location_name = null;
    private ArrayList<String> price_names = null;
    private ArrayList<String> price_moneys = null;
    private ArrayList<String> hashtag =null;
    private String notification = null;
    private int likeFlag = 0;

    public Buddy(){}
    public Buddy(double lat, double lng) {
        this.latitude = lat;
        this.longitude = lng;
        active_location = new LatLng(lat, lng);
    }
    public Buddy(double lat, double lng, String buddy_id) {
        active_location = new LatLng(lat, lng);
        this.latitude = lat;
        this.longitude= lng;
        this.buddy_id = buddy_id;
    }
    public Buddy(double lat, double lng, String title, String snippet, String buddy_id, ArrayList<String> price_names) {
        active_location = new LatLng(lat, lng);
        this.latitude = lat;
        this.longitude = lng;
        name = title;
        mSnippet = snippet;
        this.buddy_id = buddy_id;
        this.price_names = price_names;
    }
    public Buddy(Buddy buddy){
        this.buddy_id = buddy.buddy_id;
        this.name = buddy.name;
        this.active_location = buddy.active_location;
    }

    protected Buddy(Parcel in) {
        active_location = in.readParcelable(LatLng.class.getClassLoader());
        latitude = in.readDouble();
        longitude = in.readDouble();
        name = in.readString();
        mSnippet = in.readString();
        location_name = in.readString();
        buddy_id = in.readString();
        in.readList(price_names, null);
        in.readList(price_moneys, null);
        in.readList(hashtag, null);
        notification = in.readString();
        likeFlag = in.readInt();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public static final Creator<Buddy> CREATOR = new Creator<Buddy>() {
        @Override
        public Buddy createFromParcel(Parcel in) {
            return new Buddy(in);
        }

        @Override
        public Buddy[] newArray(int size) {
            return new Buddy[size];
        }
    };

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setmTitle(String mTitle) {
        this.name = mTitle;
    }

    public void setBuddy_id(String buddy_id){
        this.buddy_id = buddy_id;
    }
    @Override
    public LatLng getPosition() {
        return active_location;
    }

    public LatLng getmPosition() {
        return active_location;
    }

    public void setmPosition(LatLng mPosition) {
        this.active_location = mPosition;
        this.latitude = active_location.latitude;
        this.longitude = active_location.longitude;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public String getBuddy_id() {
        return buddy_id;
    }

    public int getLikeFlag(){return this.likeFlag;}
    public void setLikeFlag(int flag){
        this.likeFlag = flag;
    }
    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeParcelable(this.active_location, flags);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.name);
        dest.writeString(this.mSnippet);
        dest.writeString(this.location_name);
        dest.writeString(this.buddy_id);
        dest.writeStringList(this.price_names);
        dest.writeStringList(this.price_moneys);
        dest.writeStringList(this.hashtag);
        dest.writeString(this.notification);
        dest.writeInt(this.likeFlag);
    }

    public JSONObject getJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("buddy_id", buddy_id);
            jsonObject.put("latitude",latitude);
            jsonObject.put("longitude",longitude);
            jsonObject.put("location_name",location_name);
            jsonObject.put("name", name);
            jsonObject.put("price_list",price_names);
            jsonObject.put("price_moneys",price_moneys);
            jsonObject.put("hashtag",hashtag);
            jsonObject.put("notification",notification);
            jsonObject.put("hashtag",hashtag);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return "Buddy{" +
                "active_location=" + active_location +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", mSnippet='" + mSnippet + '\'' +
                ", buddy_id='" + buddy_id + '\'' +
                ", location_name='" + location_name + '\'' +
                ", price_names=" + price_names +
                ", price_moneys=" + price_moneys +
                ", hashtag=" + hashtag +
                ", notification='" + notification + '\'' +
                ", likeFlag=" + likeFlag +
                '}';
    }
}