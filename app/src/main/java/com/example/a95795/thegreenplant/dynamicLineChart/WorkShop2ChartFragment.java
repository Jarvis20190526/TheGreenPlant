package com.example.a95795.thegreenplant.dynamicLineChart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.a95795.thegreenplant.R;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WorkShop2ChartFragment extends Fragment {
    private View view;
    private LineChart chart;
    private DynamicLineChartManager dynamicLineChartManager;
    private List<Integer> list = new ArrayList<>(); //数据集合
    private List<String> names = new ArrayList<>(); //折线名字集合
    private List<Integer> colour = new ArrayList<>();//折线颜色集合
    private String city = "shanghai";
    private String TAG = "volley";
    private String json;
    private JSONArray HeWeather6;
    private JSONObject now;
    private int tmp,fl,hum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workshop1, container, false);
        initView();
        startThread();
        return view;
    }

    public void initView(){
        chart = view.findViewById(R.id.chart_workshop1);
        //折线名字
        names.add("PM2.5");
        names.add("温度");
        names.add("相对湿度");
        //折线颜色
        colour.add(Color.CYAN);
        colour.add(Color.GREEN);
        colour.add(Color.BLUE);
        dynamicLineChartManager = new DynamicLineChartManager(chart, names, colour);
        dynamicLineChartManager.setYAxis(100, 0, 10);
    }
    public void getData() {
        String url = "https://free-api.heweather.net/s6/weather//now?location="+city+"&key="+getString(R.string.key);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                "{}",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        json = response.toString();
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            HeWeather6  = jsonObject.getJSONArray("HeWeather6");

                            for(int i=0;i<HeWeather6.length();i++){
                                now = HeWeather6.getJSONObject(i).getJSONObject("now");

                            }
                            hum = now.getInt("hum");//湿度
                            tmp = now.getInt("tmp");//温度
                            fl = now.getInt("cloud");//pm2.5
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        });
        RequestQueue v = Volley.newRequestQueue(this.getContext());
        v.add(request);
    }
    public void startThread(){
        new Thread(new Runnable(){
            public void run() {
                while(true){
                    getData();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //添加数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    list.add(fl);
                    list.add(tmp);
                    list.add(hum);
                    dynamicLineChartManager.addEntry(list);
                    list.clear();
                }
            }
        }).start();
    }
}
