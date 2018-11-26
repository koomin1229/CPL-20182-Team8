package com.example.onthejourney.Data;

import org.json.JSONException;
import org.json.JSONObject;

public class Favorite_Buddy {
    private String _id = null;
    private String buddy_id = null;
    private String customer_id = null;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBuddy_id() {
        return buddy_id;
    }

    public void setBuddy_id(String buddy_id) {
        this.buddy_id = buddy_id;
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

            jsonObject.put("buddy_id",buddy_id);
            jsonObject.put("customer_id",customer_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return "Favorite_Buddy{" +
                "_id='" + _id + '\'' +
                ", buddy_id='" + buddy_id + '\'' +
                ", customer_id='" + customer_id + '\'' +
                '}';
    }
}
