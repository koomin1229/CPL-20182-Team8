package com.example.onthejourney.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onthejourney.Adapter.FragmentPagerAdapterMy;
import com.example.onthejourney.Adapter.RequestFragmentPagerAdapterMy;
import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeFragment extends Fragment {

    private Customer customer;
    public TabLayout tabLayout;
    public ViewPager viewPager;

    public LikeFragment() {}

    public static LikeFragment newInstance(Customer customer) {
        LikeFragment fragment = new LikeFragment();
        Bundle args = new Bundle();
        args.putParcelable("Customer",customer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        return inflater.inflate(R.layout.fragment_like, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customer = (Customer)getArguments().get("Customer");
        MyAdapter adapter = new MyAdapter(
                getChildFragmentManager()
        );


        if(viewPager == null) {
            viewPager = (ViewPager) view.findViewById(R.id.viewpager_likefragment);

            viewPager.setAdapter(adapter);
            tabLayout = (TabLayout) view.findViewById(R.id.tabs_likefragment);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    return LikeBuddyFragment.newInstance(customer);
                case 1:
                    return LikePhotoFragment.newInstance(customer);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    String str1 = "Buddy";
                    return str1;
                case 1:
                    String str = "Photo";

                    return str;
            }
            return null;
        }
    }



}