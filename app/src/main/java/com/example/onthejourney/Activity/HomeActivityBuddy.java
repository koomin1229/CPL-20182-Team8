package com.example.onthejourney.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.onthejourney.Adapter.Item_review_adapter;
import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.Data.FeedItem;
import com.example.onthejourney.Data.Post_scripts;
import com.example.onthejourney.Fragment.LikeFragment;
import com.example.onthejourney.Fragment.MapFragment;
import com.example.onthejourney.Fragment.MyPageFragment;
import com.example.onthejourney.Fragment.ScheduleFragment;
import com.example.onthejourney.Fragment.SearchFragment;
import com.example.onthejourney.Fragment.requestAchat;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.gson.reflect.TypeToken;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;


public class HomeActivityBuddy extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private int flag = 0;
    Buddy buddy = null;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //buddy = new Buddy(32.7, 160.5, "hiroo~");
        buddy = getIntent().getParcelableExtra("Buddy");



        fragmentManager = getSupportFragmentManager();

        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        bottomNavigationView.setTextVisibility(false);
        bottomNavigationView.enableItemShiftingMode(true);
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableShiftingMode(true);

        // HomeActivity의 Default Fragment 설정
        if (savedInstanceState == null) {
            fragment = new ScheduleFragment();
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.main_container, new ScheduleFragment()).commit();
        }

        // BottomNavigationView의 item을 클릭했을 때 Fragment 전환
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                flag = 0;

                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
                switch (item.getItemId()) {
                    case R.id.search:
                        if (!(currentFragment instanceof ScheduleFragment)) {
                            fragment = new ScheduleFragment();
                            Bundle args = new Bundle();
                            args.putParcelable("Buddy", buddy);
                            fragment.setArguments(args);
                        }
                        break;
                    case R.id.map:
                        if (!(currentFragment instanceof requestAchat)) {
                            fragment = new requestAchat().newInstance();
                            Bundle arg = new Bundle();
                            arg.putParcelable("Buddy", buddy);
                            fragment.setArguments(arg);
                        }
                        break;
                    case R.id.like:

                        final ArrayList<String> image_path_list = new ArrayList<>();

                        new HttpAsyncTask("GET", "feed_items/buddy/" + buddy.getBuddy_id(), null, null, new TypeToken<ResultBody<FeedItem>>() {
                        }.getType(),
                                new MyCallBack() {
                                    @Override
                                    public void doTask(Object resultBody) {
                                        ResultBody<FeedItem> result = (ResultBody<FeedItem>) resultBody;

                                        for (int i = 0; i < result.getDatas().size(); i++) {
                                            image_path_list.add(result.getDatas().get(i).getImage_path());
                                        }
                                        Intent intent = new Intent(HomeActivityBuddy.this, Photographer_info.class);
                                        intent.putExtra("Buddy", buddy);
                                        intent.putStringArrayListExtra("List", image_path_list);
                                        startActivity(intent);
                                        flag = 1;
                                    }
                                }).execute();

                        break;
                    case R.id.mypage:
                        if (!(currentFragment instanceof MyPageFragment)) {
                            fragment = new MyPageFragment();
                            Bundle args1 = new Bundle();
                            args1.putParcelable("Buddy", buddy);
                            fragment.setArguments(args1);
                        }
                        break;
                }
                if (flag == 1) {
                    return true;
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment, "TAG").commit();
                    return true;
                }
            }
        });
    }
}