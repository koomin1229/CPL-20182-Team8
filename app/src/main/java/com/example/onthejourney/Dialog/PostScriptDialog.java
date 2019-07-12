package com.example.onthejourney.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.TextView;

import com.example.onthejourney.Data.Post_scripts;
import com.example.onthejourney.R;

public class PostScriptDialog extends  Dialog implements  View.OnClickListener {
    private static final int LAYOUT = R.layout.dialog_post_script;

    private MyReviewDialogListener dialogListener;

    public interface MyReviewDialogListener {
        public void onPositiveClicked(Post_scripts post_script);
        public void onNegativeClicked();
    }public void setDialogListener(MyReviewDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    private Context context;

    private TextInputEditText comment;
    private TextInputEditText grade;

    private TextView cancelTv;
    private TextView searchTv;

    public PostScriptDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        comment = (TextInputEditText) findViewById(R.id.comment);
        grade = (TextInputEditText) findViewById(R.id.grade);

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
                Post_scripts post_script = new Post_scripts();

                post_script.setComments(comment.getText().toString());

                int val = Integer.parseInt( grade.getText().toString()  );
                post_script.setGrade(val);

                dialogListener.onPositiveClicked(post_script);
                dismiss();
                break;
        }
    }

}