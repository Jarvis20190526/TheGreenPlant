package com.example.a95795.thegreenplant.HomeFragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.transition.Fade;
import android.support.transition.Slide;
import android.support.v4.app.Fragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a95795.thegreenplant.MainActivity;
import com.example.a95795.thegreenplant.R;
import com.example.a95795.thegreenplant.adapter.MyPagerAdapter;
import com.example.a95795.thegreenplant.custom.StatusBarCompat;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;


public class HomeFragment extends SupportFragment {

    private BottomBar mBottomBar;
    private EnvironmentFragment environmentFragment;
    private EquipmentFragment equipmentFragment;
    private PersonageFragment personageFragment;
    private ViewPager viewPager;
    private CoordinatorLayout mCoordinator;
    private List<Fragment> fragmentList;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = (TextView) view.findViewById(R.id.textView);
        //沉浸式状态栏
        StatusBarCompat.compat(HomeFragment.this.getActivity(), Color.parseColor("#FF16A295"));
        mCoordinator = (CoordinatorLayout) view.findViewById(R.id.mCoordinator);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        initViewPager();
        mBottomBar = BottomBar.attachShy(mCoordinator, viewPager, savedInstanceState);
        mBottomBar.setItems(R.menu.navigation);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                //单击事件 menuItemId 是 R.menu.bottombar_menu 中 item 的 id
                switch (menuItemId) {
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_dashboard:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_notifications:
                        viewPager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
        return view;

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存BottomBar的状态
        mBottomBar.onSaveInstanceState(outState);
    }


    private void initViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(null == environmentFragment ? environmentFragment = EnvironmentFragment.newInstance() : environmentFragment);
        fragmentList.add(null == equipmentFragment ? equipmentFragment = EquipmentFragment.newInstance() : equipmentFragment);
        fragmentList.add(null == personageFragment ? personageFragment = PersonageFragment.newInstance() : personageFragment);
        viewPager.setAdapter(new MyPagerAdapter(getFragmentManager(), fragmentList));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomBar.selectTabAtPosition(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



}
