package com.example.a95795.thegreenplant.HomeFragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.a95795.thegreenplant.R;
import com.example.a95795.thegreenplant.adapter.EquipmentAdapter;
import com.example.a95795.thegreenplant.custom.Equipment_Dianji;
import com.example.a95795.thegreenplant.custom.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.angmarch.views.NiceSpinner;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentFragment extends Fragment {

    private NiceSpinner niceSpinner;
    public static EquipmentFragment newInstance() {
        return new EquipmentFragment();
    }
    // SpringView
    SpringView springView;


    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //结束刷新动作
                    springView.onFinishFreshAndLoad();
                    //更新adapter
                    replaceFragment(new EquipmentItemFragment());
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_equipment, container, false);
        springView = (SpringView) view.findViewById(R.id.Spview_spbranchList);
        niceSpinner =  view.findViewById(R.id.nice_spinner);
        initData();initEvnet();replaceFragment(new EquipmentItemFragment());
        List<String> spinnerData = new LinkedList<>(Arrays.asList("一号车间", "二号车间","三号车间","四号车间"));
        niceSpinner.attachDataSource(spinnerData);
        niceSpinner.setTextSize(13);
        niceSpinner.setArrowDrawable(R.drawable.jiantou);

        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EquipmentFragment.this.getActivity(),"点击了第"+(1 + position)+"个",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        return view;
    }
    private void initData() {

        springView.setHeader(new DefaultHeader(this.getActivity()));

    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.equipment, fragment);
        transaction.commit();
    }
    private void initEvnet() {

        springView.setType(SpringView.Type.FOLLOW);

//        刷新和载入很多其它的事件处理
//        假设须要处理的话，仅仅需在代码中加入监听：
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                handler.sendEmptyMessageDelayed(0,1000);
            }

            @Override
            public void onLoadmore() {

            }
        });

    }



}
