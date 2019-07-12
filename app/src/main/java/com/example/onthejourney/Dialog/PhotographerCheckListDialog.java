package com.example.onthejourney.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.onthejourney.Data.CheckList;
import com.example.onthejourney.R;

public class PhotographerCheckListDialog extends Dialog implements View.OnClickListener{
    private static final int LAYOUT = R.layout.dialog_checklist_photographer;

    private CheckList checkList;
    private MyDialogListener_photo  dialogListener;

    public interface MyDialogListener_photo {
        public void onPositiveClicked();
        public void onNegativeClicked();
    }

    public void setDialogListener(MyDialogListener_photo  dialogListener, CheckList checkList){
        this.dialogListener = dialogListener;
        this.checkList = checkList;
    }


    private Context context;

    private TextView buddyId;
    private TextView start;
    private TextView end;
    private TextView location;
    private TextView number;
    private TextView suggestPrice;
    private TextView suggest;
    private TextView cancelTv;
    private TextView searchTv;


    public PhotographerCheckListDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        buddyId = findViewById(R.id.checklist_photographer_buddyId);
        buddyId.setText(checkList.getBuddy_id());
        start = findViewById(R.id.checklist_photographer_start);
        start.setText(checkList.getStart_year() + "년 " + checkList.getStart_month() + "월 " + checkList.getStart_day() + "일 ");
        end = findViewById(R.id.checklist_photographer_end);
        end.setText(checkList.getEnd_year() + "년 " + checkList.getEnd_month() + "월 " + checkList.getEnd_day() + "일 ");
        location = findViewById(R.id.checklist_photographer_location);
        location.setText(checkList.getLocation());
        number = findViewById(R.id.checklist_photographer_number);
        number.setText(Integer.toString(checkList.getPeople_number()));
        suggestPrice = findViewById(R.id.checklist_photographer_suggestPrice);
        suggestPrice.setText(Integer.toString(checkList.getSuggested_price()));
        suggest = findViewById(R.id.checklist_photographer_suggest1);
        suggest.setText(checkList.getRequirement_list().toString());
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

                dialogListener.onPositiveClicked();
                dismiss();
                break;
        }
    }
}