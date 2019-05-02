package com.example.a95795.thegreenplant.HomeFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentItemFragment extends Fragment {

    private ExpandableListView expandableListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipment_item, container, false);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expend_list);
        init();
        return view;
    }
    public void init(){
        String url =getString(R.string.ip)+"user/dianji";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                "",
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Gson gson = new Gson();
                            final ArrayList<Equipment_Dianji> subjectList = gson.fromJson(response.getJSONArray("dianji").toString(),new TypeToken<List<Equipment_Dianji>>(){}.getType());
                            expandableListView.setAdapter(new EquipmentAdapter(subjectList));
                            //定义临时数组
                            ArrayList list = new ArrayList();
                            //遍历找到出现问题的设备
                            for(int i =0;i<subjectList.size();i++){
                                if(subjectList.get(i).getEquipmentNow().equals("2")){
                                    list.add(subjectList.get(i).getEquipmentDianji());
                                }
                            }
                            //提示出现故障位置
                            new SweetAlertDialog(EquipmentItemFragment.this.getActivity(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("警告")
                                    .setContentText(list + "机器出现故障！")
                                    .setConfirmText("确定")
                                    .show();

                            //控制只能打开一个组
                            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                                @Override
                                public void onGroupExpand(int groupPosition) {
                                    int count = new EquipmentAdapter(subjectList).getGroupCount();
                                    for(int i = 0;i < count;i++){
                                        if (i!=groupPosition){
                                            expandableListView.collapseGroup(i);
                                        }
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley",error.toString());
                    }
                }
        );
        MyApplication.addRequest(jsonObjectRequest,"MainActivity");

    }
}
