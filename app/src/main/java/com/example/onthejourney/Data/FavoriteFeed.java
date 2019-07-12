package com.example.onthejourney.Data;

import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteFeed {
    private String feed_id;
    private String customer_id;

    public String getFeed_id() {
        return feed_id;
    }

    public FavoriteFeed(String feed_id, String customer_id) {
        this.feed_id = feed_id;
        this.customer_id = customer_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }


    public JSONObject getJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("feed_id", feed_id);
            jsonObject.put("customer_id", customer_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}