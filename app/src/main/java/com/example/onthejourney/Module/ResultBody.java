package com.example.onthejourney.Module;


import java.io.Serializable;
import java.util.ArrayList;

public class ResultBody<T> implements Serializable {
    private String success;
    private String size;
    private ArrayList<T> datas;
    private String error;
    private String newUser;


    public ResultBody(String success, String size, ArrayList<T> datas, String error, String newUser) {

        this.success = success;
        this.size = size;
        this.datas = datas;
        this.error = error;
        this.newUser = newUser;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    public String getSuccess() {
        return success;
    }

    public String getSize() {
        return size;
    }

    public ArrayList<T> getDatas() {
        return datas;
    }

    public void setData(ArrayList<T> data) {
        this.datas = data;
    }

    public String getError() {
        return error;
    }
}