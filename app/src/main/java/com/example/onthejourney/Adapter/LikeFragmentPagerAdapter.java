package com.example.onthejourney.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.onthejourney.Data.Customer;
import com.example.onthejourney.Fragment.LikeBuddyFragment;
import com.example.onthejourney.Fragment.LikePhotoFragment;

public class LikeFragmentPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {
    private Customer customer;
    public LikeFragmentPagerAdapter(FragmentManager fm, Customer customer) {
        super(fm);
        this.customer = customer;
    }

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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "사진작가";
            case 1:
                return "사진";
            default:
                return null;
        }
    }
}