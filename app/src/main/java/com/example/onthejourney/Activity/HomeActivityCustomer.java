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

import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.Fragment.LikeFragment;
import com.example.onthejourney.Fragment.MapFragment;
import com.example.onthejourney.Fragment.MyPageFragment;
import com.example.onthejourney.Fragment.SearchFragment;
import com.example.onthejourney.Fragment.requestAchat;
import com.example.onthejourney.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class HomeActivityCustomer extends AppCompatActivity {

    Fragment fragment = null;
    private FragmentManager fragmentManager;
    private int flag = 0;
    Customer Customer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       Customer = (Customer) getIntent().getParcelableExtra("Customer");


        fragmentManager = getSupportFragmentManager();

        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        bottomNavigationView.setTextVisibility(false);
        bottomNavigationView.enableItemShiftingMode(true);
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableShiftingMode(true);

        // HomeActivity의 Default Fragment 설정
        if (savedInstanceState == null) {
            fragment = new SearchFragment();
            Bundle args = new Bundle();
            args.putParcelable("Customer", Customer);
            fragment.setArguments(args);
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.main_container, fragment).commit();
        }

        // BottomNavigationView의 item을 클릭했을 때 Fragment 전환
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                flag = 0;

                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
                switch (item.getItemId()) {
                    case R.id.search:
                        if(!(currentFragment instanceof SearchFragment)) {
                            fragment = new SearchFragment();
                            Bundle args = new Bundle();
                            args.putParcelable("Customer", Customer);
                            fragment.setArguments(args);
                        }
                        break;
                    case R.id.map:
                        Intent intent = new Intent(HomeActivityCustomer.this, MapsActivity.class);
                        intent.putExtra("Customer", Customer);
                        startActivity(intent);
                        flag = 1;
                        fragment = new MapFragment();
                        break;
                    case R.id.like:
                        if(!(currentFragment instanceof  LikeFragment)) {
                            fragment = new LikeFragment().newInstance(Customer);
                            Bundle arg = new Bundle();
                            arg.putParcelable("Customer", Customer);
                            fragment.setArguments(arg);
                        }
                        break;
                    case R.id.mypage:
                        if(!(currentFragment instanceof  MyPageFragment)) {
                            fragment = new MyPageFragment().newInstance(Customer);
                            Bundle args1 = new Bundle();
                            args1.putParcelable("Customer", Customer);
                            Log.d("Customer", Customer.toString());
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