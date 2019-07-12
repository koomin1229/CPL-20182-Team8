package com.example.onthejourney.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.onthejourney.Data.Post_scripts;
import com.example.onthejourney.R;

import java.util.ArrayList;

public class Item_review_adapter extends RecyclerView.Adapter<Item_review_adapter.ItemViewHolder>{
    private ArrayList<Post_scripts> post_scripts;
    private Context context;
    private int temp;
    private String tempstr;

    public Item_review_adapter(ArrayList<Post_scripts> post_scripts) {
        this.post_scripts = post_scripts;
    }

    @NonNull
    @Override
    public Item_review_adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_review_recycler_view, viewGroup, false);
        context = viewGroup.getContext();
        return new Item_review_adapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Item_review_adapter.ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.customerId.setText(post_scripts.get(i).getCustomer_id());
        temp = post_scripts.get(i).getGrade();
        tempstr = Integer.toString(temp);
        itemViewHolder.grade.setText(tempstr);
        itemViewHolder.comments.setText(post_scripts.get(i).getComments());
    }

    @Override
    public int getItemCount() {
        return post_scripts.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView customerId;
        TextView grade;
        TextView comments;

        public ItemViewHolder(View itemView) {
            super(itemView);
            customerId = (TextView) itemView.findViewById(R.id.item_review_customerid);
            grade = (TextView) itemView.findViewById(R.id.item_review_grade);
            comments = (TextView) itemView.findViewById(R.id.item_review_comments);
        }
    }
}