package com.example.a95795.thegreenplant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 这是一个viewPager适配器
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    /**
     *  FragmentPagerAdapter:FragmentPagerAdapter适合使用在固定的数量较少的场景,最多可以保留3个
     *  FragmentStatePagerAdapter:
     * @param fm
     */
    private List<Fragment> fragments;
//234
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public MyPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.fragments = list;
    }


    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

}
