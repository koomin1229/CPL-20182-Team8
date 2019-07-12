package com.example.onthejourney.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.onthejourney.Data.CheckList;
import com.example.onthejourney.Data.MyDate;
import com.example.onthejourney.R;

import java.sql.Date;
import java.util.ArrayList;

public class CheckListDialog extends Dialog implements View.OnClickListener {
    private static final int LAYOUT = R.layout.dialog_checklist;

    private MyDialogListener dialogListener;

    public interface MyDialogListener {
        public void onPositiveClicked(CheckList checkList);
        public void onNegativeClicked();
    }public void setDialogListener(MyDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }


    private Context context;

    private DatePicker sdp, edp;
    private TextInputEditText suggest_priceEt;
    private TextInputEditText require_listEt;
    private TextInputEditText locationEt;
    private TextInputEditText people_numberEt;

    private TextView cancelTv;
    private TextView searchTv;


    public CheckListDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        sdp = (DatePicker)findViewById(R.id.startTime);
        edp = (DatePicker)findViewById(R.id.endTime);
        suggest_priceEt = (TextInputEditText) findViewById(R.id.suggest_priceEt);
        locationEt = (TextInputEditText) findViewById(R.id.locationEt);
        people_numberEt = (TextInputEditText) findViewById(R.id.people_numberEt);
        require_listEt = (TextInputEditText) findViewById(R.id.require_listEt);

        cancelTv = (TextView) findViewById(R.id.findPwDialogCancelTv);
        searchTv = (TextView) findViewById(R.id.findPwDialogFindTv);

        cancelTv.setOnClickListener(this);
        searchTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.findPwDialogCancelTv:
                cancel();
                break;
            case R.id.findPwDialogFindTv:
                CheckList checkList = new CheckList();

                String syear = Integer.toString(sdp.getYear());
                String smonth = Integer.toString(sdp.getMonth()+1);
                String sday = Integer.toString(sdp.getDayOfMonth());

                checkList.setStart_year(syear);
                checkList.setStart_month(smonth);
                checkList.setStart_day(sday);

                String eyear = Integer.toString(edp.getYear());
                String emonth = Integer.toString(edp.getMonth()+1);
                String eday = Integer.toString(edp.getDayOfMonth());

                checkList.setEnd_year(eyear);
                checkList.setEnd_month(emonth);
                checkList.setEnd_day(eday);
                checkList.setSuggested_price(Integer.parseInt(suggest_priceEt.getText().toString()));
                checkList.setLocation(locationEt.getText().toString());
                Log.d("location",checkList.getLocation());
                checkList.setPeople_number(Integer.parseInt(people_numberEt.getText().toString()));

                ArrayList<String> arrayList = new ArrayList<>();
                String require = require_listEt.getText().toString();
                String[] words = require.split(",");
                for(String wo : words){
                    arrayList.add(wo);
                    Log.d("wo",wo);
                }

                checkList.setRequirement_list(arrayList);
                dialogListener.onPositiveClicked(checkList);
                dismiss();
                break;
        }
    }
}