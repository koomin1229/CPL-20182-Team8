package com.example.onthejourney.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onthejourney.Adapter.RecyclerViewAdapter;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.Module.RequestHttpURLConnection;
import com.example.onthejourney.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikePhotoFragment extends android.support.v4.app.Fragment {


    public static Customer customer = null;
    private ArrayList<String> favorite_image;

    public LikePhotoFragment(){}

    public static LikePhotoFragment newInstance(Customer customer) {
        // Required empty public constructor
        Bundle args = new Bundle();
        args.putParcelable("Customer",customer);
        LikePhotoFragment fragment = new LikePhotoFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_likephoto, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        customer = (Customer)getArguments().get("Customer");


        NetworkTask networkTask = new NetworkTask("favorite_feeds/customer/", "feed_id", new NetworkTask.Listener() {
            @Override
            public void onFinished(final ArrayList<String> s) {

                RecyclerView rv = (RecyclerView) getActivity().findViewById(R.id.rvLikePhoto);
                rv.setLayoutManager(new GridLayoutManager(getContext(),3));

                RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), s,customer);
                rv.setAdapter(adapter);

            }
        });
        networkTask.execute();


    }

    public static class NetworkTask extends AsyncTask<Void, Void, ArrayList<String>> {

        private String option1, option2;
        private Listener listener;

        public interface Listener {
            void onFinished(ArrayList<String> s);
        }

        public NetworkTask(String option1, String option2, Listener listener) {
            this.option1 = option1;
            this.option2 = option2;
            this.listener = listener;
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> result = new ArrayList<String>();
            ArrayList<String> feedId = new ArrayList<>();
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.getJsonText(option1, option2, customer.getCustomer_id());


            return result;
        }

        protected void onPostExecute(ArrayList<String> s) {
            listener.onFinished(s);

        }
    }
}
