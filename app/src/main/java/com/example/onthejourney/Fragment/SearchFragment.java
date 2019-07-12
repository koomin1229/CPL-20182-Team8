package com.example.onthejourney.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.onthejourney.Adapter.HashTagFeedItemAdapter;
import com.example.onthejourney.Adapter.RecyclerViewAdapter;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.Data.FeedItem;
import com.example.onthejourney.Module.HttpAsyncTask;
import com.example.onthejourney.Module.MyCallBack;
import com.example.onthejourney.Module.ResultBody;
import com.example.onthejourney.R;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import javax.xml.transform.Result;


public class SearchFragment extends Fragment {
    private EditText tv;
    private Button button;
    private Customer customer = null;
    private RecyclerView rv;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        customer = (Customer)getArguments().get("Customer");
        Log.d("search_fragment", customer.toString());

        tv = (EditText) view.findViewById(R.id.editTextInsearch);

        rv = (RecyclerView) getActivity().findViewById(R.id.search_rv);
        rv.setLayoutManager(new GridLayoutManager(getContext(),3));
        rv.setHasFixedSize(true);
        new HttpAsyncTask("GET", "feed_items", null, null,
                new TypeToken<ResultBody<FeedItem>>() {
                }.getType()
                , new MyCallBack() {
            @Override
            public void doTask(Object resultBody) {
                ResultBody<FeedItem> result = (ResultBody<FeedItem>) resultBody;

                HashTagFeedItemAdapter adapater = new HashTagFeedItemAdapter(result.getDatas(), null, getContext(), customer);
                rv.setAdapter(adapater);
            }
        }).execute();

        //HashTagFeedItemAdapter adapter = new HashTagFeedItemAdapter(null, null, getContext(), customer);
        //rv.setAdapter(adapter);

        button = (Button)view.findViewById(R.id.buttonInsearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tv.getText().toString();
                final TextView myText = v.findViewById(R.id.textInsearch);

                Log.d("search_fragment", text);
                new HttpAsyncTask("GET", "feed_items/hashtag/" + text, null, null
                        , new TypeToken<ResultBody<FeedItem>>() {
                }.getType(),
                        new MyCallBack() {
                            @Override
                            public void doTask(Object resultBody) {
                                ResultBody<FeedItem> result = (ResultBody<FeedItem>) resultBody;

                                Log.d("search_fragment", result.getDatas().get(0).getImage_path());
                                Log.d("search_fragment", ":" + result.getDatas().size());

                                if(result.getDatas().size() != 0) {
                                    HashTagFeedItemAdapter adapter = new HashTagFeedItemAdapter(result.getDatas(), null, getContext(), customer);
                                    rv.setAdapter(adapter);
                                }
                            }
                        }
                ).execute();
            }
        });

        tv.setHint("search");
    }

    public SearchFragment() {}

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }
}