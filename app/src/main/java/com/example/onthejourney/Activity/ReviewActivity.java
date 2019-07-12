package com.example.onthejourney.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onthejourney.Adapter.Item_review_adapter;
import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.Data.CheckList;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.Data.Post_scripts;
import com.example.onthejourney.Dialog.CheckListDialog;
import com.example.onthejourney.Dialog.PostScriptDialog;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.reflect.TypeToken;


import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    Buddy buddy;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Context context;
    public  ArrayList<Post_scripts> post_scripts = new ArrayList<Post_scripts>();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        buddy = getIntent().getParcelableExtra("Buddy");
        final Customer me = (Customer) getIntent().getParcelableExtra("Customer");

        context = getApplicationContext();

        TextView buddyId;
        buddyId = (TextView) findViewById(R.id.item_review_buddyid);
        buddyId.setText(buddy.getTitle());

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


        Button button = findViewById(R.id.btnReview);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostScriptDialog dialog = new PostScriptDialog(v.getContext());
                dialog.setDialogListener(new PostScriptDialog.MyReviewDialogListener() {
                    @Override
                    public void onPositiveClicked(Post_scripts post_script) {
                        post_script.setBuddy_id(buddy.getBuddy_id());
//                        post_script.setCustomer_id(me.getCustomer_id());
                        post_script.setCustomer_id("hhm");

                        post_script.setComments(post_script.getComments());
                        post_script.setGrade(post_script.getGrade());
                        JSONObject jsonObject = post_script.getJsonObject();
                        new HttpAsyncTask("POST", "post_scripts", jsonObject, null, new TypeToken<ResultBody<Post_scripts>>() {
                        }.getType(),
                                new MyCallBack() {
                                    @Override
                                    public void doTask(Object resultBody) {
                                        ResultBody<Post_scripts> result = (ResultBody<Post_scripts>) resultBody;
                                    }
                                })
                                .execute();
                        Toast.makeText(context, "후기 작성이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNegativeClicked() {
                        Toast.makeText(context, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                        Log.d("MyReviewDialogListener", "onNegativeClicked");
                    }
                });
                dialog.show();
            }
        });

    }

}