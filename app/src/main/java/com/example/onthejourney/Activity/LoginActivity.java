package com.example.onthejourney.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.reflect.TypeToken;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CUSTOMER_SIGN_IN = 50001;
    public static final int BUDDY_SIGN_IN = 50002;
    public static final int SIGN_UP = 50003;


    private Buddy buddy = new Buddy();
    GoogleSignInClient mGoogleSignInClient = null;
    GoogleSignInAccount mGoogleAccount;
    Button mCustomerSignInButton = null;
    Button mBuddySignInButton = null;
    Button mSignUPButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 사용자의 ID, 전자 메일 주소 및 기본
        // 프로필 을 요청하도록 로그인을 구성 합니다. ID 및 기본 프로필은 DEFAULT_SIGN_IN에 포함됩니다.
        // GoogleSignInOptions gso = 새 GoogleSignInOptions . 작성자 ( GoogleSignInOptions . DEFAULT_SIGN_IN ) . requestEmail () . 빌드 ();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // gso로 지정된 옵션을 사용하여 GoogleSignInClient를 빌드합니다.
        mGoogleSignInClient = GoogleSignIn. getClient ( this , gso );

        // 기존 Google 로그인 계정을 확인합니다 (사용자가 이미 로그인 한 경우).
        // GoogleSignInAccount는 null이 아닙니다.
        // GoogleSignInAccount 계정 = GoogleSignIn . getLastSignedInAccount ( this ); updateUI ( 계정 );



        mCustomerSignInButton = findViewById(R.id.sign_in_button_customer);
        mCustomerSignInButton.setOnClickListener(this);

        mBuddySignInButton = findViewById(R.id.sign_in_button_buddy);
        mBuddySignInButton.setOnClickListener(this);

        mSignUPButton = findViewById(R.id.sign_up_button);
        mSignUPButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(GoogleSignIn.getLastSignedInAccount(this) != null){
            signOut();
        }

        switch (v.getId()){
            case R.id.sign_in_button_customer:
                customerSignIn();
                break;
            case R.id.sign_in_button_buddy:
                buddySignIn();
                break;
            case R.id.sign_up_button:
                signUp();
                break;
        }
    }

    public void customerSignIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, CUSTOMER_SIGN_IN);
    }

    public void buddySignIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, BUDDY_SIGN_IN);
    }

    public void signUp(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_UP);
    }

    public void signOut(){
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Toast.makeText(LoginActivity.this, "signed out", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == BUDDY_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("account_id",account.getId());

                new HttpAsyncTask("GET", "buddies/" + account.getId(), null, null, new TypeToken<ResultBody<Buddy>>() {
                }.getType(), new MyCallBack() {
                    @Override
                    public void doTask(Object resultBody) {
                        ResultBody<Buddy> result = (ResultBody<Buddy>) resultBody;

                        /*  회원 가입이 되어 있을 때  */
                        if(result.getDatas().size() == 1){
                            Intent intent = new Intent(LoginActivity.this, HomeActivityBuddy.class);
                            intent.putExtra("Buddy", result.getDatas().get(0));
                            startActivity(intent);
                        }
                        /*  회원 가입이 되어 있지 않을 때  */
                        else{
                            Toast.makeText(getApplicationContext(), "회원가입 안되있어용!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute();
            }catch (ApiException e){
                e.printStackTrace();
            }
        }else if(requestCode == CUSTOMER_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("account_id",account.getId());


                new HttpAsyncTask("GET", "customers/" + account.getId(), null, null, new TypeToken<ResultBody<Customer>>() {
                }.getType(), new MyCallBack() {
                    @Override
                    public void doTask(Object resultBody) {
                        ResultBody<Customer> result = (ResultBody<Customer>) resultBody;

                        /*  회원 가입이 되어 있을 때  */
                        if(result.getDatas().size() == 1){
                            Intent intent = new Intent(LoginActivity.this, HomeActivityCustomer.class);
                            intent.putExtra("Customer", result.getDatas().get(0));
                            startActivity(intent);
                        }
                        /*  회원 가입이 되어 있지 않을 때  */
                        else{
                            Toast.makeText(getApplicationContext(), "회원가입 안되있어용!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute();
            }catch (ApiException e){
                e.printStackTrace();
            }
        }else if(requestCode == SIGN_UP){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                               GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("account_id", account.getId());

                new HttpAsyncTask("POST", "customers",
                        new Customer(account.getFamilyName() + account.getGivenName(), account.getId(), account.getFamilyName() + account.getGivenName()).getJsonObject(),
                        null, new TypeToken<ResultBody<Customer>>() {
                }.getType(),
                        new MyCallBack() {
                            @Override
                            public void doTask(Object resultBody) {
                                System.out.println("make customer");
                            }
                        }).execute();

                buddy.setBuddy_id(account.getId());
                buddy.setmTitle(account.getFamilyName()+ account.getGivenName());

                Intent intent = new Intent(this, SignUpActivity.class);
                startActivityForResult(intent, 1);



            }catch (ApiException e){
                e.printStackTrace();
            }
        }
        else if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                assert data != null;
                buddy.setLocation_name(data.getStringExtra("str"));
                buddy.setmPosition((LatLng)data.getParcelableExtra("Latlng"));

                Log.d("Latlng",buddy.getmPosition().toString());
                Log.d("BuddyInLogin",buddy.toString());
                new HttpAsyncTask("POST", "buddies",
                        buddy.getJsonObject(),
                        null, new TypeToken<ResultBody<Customer>>() {
                }.getType(),
                        new MyCallBack() {
                            @Override
                            public void doTask(Object resultBody) {
                                System.out.println("make buddy");
                            }
                        }).execute();

            }
        }
    }



    public void updateUI(GoogleSignInAccount account){
        Toast.makeText(getApplicationContext(), account.getFamilyName() + account.getGivenName(), Toast.LENGTH_LONG).show();
    }



}
