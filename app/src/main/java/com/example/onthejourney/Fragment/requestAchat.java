package com.example.onthejourney.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onthejourney.Adapter.RequestFragmentPagerAdapterMy;
import com.example.onthejourney.Data.Buddy;
import com.example.onthejourney.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class requestAchat extends Fragment {

    static Buddy buddy = null;
    private ViewPager viewPager;
    public static requestAchat newInstance(){
        Bundle args = new Bundle();

        requestAchat fragment = new requestAchat();
        args.putParcelable("Buddy",buddy);
        fragment.setArguments(args);
        return fragment;
    }

    public requestAchat(){}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_achat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buddy = (Buddy)getArguments().get("Buddy");
        Log.d("buddy",buddy.toString());
        RequestFragmentPagerAdapterMy adapter = new RequestFragmentPagerAdapterMy(
                getChildFragmentManager(), buddy
        );

        if(viewPager == null) {
            viewPager = (ViewPager) getActivity().findViewById(R.id.reqViewPager);
            viewPager.setOffscreenPageLimit(2);
            viewPager.setAdapter(adapter);

            TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.reqTabs);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
