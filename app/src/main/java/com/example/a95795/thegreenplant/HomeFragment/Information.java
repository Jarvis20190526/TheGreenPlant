package com.example.a95795.thegreenplant.HomeFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.eminayar.panter.PanterDialog;
import com.example.a95795.thegreenplant.R;
import com.example.a95795.thegreenplant.custom.MyApplication;
import com.example.a95795.thegreenplant.custom.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import ru.katso.livebutton.LiveButton;

public class Information extends Fragment {

    private ImageView mHBack;
    private ImageView mHHead;

    private ItemView mNickName;
    private ItemView mSex;
    private ItemView mId;
    private ItemView mTelephone;
    private ItemView mTime;
    private ItemView mJob;
    private ItemView mWorkshop;
    private LiveButton exit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, null);
    }
    public static Information newInstance() {
        return new Information();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        set();
        setData();
        initView();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PanterDialog(getContext())
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getActivity().finish();
                            }
                        })
                        .setNegative("取消")
                        .setMessage("是否退出登录本账号")
                        .isCancelable(true)
                        .show();
            }
        });

    }

    public void set(){
        mHBack = (ImageView) getActivity().findViewById(R.id.h_back);
        mHHead = (ImageView) getActivity().findViewById(R.id.h_head);
        //下面item控件
        mNickName = (ItemView) getActivity().findViewById(R.id.nickName);
        mSex = (ItemView) getActivity().findViewById(R.id.sex);
        mTelephone = (ItemView) getActivity().findViewById(R.id.telephone);
        mJob = (ItemView) getActivity().findViewById(R.id.job);
        mWorkshop = (ItemView) getActivity().findViewById(R.id.workshop);
        exit=getActivity().findViewById(R.id.ex);
    }

    private void setData() {
        //设置背景磨砂效果
        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new BlurTransformation(getContext(), 25), new CenterCrop(getContext()))
                .into(mHBack);
        //设置圆形图像
        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(mHHead);

        //设置用户名整个item的点击事件
        mNickName.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
        //修改用户名item的左侧图标
      /* mNickName.setLeftIcon(R.drawable.ic_phone);
        //
        mNickName.setLeftTitle("修改后的用户名");
        mNickName.setRightDesc("名字修改");
        mNickName.setShowRightArrow(false);
        mNickName.setShowBottomLine(false);

        //设置用户名整个item的点击事件
        mNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }
        });
*/

    }

    private void initView() {
        String url = getString(R.string.ip)+"user/userall";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                "{}",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("volley", response.toString());
                        try {
                            List<User> producttype = new Gson().fromJson(response.getString("UserList"), new TypeToken<List<User>>() {
                            }.getType());
                            for (int i=0;i<producttype.size();i++){
                                if (producttype.get(i).getUserId()==101000){
                                    mNickName.setRightDesc(producttype.get(i).getUserName());
                                    mTelephone.setRightDesc(producttype.get(i).getUserCall()+"");
                                    if(producttype.get(i).getUserWork()==0){
                                        mJob.setRightDesc("生产员");
                                        mWorkshop.setRightDesc("生产员无管理车间");
                                    }else{
                                        mJob.setRightDesc("管理员");
                                        mWorkshop.setRightDesc(producttype.get(i).getUserWorkshop()+"");
                                    }
                                    if (producttype.get(i).getUserSex()==0) {
                                        mSex.setRightDesc("男");
                                    }else{
                                        mSex.setRightDesc("女");
                                        Glide.with(getContext()).load(R.drawable.test)
                                                .bitmapTransform(new BlurTransformation(getContext(), 25), new CenterCrop(getContext()))
                                                .into(mHBack);
                                        //设置圆形图像
                                        Glide.with(getContext()).load(R.drawable.test)
                                                .bitmapTransform(new CropCircleTransformation(getContext()))
                                                .into(mHHead);
                                    }
                                   /* Date date=producttype.get(i).getUserFirstjob();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    mTime.setRightDesc(sdf.format(date));*/
                                }

                            }
//                           HocellAdapter myAdapter=new HocellAdapter(producttype,R.layout.hocell);
//                           LinearLayoutManager manager=new LinearLayoutManager(getActivity());
//                           manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            //time.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL));
//                           time.setLayoutManager(manager);
//                           time.setAdapter(myAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley", error.toString());
                    }
                }
        );
        MyApplication.addRequest(jsonObjectRequest, "MainActivity");
    }



}
