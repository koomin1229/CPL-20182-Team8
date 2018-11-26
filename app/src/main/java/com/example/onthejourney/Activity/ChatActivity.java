package com.example.onthejourney.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.onthejourney.Adapter.ChatAdapter;
import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.Data.ChatDTO;
import com.example.onthejourney.Data.CheckList;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ChatActivity extends AppCompatActivity {

    private TextView chatName;
    private String NOT_ME;
    private String CHAT_NAME;
    private String USER_NAME;
    private CheckList checkList;
    private ListView chat_view;
    private EditText chat_edit;
    private Button chat_send;
    private ChatAdapter adapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private Buddy buddy;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);



        // 위젯 ID 참조
        chatName = findViewById(R.id.chat_UserName);
        chat_view = (ListView) findViewById(R.id.chat_view);
        chat_edit = (EditText) findViewById(R.id.chat_edit);
        chat_send = (Button) findViewById(R.id.chat_sent);


        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        Intent intent = getIntent();
        NOT_ME = intent.getStringExtra("notMe");
        CHAT_NAME = intent.getStringExtra("chatName");
        USER_NAME = intent.getStringExtra("userName");
        buddy = intent.getParcelableExtra("Buddy");
        customer = intent.getParcelableExtra("Customer");




        chatName.setText(NOT_ME);
        // 채팅 방 입장

        if(buddy == null)
            adapter = new ChatAdapter(this, R.layout.listitem_chat, customer);

        else
            adapter = new ChatAdapter(this, R.layout.listitem_chat, buddy);

        chat_view.setAdapter(adapter);

        openChat(CHAT_NAME);

        // 메시지 전송 버튼에 대한 클릭 리스너 지정
        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chat_edit.getText().toString().equals(""))
                    return;

                ChatDTO chat = new ChatDTO(USER_NAME, chat_edit.getText().toString()); //ChatDTO를 이용하여 데이터를 묶는다.
                chat.setTime(System.currentTimeMillis());
                databaseReference.child("CHAT").child(CHAT_NAME).push().setValue(chat); // 데이터 푸쉬
                chat_edit.setText(""); //입력창 초기화

            }
        });
    }

    private void addMessage(DataSnapshot dataSnapshot) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        chatDTO.setFirebaseKey(dataSnapshot.getKey());
        adapter.add(chatDTO);

        chat_view.smoothScrollToPosition(adapter.getCount());
        Log.d("adapteCount",Integer.toString(adapter.getCount()));
    }

    private void removeMessage(DataSnapshot dataSnapshot) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.remove(chatDTO);
    }

    private void openChat(String chatName) {
        // 리스트 어댑터 생성 및 세팅

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("CHAT").child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addMessage(dataSnapshot);
                Log.e("LOG", "s:"+s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeMessage(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}