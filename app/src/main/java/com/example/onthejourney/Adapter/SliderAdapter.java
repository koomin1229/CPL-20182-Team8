package com.example.onthejourney.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onthejourney.R;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {
    private ArrayList<String> image_path_list;
    String url = "http://ec2-18-222-114-158.us-east-2.compute.amazonaws.com:3000/";
    private Context context;
    private LayoutInflater inflater;

    public SliderAdapter(Context context, ArrayList<String> list){
        this.context = context;
        this.image_path_list = list;
    }

    @Override
    public int getCount() {
        return image_path_list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==((View)o);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //초기화
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider, container, false);
        ImageView sliderImageView = (ImageView)v.findViewById(R.id.sliderImageView);

        Glide.with(context).load(url+image_path_list.get(position)).into(sliderImageView);
        container.addView(v);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
//        super.destroyItem(container, position, object);
    }

}
