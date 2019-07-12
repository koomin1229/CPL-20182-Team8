package com.example.onthejourney.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.Data.Profile;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Item_likephotographer_adapter extends RecyclerView.Adapter<Item_likephotographer_adapter.ItemViewHolder> {
    ArrayList<ArrayList<String>> image_path_list;
    private String url = "http://ec2-18-222-114-158.us-east-2.compute.amazonaws.com:3000/";
    private ArrayList<Buddy> buddy;
    Context context;
    public Item_likephotographer_adapter(ArrayList<Buddy> buddy, ArrayList<ArrayList<String>> image_path_list) {
        this.buddy = buddy;
        this.image_path_list = image_path_list;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_likephotographer_recycler_view, viewGroup, false);
        context = viewGroup.getContext();
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int i) {

        new HttpAsyncTask("GET", "profiles/buddy/" + buddy.get(i).getBuddy_id(), null, null, new TypeToken<ResultBody<Profile>>() {
        }.getType(),
                new MyCallBack() {
                    @Override
                    public void doTask(Object resultBody) {
                        ResultBody<Profile> result = (ResultBody<Profile>) resultBody;

                        if (result.getDatas().size() == 0 && image_path_list.size() != 0) {
                            Glide.with(context).load("http://ec2-18-222-114-158.us-east-2.compute.amazonaws.com:3000/" + image_path_list.get(i).get(0)).into(itemViewHolder.imageView);
                        }
                        else if(result.getDatas().size() == 0 && image_path_list.size() != 0){
                            return;
                        }
                        else
                            Glide.with(context).load("http://ec2-18-222-114-158.us-east-2.compute.amazonaws.com:3000/" + result.getDatas().get(0).getImage_path()).into(itemViewHolder.imageView);

                        Log.d("Buddy in likephotogra",buddy.get(i).toString());
                        itemViewHolder.location.setText(buddy.get(i).getLocation_name());
                        itemViewHolder.textView.setText(buddy.get(i).getTitle());

                    }
                }
        ).execute();

    }


    @Override
    public int getItemCount() {
        if(buddy == null){
            return 0;
        }
        return buddy.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private TextView location;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_likephotographer_image);
            textView = (TextView) itemView.findViewById(R.id.item_like_buddyName);
            location = itemView.findViewById(R.id.LikeBuddyLocation);
        }
    }
}
