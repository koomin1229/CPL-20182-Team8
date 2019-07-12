package com.example.onthejourney.Module;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.onthejourney.Data.Buddy;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

//스트링 배열으로 바꿔서 URL들 리턴하면 더빨리 이미지 띄울수있음
public class RequestHttpURLConnection {
    String url = "http://ec2-18-139-110-4.ap-southeast-1.compute.amazonaws.com:3000/";
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
                        Log.d("option2",option2);
                        Log.d("image_path",image_path);
                        arrayList.add(image_path);
                    }
                }
            } else if (option1.equals("favorite_buddies/customer/")) {
                url += id;
                Log.d("URL",url);
                String jsonPage = getStringFromUrl(url);
                JSONObject json = new JSONObject(jsonPage);
                JSONArray jArr = json.getJSONArray("datas");
                for (int i = 0; i < jArr.length(); i++) {
                    json = jArr.getJSONObject(i);
                    arrayList.add(json.getString(option2));

                }

            }else if (option1.equals("favorite_feeds/customer/")) {
                String jsonPage = null;
                url += id;
                ArrayList<String> feedId = new ArrayList<String>();
                try {
                    jsonPage = getStringFromUrl(url);
                }catch(NullPointerException e){
                    e.printStackTrace();
                }

                JSONObject json = new JSONObject(jsonPage);
                JSONArray jArr = json.getJSONArray("datas");
                for (int i = 0; i < jArr.length(); i++) {
                    json = jArr.getJSONObject(i);
                    feedId.add(json.getString(option2));

                }
                if(feedId.size() > 0) {
                    jsonPage = getStringFromUrl("http://ec2-18-139-110-4.ap-southeast-1.compute.amazonaws.com:3000/feed_items");
                    json = new JSONObject(jsonPage);
                    jArr = json.getJSONArray("datas");
                    for (int i = 0; i < jArr.length(); i++) {
                        json = jArr.getJSONObject(i);
                        for (int j = 0; j < feedId.size(); j++) {
                            if (json.getString("_id").equals(feedId.get(j))) {
                                arrayList.add(json.getString("image_path"));
                                break;
                            }
                        }
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
                    Log.d("In getBuddy", buddy.toString());
                }

            }
            return buddy;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStringFromUrl(String pUrl) {


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
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            }catch (FileNotFoundException e){
                e.printStackTrace();
                return null;
            }
            BufferedReader bufreader = null;
            InputStream contentStream = urlConnection.getInputStream();

            bufreader = new BufferedReader(new InputStreamReader(contentStream, "UTF-8"));
            String line = null;

            //버퍼의 웹문서 소스를 줄단위로 읽어(line), Page에 저장함
            while ((line = bufreader.readLine()) != null) {
                Log.d("line:", line);
                page.append(line);
            }

            bufreader.close();
            return page.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //자원해제

            urlConnection.disconnect();

        }
        return null;
    }// getStringFromUrl()-------------------------


}