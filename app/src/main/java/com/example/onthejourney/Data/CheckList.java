package com.example.onthejourney.Data;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class CheckList {
    private String _id = null;
    private String customer_id=null;
    private String buddy_id = null;
    private String customer_name=null;
    private String buddy_name=null;
    private String state = null;
    private String start_year = null;
    private String start_month = null;
    private String start_day = null;
    private String end_year = null;
    private String end_month = null;
    private String end_day = null;
    private String key = null;
    private String location_name = null;
    private int people_number = 0;
    private ArrayList<String> requirement_list = null;
    private int suggested_price = 0;



    public CheckList(){

    }

    public CheckList(String customer_id, String buddy_id, String state, String start_year, String start_month, String start_day, String end_year, String end_month, String end_day, String location, int people_number, ArrayList<String> requirement_list, int suggested_price) {
        this.customer_id = customer_id;
        this.buddy_id = buddy_id;
        this.state = state;
        this.start_year = start_year;
        this.start_month = start_month;
        this.start_day = start_day;
        this.end_year = end_year;
        this.end_month = end_month;
        this.end_day = end_day;
        this.location_name = location;
        this.people_number = people_number;
        this.requirement_list = requirement_list;
        this.suggested_price = suggested_price;
    }



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getBuddy_name() {
        return buddy_name;
    }

    public void setBuddy_name(String buddy_name) {
        this.buddy_name = buddy_name;
    }

    public String getStart_year() {
        return start_year;
    }

    public void setStart_year(String start_year) {
        this.start_year = start_year;
    }

    public String getStart_month() {
        return start_month;
    }

    public void setStart_month(String start_month) {
        this.start_month = start_month;
    }

    public String getStart_day() {
        return start_day;
    }

    public void setStart_day(String start_day) {
        this.start_day = start_day;
    }

    public String getEnd_year() {
        return end_year;
    }

    public void setEnd_year(String end_year) {
        this.end_year = end_year;
    }

    public String getEnd_month() {
        return end_month;
    }

    public void setEnd_month(String end_month) {
        this.end_month = end_month;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getBuddy_id() {
        return buddy_id;
    }

    public void setBuddy_id(String buddy_id) {
        this.buddy_id = buddy_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLocation() {
        return location_name;
    }

    public void setLocation(String location_name) {
        this.location_name = location_name;
    }

    public int getPeople_number() {
        return people_number;
    }

    public void setPeople_number(int people_number) {
        this.people_number = people_number;
    }

    public ArrayList<String> getRequirement_list() {
        return requirement_list;
    }

    public void setRequirement_list(ArrayList<String> requirement_list) {
        this.requirement_list = requirement_list;
    }

    public int getSuggested_price() {
        return suggested_price;
    }

    public void setSuggested_price(int suggested_price) {
        this.suggested_price = suggested_price;
    }

    public JSONObject getJsonObject(String customer_id, String buddy_id, String state, Date start_time, Date end_time, LatLng location_name, int people_number, int suggested_price, JSONArray array){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("customer_id", customer_id);
            jsonObject.put("buddy_id", buddy_id);
            jsonObject.put("state", state);
            jsonObject.put("start_year", start_year);
            jsonObject.put("start_month", start_month);
            jsonObject.put("start_day", start_day);
            jsonObject.put("end_year", end_year);
            jsonObject.put("end_month", end_month);
            jsonObject.put("end_day", end_day);
            jsonObject.put("location_name",location_name);
            jsonObject.put("people_number", people_number);
            jsonObject.put("suggested_price", suggested_price);
            jsonObject.put("requirement_list", array);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
    public JSONObject getJsonObject(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("customer_id", customer_id);
            jsonObject.put("buddy_id", buddy_id);
            jsonObject.put("customer_name", customer_name);
            jsonObject.put("buddy_name", buddy_name);
            jsonObject.put("state", state);
            jsonObject.put("start_year", start_year);
            jsonObject.put("start_month", start_month);
            jsonObject.put("start_day", start_day);
            jsonObject.put("end_year", end_year);
            jsonObject.put("end_month", end_month);
            jsonObject.put("end_day", end_day);
            jsonObject.put("key",key);
            jsonObject.put("location_name",location_name);
            jsonObject.put("people_number", people_number);
            jsonObject.put("suggested_price", suggested_price);
            jsonObject.put("requirement_list", requirement_list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return "CheckList{" +
                "customer_id='" + customer_id + '\'' +
                ", buddy_id='" + buddy_id + '\'' +
                ", state='" + state + '\'' +
                ", start_year='" + start_year + '\'' +
                ", start_month='" + start_month + '\'' +
                ", start_day='" + start_day + '\'' +
                ", end_year='" + end_year + '\'' +
                ", end_month='" + end_month + '\'' +
                ", end_day='" + end_day + '\'' +
                ", key='" + key + '\'' +
                ", location_name='" + location_name + '\'' +
                ", people_number=" + people_number +
                ", requirement_list=" + requirement_list +
                ", suggested_price=" + suggested_price +
                '}';
    }
}
