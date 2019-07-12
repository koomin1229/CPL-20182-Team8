package com.example.onthejourney.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.maps.android.clustering.ClusterItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Customer implements Parcelable {
    private String nickname;
    private String customer_id;
    private String name;

    public Customer(String nickname, String customer_id, String name) {
        this.nickname = nickname;
        this.customer_id = customer_id;
        this.name = name;
    }

    protected Customer(Parcel in) {
        nickname = in.readString();
        customer_id = in.readString();
        name = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public String getNickname() {
        return nickname;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getName() {
        return name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "nickname='" + nickname + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public JSONObject getJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customer_id", customer_id);
            jsonObject.put("nickname", nickname);
            jsonObject.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickname);
        dest.writeString(customer_id);
        dest.writeString(name);
    }
}
