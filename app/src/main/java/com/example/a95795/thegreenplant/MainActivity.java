package com.example.a95795.thegreenplant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.a95795.thegreenplant.custom.AndroidWorkaround;
import com.example.a95795.thegreenplant.register.EndMessageFragment;
import com.example.a95795.thegreenplant.Login.LoginFragment;
import com.example.a95795.thegreenplant.register.MessageFragment;
import com.example.a95795.thegreenplant.register.PhoneFragment;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends SupportActivity  {
    private FragmentTransaction mFragmentTransaction;//fragment事务
    private FragmentManager mFragmentManager;//fragment管理者
    private MessageFragment messageFragment;
    private PhoneFragment phonefragment;
    private EndMessageFragment endMessageFragment;
    private LoginFragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //适配三大虚拟键 避免遮盖控件
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
        loadRootFragment(R.id.Home, LoginFragment.newInstance()); //activity初始加载LogonFragment



    }
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向动画
        return new DefaultHorizontalAnimator();
    }

    //简单的更新fragment
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Home, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    //显示和隐藏fragment
    public void setClick(int type) {
        //开启事务
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //显示之前将所有的fragment都隐藏起来,在去显示我们想要显示的fragment
        hideFragment(transaction);
        switch (type) {
            case 0:
                if (phonefragment == null) {
                    phonefragment = new PhoneFragment();
                    //加入事务
                    transaction.add(R.id.Home, phonefragment);
                } else {
                    transaction.show(messageFragment);
                }
                break;
            case 1:
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    //加入事务
                    transaction.add(R.id.Home, messageFragment);
                } else {
                    transaction.show(messageFragment);
                }
                break;
            case 2:
                if (endMessageFragment == null) {
                    endMessageFragment = new EndMessageFragment();
                    //加入事务
                    transaction.add(R.id.Home, endMessageFragment);
                } else {
                    transaction.show(endMessageFragment);
                }
                break;
            case 3:
                if (loginFragment == null) {
                    loginFragment = new LoginFragment();
                    //加入事务
                    transaction.add(R.id.Home, loginFragment);
                } else {
                    transaction.show(loginFragment);
                }
                break;
        }
        //提交事务
        transaction.commit();
    }

    public void hideFragment(FragmentTransaction fragmentTransaction) {
        //如果此fragment不为空的话就隐藏起来
        if (messageFragment != null) {
            fragmentTransaction.hide(messageFragment);
        }
        if (phonefragment != null) {
            fragmentTransaction.hide(phonefragment);
        }
        if (endMessageFragment != null) {
            fragmentTransaction.hide(endMessageFragment);
        }
        if (loginFragment != null) {
            fragmentTransaction.hide(loginFragment);
        }
    }
    @Override
    public void onBackPressedSupport() {
        if(getSupportFragmentManager().getBackStackEntryCount() <= 1){
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
        } else
            getSupportFragmentManager().popBackStack();

    }


}
