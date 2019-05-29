package com.example.a95795.thegreenplant;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutFragment extends SwipeBackFragment implements ISupportFragment {

    private Toolbar toolbar;
    private RelativeLayout relativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_about, null);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        View aboutPage = new AboutPage(getContext())
                .isRTL(false)
                .setImage(R.drawable.ic_leaf)//图片
                .setDescription("致力于绿色车间智能化")//介绍
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("与我联系")
                .addEmail("2410191719@qq.com")//邮箱
                .addWebsite("http://www.ncvt.net")//网站
                .addGitHub("957955071")//github
                .create();

        relativeLayout.addView(aboutPage);
    }

    private void initViews(){
        relativeLayout=getActivity().findViewById(R.id.relativeLayout);
        toolbar= (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar!=null){
  //          actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("关于");
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                 getActivity().finish();
//                break;
//            default:
//
//        }
//        return true;
//    }
}
