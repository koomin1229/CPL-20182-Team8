package com.example.onthejourney.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.onthejourney.Adapter.RequestAdapter;
import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.Data.CheckList;
import com.example.onthejourney.Dialog.PhotographerCheckListDialog;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {

    private Buddy buddy;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public static RequestFragment newInstance(Buddy buddy) {
        Bundle args = new Bundle();
        args.putParcelable("Buddy", buddy);
        RequestFragment fragment = new RequestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        buddy = (Buddy) getArguments().getParcelable("Buddy");
        final ArrayList<CheckList> checkLists = new ArrayList<>();
        new HttpAsyncTask("GET", "checklists/buddy/" + buddy.getBuddy_id(), null, null, new TypeToken<ResultBody<CheckList>>() {
        }.getType(),
                new MyCallBack() {
                    @Override
                    public void doTask(Object resultBody) {
                        ResultBody<CheckList> result = (ResultBody<CheckList>) resultBody;

                        for (int i = 0; i < result.getDatas().size(); i++) {
                            if (result.getDatas().get(i).getState().equals("예약중"))
                                checkLists.add((CheckList) result.getDatas().get(i));
                        }

                        final ListView listView = view.findViewById(R.id.listView_request);
                        final RequestAdapter adapter = new RequestAdapter(checkLists,buddy);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                                PhotographerCheckListDialog dialog = new PhotographerCheckListDialog(view.getContext());
                                dialog.setDialogListener(new PhotographerCheckListDialog.MyDialogListener_photo() {
                                    @Override
                                    public void onPositiveClicked() {
                                        CheckList checkList = checkLists.get(position);
                                        checkLists.remove(position);
                                        Log.d("checklist", checkList.toString());
                                        checkList.setState("진행중");
                                        String key = databaseReference.child("CHAT").push().getKey();
                                        checkList.setKey(key);
                                        JSONObject jsonObject = checkList.getJsonObject();
                                        new HttpAsyncTask("PUT", "checkLists/" + checkList.get_id(), jsonObject, null, new TypeToken<ResultBody<CheckList>>() {
                                        }.getType(),
                                                new MyCallBack() {
                                                    @Override
                                                    public void doTask(Object resultBody) {
                                                        ResultBody<CheckList> result = (ResultBody<CheckList>) resultBody;
                                                    }
                                                })
                                                .execute();


                                        listView.setAdapter(adapter);
                                        Toast.makeText(view.getContext(), "작업을 수락하였습니다.", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNegativeClicked() {
                                        Toast.makeText(view.getContext(), "취소하였습니다.", Toast.LENGTH_SHORT).show();
                                        Log.d("MyDialogListener", "onNegativeClicked");
                                    }
                                }, checkLists.get(position));
                                dialog.show();
                            }
                        });
                    }
                }).execute();
    }
}
