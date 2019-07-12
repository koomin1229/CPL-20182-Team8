package com.example.onthejourney.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.R;

import java.util.ArrayList;

public class PhotographerListViewAdapter extends BaseAdapter {

    Context context;
    private String url = "http://ec2-18-222-114-158.us-east-2.compute.amazonaws.com:3000/";
    private ArrayList<Buddy> listViewItemList;
    private ArrayList<ArrayList<String>> image_path_list;


    public PhotographerListViewAdapter(ArrayList<Buddy> arrayList, ArrayList<ArrayList<String>> image_path_list) {
        listViewItemList = arrayList;
        this.image_path_list = image_path_list;

        context = null;
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView titleTextView;
        final ImageView imageView1;
        final ImageView imageView2;
        final ImageView imageView3;
        final ImageView like;
        if (convertView == null) {
            context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.photographer, parent, false);
        }

        like = convertView.findViewById(R.id.like1);
        titleTextView = (TextView) convertView.findViewById(R.id.textView);
        imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);
        imageView2 = (ImageView) convertView.findViewById(R.id.imageView2);
        imageView3 = (ImageView) convertView.findViewById(R.id.imageView3);

        int size = image_path_list.get(position).size();
        if (size >= 1) {
            Glide.with(context).load(url + image_path_list.get(position).get(0)).into(imageView1);
            if (size >= 2)
                Glide.with(context).load(url + image_path_list.get(position).get(1)).into(imageView2);
            if (size >= 3)
                Glide.with(context).load(url + image_path_list.get(position).get(2)).into(imageView3);
        }
        Buddy buddy = listViewItemList.get(position);
        titleTextView.setText(buddy.getName());
        if (buddy.getLikeFlag() == 1)
            like.setBackground(context.getDrawable(R.drawable.like));

        return convertView;
    }

}