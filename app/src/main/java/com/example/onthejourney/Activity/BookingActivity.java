package com.example.onthejourney.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.onthejourney.Adapter.RequestAdapter;
import com.example.onthejourney.Data.CheckList;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity {
    private ListView chat_list;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private Customer customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

         customer = getIntent().getParcelableExtra("Customer");
        Log.d("cusinBookingActivity", customer.toString());



        chat_list = findViewById(R.id.chat_listInbook);

        showChatList();

    }


    private void showChatList() {
        // 리스트 어댑터 생성 및 세팅

        final ArrayList<CheckList> checkLists = new ArrayList<>();
        new HttpAsyncTask("GET", "checklists/customer/" + customer.getCustomer_id(), null, null, new TypeToken<ResultBody<CheckList>>() {
        }.getType(),
                new MyCallBack() {
                    @Override
                    public void doTask(Object resultBody) {
                        ResultBody<CheckList> result = (ResultBody<CheckList>) resultBody;

                        for (int i = 0; i < result.getDatas().size(); i++) {
                            if (result.getDatas().get(i).getState().equals("진행중")) {
                                checkLists.add((CheckList) result.getDatas().get(i));
                            }
                        }


                        RequestAdapter adapter = new RequestAdapter(checkLists,customer);
                        chat_list.setAdapter(adapter);
                        chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent intent = new Intent(BookingActivity.this, ChatActivity.class);
                                intent.putExtra("notMe",((CheckList)chat_list.getItemAtPosition(position)).getBuddy_name());
                                intent.putExtra("Customer", customer);
                                Log.d("Customer", customer.toString());
                                intent.putExtra("chatName", ((CheckList)chat_list.getItemAtPosition(position)).getKey());
                                Log.d("chatName", chat_list.getItemAtPosition(position).toString());
                                intent.putExtra("userName", customer.getCustomer_id());
                                startActivity(intent);
                            }
                        });
                    }
                }).execute();




    }

}






