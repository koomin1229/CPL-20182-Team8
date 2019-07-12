package com.example.onthejourney.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.onthejourney.Activity.SliderView;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    String url = "http://ec2-18-222-114-158.us-east-2.compute.amazonaws.com:3000/";
    private ArrayList<String> image_path_arr = new ArrayList<String>();;
    private Context context;
    private Customer customer;

    public RecyclerViewAdapter(Context context, ArrayList<String> list, Customer customer){
        this.context = context;
        this.image_path_arr = list;
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
        Glide.with(context).load(url+image_path_arr.get(position)).into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SliderView.class);
                intent.putExtra("Customer",customer);
                intent.putExtra("position",position);
                intent.putExtra("List",image_path_arr);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (image_path_arr == null)
            return 0;
        else
            return image_path_arr.size();
    }

}