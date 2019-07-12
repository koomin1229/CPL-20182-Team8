package com.example.onthejourney.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.onthejourney.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginSelectActivity extends AppCompatActivity {
    private static String jsonConnection(String targetUrl){
        URL url = null;
        HttpURLConnection conn = null;
        String jsonData = "";
        BufferedReader br = null;
        StringBuffer sb = null;
        String returnText = "";

        try {
            url = new URL(targetUrl);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("Get");
            conn.connect();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            sb = new StringBuffer();

            while ((jsonData = br.readLine()) != null) {
                sb.append(jsonData);
            }
            returnText = sb.toString();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                if (br != null) br.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return returnText;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_select);

        ImageButton c_login_button= (ImageButton)findViewById(R.id.c_login_button);
        ImageButton b_login_button = (ImageButton)findViewById(R.id.b_login_button);

        c_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customer_login = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ec2-18-139-110-4.ap-southeast-1.compute.amazonaws.com:3000/auth/google"));
                startActivity(customer_login);
            }
        });

        b_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buddy_login = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ec2-18-139-110-4.ap-southeast-1.compute.amazonaws.com:3000/auth/google"));
                startActivity(buddy_login);
            }
        });
    }



}