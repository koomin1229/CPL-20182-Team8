package com.example.immmy.myapplication.Module;

import android.util.Log;

import com.example.immmy.myapplication.Data.Buddy;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//스트링 배열으로 바꿔서 URL들 리턴하면 더빨리 이미지 띄울수있음
public class RequestHttpURLConnection {
    String url = "http://ec2-18-222-114-158.us-east-2.compute.amazonaws.com:3000/";
    HttpURLConnection con = null;

    public ArrayList<String> getJsonText(String option1, String option2, String id) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (option1 != null) {
            url += option1;
        }
        try {
            if (option1.equals("feed_items")) {
                String jsonPage = getStringFromUrl(url);
                JSONObject json = new JSONObject(jsonPage);
                JSONArray jArr = json.getJSONArray("datas");
                for (int i = 0; i < jArr.length(); i++) {
                    json = jArr.getJSONObject(i);
                    if (json.getString("buddy_id").equals(id)) {
                        String image_path = json.getString(option2);

                        arrayList.add(image_path);
                    }
                }
            } else if (option1.equals("customers")) {
                String jsonPage = getStringFromUrl(url);
                JSONObject json = new JSONObject(jsonPage);
                JSONArray jArr = json.getJSONArray("datas");
                JSONArray option2List = null;

                for (int i = 0; i < jArr.length(); i++) {
                    json = jArr.getJSONObject(i);
                    if (json.getString("customer_id").equals(id)) {
                        option2List = json.getJSONArray(option2);
                        Log.d("option2List", option2List.toString());
                        break;
                    }
                }
                if (option2.equals("favorite_buddy_id_list") && option2List == null) {
                    return null;
                } else {
                    for (int i = 0; i < option2List.length(); i++) {
                        Log.d("forloop option2List", option2List.toString());
                        arrayList.add(option2List.getString(i));
                    }
                }
            }

            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Buddy getBuddy(String option1, String id) {
        Buddy buddy = null;
        if (option1 != null) {
            url += option1;
        }
        try {

            String jsonPage = getStringFromUrl(url);
            JSONObject json = new JSONObject(jsonPage);
            JSONArray jArr = json.getJSONArray("datas");
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                if (json.getString("buddy_id").equals(id)) {
                    LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
                    buddy = new Buddy(DEFAULT_LOCATION.latitude, DEFAULT_LOCATION.longitude);
                    buddy.setBuddy_id(id);
                    buddy.setmTitle(json.getString("name"));
                    Log.d("In getBuddy",buddy.toString());
                }

            }
            return buddy;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStringFromUrl(String pUrl) {

        BufferedReader bufreader = null;
        HttpURLConnection urlConnection = null;

        StringBuffer page = new StringBuffer(); //읽어온 데이터를 저장할 StringBuffer객체 생성

        try {

            //[Type1]
            /*
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(pUrl));
            InputStream contentStream = response.getEntity().getContent();
            */

            //[Type2]
            URL url = new URL(pUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream contentStream = urlConnection.getInputStream();

            bufreader = new BufferedReader(new InputStreamReader(contentStream, "UTF-8"));
            String line = null;

            //버퍼의 웹문서 소스를 줄단위로 읽어(line), Page에 저장함
            while ((line = bufreader.readLine()) != null) {
                Log.d("line:", line);
                page.append(line);
            }

            return page.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //자원해제
            try {
                bufreader.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }// getStringFromUrl()-------------------------


}