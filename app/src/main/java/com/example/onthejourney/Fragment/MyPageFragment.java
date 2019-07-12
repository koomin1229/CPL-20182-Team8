package com.example.onthejourney.Fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.onthejourney.Activity.BookingActivity;
import com.example.onthejourney.Activity.HomeActivityBuddy;
import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.Data.CheckList;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.gson.reflect.TypeToken;


public class MyPageFragment extends Fragment {

    private Buddy buddy;
    private Customer customer;

    public MyPageFragment() {
    }

    public static MyPageFragment newInstance(Customer customer) {
        MyPageFragment fragment = new MyPageFragment();
        Bundle args = new Bundle();
        args.putParcelable("Customer", customer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        customer = (Customer) getArguments().get("Customer");
        buddy = (Buddy)getArguments().get("Buddy");

        Button button5 = view.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button button4 = view.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookingActivity.class);
                intent.putExtra("Customer", customer);
                startActivity(intent);
            }
        });
        Button button3 = view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button button2 = view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivityBuddy.class);

                Buddy buddy = new Buddy(37.5,160.0,"hiroo~");
                intent.putExtra("Buddy",buddy);
                startActivity(intent);
                getActivity().finish();

//                if (buddy == null)
//                    new HttpAsyncTask("GET", "buddies/" + customer.getCustomer_id(), null, null, new TypeToken<ResultBody<Buddy>>() {
//                    }.getType(),
//                            new MyCallBack() {
//                                @Override
//                                public void doTask(Object resultBody) {
//                                    ResultBody<Buddy> result = (ResultBody<Buddy>) resultBody;
//                                    buddy = result.getDatas().get(0);
//
//                                    Intent intent = new Intent(getActivity(), HomeActivityBuddy.class);
//                                    intent.putExtra("Buddy", buddy);
//                                    startActivity(intent);
//                                    getActivity().finish();
//                                }
//
//                            }
//                    ).execute();
//                else
//                    new HttpAsyncTask("GET", "customers/" + buddy.getBuddy_id(), null, null, new TypeToken<ResultBody<Customer>>() {
//                    }.getType(),
//                            new MyCallBack() {
//                                @Override
//                                public void doTask(Object resultBody) {
//                                    ResultBody<Customer> result = (ResultBody<Customer>) resultBody;
//                                    customer = result.getDatas().get(0);
//
//                                    Intent intent = new Intent(getActivity(), HomeActivityBuddy.class);
//                                    intent.putExtra("Customer", customer);
//                                    startActivity(intent);
//                                    getActivity().finish();
//                                }
//
//                            }
//                    ).execute();
            }

        });
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}