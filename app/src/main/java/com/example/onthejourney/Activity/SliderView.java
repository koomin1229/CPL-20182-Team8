package com.example.onthejourney.Activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.onthejourney.Adapter.SliderAdapter;
import com.example.onthejourney.R;

import java.util.ArrayList;

public class SliderView extends AppCompatActivity {

    SliderAdapter adapter;
    int position;
    ViewPager viewPager;
    ArrayList<String> image_path_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_view);

        image_path_list = getIntent().getStringArrayListExtra("List");
        position = getIntent().getIntExtra("position",0);
        viewPager = (ViewPager)findViewById(R.id.view);
        adapter = new SliderAdapter(this, image_path_list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}
