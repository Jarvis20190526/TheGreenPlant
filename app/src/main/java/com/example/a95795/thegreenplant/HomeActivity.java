package com.example.a95795.thegreenplant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a95795.thegreenplant.HomeFragment.EnvironmentFragment;
import com.example.a95795.thegreenplant.HomeFragment.EquipmentFragment;
import com.example.a95795.thegreenplant.HomeFragment.HomeFragment;
import com.example.a95795.thegreenplant.HomeFragment.PersonageFragment;
import com.example.a95795.thegreenplant.Login.LoginFragment;
import com.example.a95795.thegreenplant.adapter.MyPagerAdapter;
import com.example.a95795.thegreenplant.custom.SecretTextView;
import com.example.a95795.thegreenplant.custom.StatusBarCompat;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

public class HomeActivity extends SupportActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    SecretTextView secretTextView;

    public static HomeActivity newInstance() {
        return new HomeActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        StatusBarCompat.compat(this, Color.parseColor("#FF16A295"));
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadRootFragment(R.id.home, HomeFragment.newInstance()); //activity初始加载HomeFragment
        secretTextView = (SecretTextView)findViewById(R.id.textView);
        secretTextView.setDuration(1000);
        secretTextView.setIsVisible(true);



    }


    @Override
    public void onBackPressedSupport() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示：");
            builder.setMessage("您确定退出？");
            //设置确定按钮
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            //设置取消按钮
            builder.setPositiveButton("取消", null);
            //显示提示框
            builder.show();

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Toast.makeText(HomeActivity.this, "123", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void test(int number) {
        switch (number) {
            case 1:
                secretTextView.hide();
                secretTextView.setText("环境监测");
                secretTextView.show();
                break;
            case 2:
                secretTextView.hide();
                secretTextView.setText("设备监测");
                secretTextView.show();

                break;
            case 3:
                secretTextView.hide();
                secretTextView.setText("个人中心");
                secretTextView.show();
            default:
                break;
        }
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
