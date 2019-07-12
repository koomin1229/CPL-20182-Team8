package com.example.onthejourney.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.onthejourney.Activity.BookingActivity;
import com.example.onthejourney.Activity.ChatActivity;
import com.example.onthejourney.Adapter.RequestAdapter;
import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.Data.ChatDTO;
import com.example.onthejourney.Data.CheckList;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private ListView chat_list;
    private Buddy buddy;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public static ChatFragment newInstance(Buddy buddy){
        Bundle args = new Bundle();
        args.putParcelable("Buddy",buddy);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buddy = (Buddy)getArguments().get("Buddy");
        Log.d("buddy in chatFragment",buddy.toString());
        chat_list = view.findViewById(R.id.chat_list);

        showChatList();
    }
    private void showChatList() {
        // 리스트 어댑터 생성 및 세팅
        final ArrayList<CheckList> checkLists = new ArrayList<>();
        new HttpAsyncTask("GET", "checklists/buddy/" + buddy.getBuddy_id(), null, null, new TypeToken<ResultBody<CheckList>>() {
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


                        RequestAdapter adapter = new RequestAdapter(checkLists,buddy);
                        chat_list.setAdapter(adapter);
                        chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent intent = new Intent(getActivity(), ChatActivity.class);
                                intent.putExtra("notMe",((CheckList)chat_list.getItemAtPosition(position)).getCustomer_name());
                                intent.putExtra("Buddy", buddy);
                                Log.d("Buddy", buddy.toString());
                                intent.putExtra("chatName", ((CheckList)chat_list.getItemAtPosition(position)).getKey());
                                Log.d("chatName", chat_list.getItemAtPosition(position).toString());
                                intent.putExtra("userName", buddy.getBuddy_id());
                                startActivity(intent);
                            }
                        });
                    }
                }).execute();


    }
}
