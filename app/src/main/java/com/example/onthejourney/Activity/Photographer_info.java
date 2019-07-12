
package com.example.onthejourney.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.onthejourney.Adapter.RecyclerViewAdapter;
import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.Data.CheckList;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.Data.Favorite_Buddy;
import com.example.onthejourney.Data.Profile;
import com.example.onthejourney.Dialog.CheckListDialog;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Photographer_info extends AppCompatActivity {

    private Buddy buddy;
    private Customer me;
    Context context;
    private ImageView imageView;
    private RecyclerView rvSample;
    private boolean like = false;
    private Button request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_info);

        buddy = (Buddy) getIntent().getParcelableExtra("Buddy");


        context = this;
        //final Buddy buddy = (Buddy) getIntent().getParcelableExtra("Buddy");
        final Customer me = (Customer) getIntent().getParcelableExtra("Customer");
        final ArrayList<String> image_path_list = (ArrayList<String>) getIntent().getStringArrayListExtra("List");

        Log.d("photobuddy", buddy.toString());

        if (me != null) {
            LinearLayout linearLayout = findViewById(R.id.ButtonLinear);
            linearLayout.setVisibility(LinearLayout.VISIBLE);
            request = findViewById(R.id.request);
            request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CheckListDialog dialog = new CheckListDialog(v.getContext());
                    dialog.setDialogListener(new CheckListDialog.MyDialogListener() {
                        @Override
                        public void onPositiveClicked(CheckList checkList) {
                            Log.d("checklist", checkList.toString());
                            checkList.setState("예약중");
                            checkList.setBuddy_id(buddy.getBuddy_id());
                            checkList.setBuddy_name(buddy.getName());
                            checkList.setCustomer_id(me.getCustomer_id());
                            checkList.setCustomer_name(me.getName());
                            JSONObject jsonObject = checkList.getJsonObject();
                            new HttpAsyncTask("POST", "checkLists", jsonObject, null, new TypeToken<ResultBody<CheckList>>() {
                            }.getType(),
                                    new MyCallBack() {
                                        @Override
                                        public void doTask(Object resultBody) {
                                            ResultBody<CheckList> result = (ResultBody<CheckList>) resultBody;
                                        }
                                    })
                                    .execute();
                            Toast.makeText(context, "작업 제안이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNegativeClicked() {
                            Toast.makeText(context, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                            Log.d("MyDialogListener", "onNegativeClicked");
                        }
                    });
                    dialog.show();
                }
            });
        } else {
            LinearLayout linearLayout = findViewById(R.id.ButtonLinear);
            linearLayout.setVisibility(LinearLayout.GONE);
        }

        TextView textView = findViewById(R.id.textView2);
        textView.setText(buddy.getTitle());


        final ImageView likeText = findViewById(R.id.likeInInfo);


        if (buddy.getLikeFlag() == 1) {
            like = true;
            likeText.setBackground(getDrawable(R.drawable.like));
        }
        likeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (like == false) like = true; //true이면 선호작가에 추가
                else like = false;
                if(like)
                    likeText.setBackground(getDrawable(R.drawable.like));
                else
                    likeText.setBackground(getDrawable(R.drawable.unlike));
                new HttpAsyncTask("GET", "favorite_buddies" , null, null, new TypeToken<ResultBody<Favorite_Buddy>>() {
                }.getType(),
                        new MyCallBack() {
                            @Override
                            public void doTask(Object resultBody) {

                                Favorite_Buddy fb = new Favorite_Buddy();

                                ResultBody<Favorite_Buddy> result = (ResultBody<Favorite_Buddy>) resultBody;
                                for(int i=0;i<result.getDatas().size();i++){
                                    fb = result.getDatas().get(i);
                                    if(fb.getBuddy_id().equals(buddy.getBuddy_id()) && fb.getCustomer_id().equals(me.getCustomer_id())){
                                        break;
                                    }
                                }

                                Log.d("_id",fb.toString());

                                if(like) {
                                    JSONObject jsonObject = fb.getJsonObject();
                                    new HttpAsyncTask("POST", "favorite_buddies/" , jsonObject, null, new TypeToken<ResultBody<Favorite_Buddy>>() {
                                    }.getType(),
                                            new MyCallBack() {
                                                @Override
                                                public void doTask(Object resultBody) {
                                                    ResultBody<Favorite_Buddy> result = (ResultBody<Favorite_Buddy>) resultBody;
                                                }
                                            })
                                            .execute();
                                }
                                else{
                                    Log.d("else",fb.toString());
                                    new HttpAsyncTask("DELETE", "favorite_buddies/" + fb.get_id(),null, null, new TypeToken<ResultBody<Favorite_Buddy>>() {
                                    }.getType(),
                                            new MyCallBack() {
                                                @Override
                                                public void doTask(Object resultBody) {
                                                    ResultBody<Favorite_Buddy> result = (ResultBody<Favorite_Buddy>) resultBody;
                                                }
                                            })
                                            .execute();
                                }


                            }
                        })
                        .execute();
            }
        });



        imageView = findViewById(R.id.imageView);
        new HttpAsyncTask("GET", "profiles/buddy/" + buddy.getBuddy_id(), null, null, new TypeToken<ResultBody<Profile>>() {
        }.getType(),
                new MyCallBack() {
                    @Override
                    public void doTask(Object resultBody) {
                        ResultBody<Profile> result = (ResultBody<Profile>) resultBody;

                        if(image_path_list.size() == 0){

                        }
                        else if (result.getDatas().size() == 0) {
                            Glide.with(context).load("http://ec2-18-222-114-158.us-east-2.compute.amazonaws.com:3000/" + image_path_list.get(0)).into(imageView);
                        } else
                            Glide.with(context).load("http://ec2-18-222-114-158.us-east-2.compute.amazonaws.com:3000/" + result.getDatas().get(0).getImage_path()).into(imageView);

                    }
                }
        ).execute();

        final TextView likeNum = findViewById(R.id.likeNum);
        new HttpAsyncTask("GET", "favorite_buddies/buddy/" + buddy.getBuddy_id(), null, null, new TypeToken<ResultBody<Profile>>() {
        }.getType(),
                new MyCallBack() {
                    @Override
                    public void doTask(Object resultBody) {

                        ResultBody<Profile> result = (ResultBody<Profile>) resultBody;
                        likeNum.setText(Integer.toString(result.getDatas().size()));
                        Log.d("photographer fabu",likeNum.getText().toString());
                    }
                }
        ).execute();
        TextView area = findViewById(R.id.area);
        area.setText(buddy.getLocation_name());
        this.rvSample = (RecyclerView) findViewById(R.id.rvSample);
        this.rvSample.setLayoutManager(new GridLayoutManager(this, 3));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, image_path_list, me);
        this.rvSample.setAdapter(adapter);
    }

    public void noticeClick(View v) {
        Intent i = new Intent(Photographer_info.this, Notice.class);
        startActivity(i);
    }

    public void introduceClick(View v) {
        Intent i = new Intent(Photographer_info.this, IntroducePrice.class);
        startActivity(i);
    }

    public void scheduleClick(View v) {
        Intent i = new Intent(Photographer_info.this, Schedule.class);
        startActivity(i);
    }

    public void reviewClick(View v) {
        Intent i = new Intent(Photographer_info.this, ReviewActivity.class);
        i.putExtra("Buddy", buddy);
        i.putExtra("Customer", me);
        startActivity(i);

    }
}