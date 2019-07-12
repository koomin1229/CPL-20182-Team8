package com.example.onthejourney.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.onthejourney.Activity.SliderView;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.Data.FavoriteFeed;
import com.example.onthejourney.Data.FeedItem;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class HashTagFeedItemAdapter extends RecyclerView.Adapter<ViewHolder> {
    String url = "http://ec2-18-222-114-158.us-east-2.compute.amazonaws.com:3000/";
    private ArrayList<FeedItem> feedItems = null;
    private ArrayList<String> image_path_arr = new ArrayList<String>();;
    private Context context;
    private Customer customer;

    public ArrayList<FeedItem> getFeedItems() {
        return feedItems;
    }

    public void setFeedItems(ArrayList<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }

    public HashTagFeedItemAdapter(ArrayList<FeedItem> feedItems, ArrayList<String> image_path_arr, Context context, Customer customer) {
        this.feedItems = feedItems;
        this.image_path_arr = image_path_arr;
        this.context = context;
        this.customer = customer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewimage, parent, false);
        return new ViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Glide.with(context).load(url+feedItems.get(position).getImage_path()).into(viewHolder.imageView);
        Log.d("inadapter",url+feedItems.get(position).getImage_path());
        viewHolder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new HttpAsyncTask("POST", "favorite_feeds",
                        new FavoriteFeed(feedItems.get(position).get_id(), customer.getCustomer_id()).getJsonObject(),
                        null, new TypeToken<ResultBody<FavoriteFeed>>(){}.getType()
                        , new MyCallBack() {
                    @Override
                    public void doTask(Object resultBody) {
                        Toast.makeText(context, "좋아하는 사진에 추가했어요~", Toast.LENGTH_SHORT).show();
                        Log.d("favorite_feed", "maked");
                    }
                }).execute();






            }
        });
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }
}