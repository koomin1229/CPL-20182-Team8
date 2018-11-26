package com.example.onthejourney.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onthejourney.Adapter.Item_review_adapter;
import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.Data.Post_scripts;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    Buddy buddy;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Context mContext;
    public  ArrayList<Post_scripts> post_scripts = new ArrayList<Post_scripts>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        buddy = getIntent().getParcelableExtra("Buddy");

        mContext = getApplicationContext();

        TextView buddyId;
        buddyId = (TextView) findViewById(R.id.item_review_buddyid);
        buddyId.setText(buddy.getBuddy_id());

        recyclerView = findViewById(R.id.rvReview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        new HttpAsyncTask("GET", "post_scripts/buddy/" + buddy.getBuddy_id(), null, null, new TypeToken<ResultBody<Post_scripts>>() {
        }.getType(),
                new MyCallBack() {
                    @Override
                    public void doTask(Object resultBody) {
                        ResultBody<Post_scripts> result = (ResultBody<Post_scripts>) resultBody;

                        for (int i = 0; i < result.getDatas().size(); i++) {
                            post_scripts.add((Post_scripts) result.getDatas().get(i));
                        }
                        Item_review_adapter adapter = new Item_review_adapter(post_scripts);
                        recyclerView.setAdapter(adapter);
                    }
                }).execute();
    }

}