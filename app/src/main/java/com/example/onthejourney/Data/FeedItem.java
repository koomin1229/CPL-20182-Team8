package com.example.onthejourney.Data;

import java.util.ArrayList;

public class FeedItem {
    private String _id;
    private String buddy_id;
    private String image_path;
    private String favorite_num;
    private String location_name;
    private ArrayList<String> hashtag;

    public String getBuddy_id() {
        return buddy_id;
    }

    public FeedItem(String _id, String buddy_id, String image_path, String favorite_num, String location_name, ArrayList<String> hashtag) {
        this._id = _id;
        this.buddy_id = buddy_id;
        this.image_path = image_path;
        this.favorite_num = favorite_num;
        this.location_name = location_name;
        this.hashtag = hashtag;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setBuddy_id(String buddy_id) {
        this.buddy_id = buddy_id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getFavorite_num() {
        return favorite_num;
    }

    public void setFavorite_num(String favorite_num) {
        this.favorite_num = favorite_num;
    }


    public ArrayList<String> getHashtag() {
        return hashtag;
    }

    public void setHashtag(ArrayList<String> hashtag) {
        this.hashtag = hashtag;
    }

}