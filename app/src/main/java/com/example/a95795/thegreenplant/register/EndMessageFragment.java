package com.example.a95795.thegreenplant.register;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.a95795.thegreenplant.R;
import com.example.a95795.thegreenplant.custom.Message;
import com.example.a95795.thegreenplant.custom.MyApplication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import me.yokeyword.swipebackfragment.SwipeBackFragment;


public class EndMessageFragment extends SwipeBackFragment {

    private EditText password,passwordAffirm,userId;
    private String Password,PasswordAffirm,Name,ID,Job_Number,Phone,UserId;
    private int id;
    private Button button;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end_message, container, false);
       init(view);endButton();
        EventBus.getDefault().register(this);
        Toast.makeText(EndMessageFragment.this.getActivity(),Name+ID+Job_Number+Phone+id,Toast.LENGTH_LONG).show();
        return attachToSwipeBack(view);
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(Message message) {
        Name = message.getName();
        ID = message.getId();
        Job_Number = message.getJob_Number();
        Phone = message.getPhone();
        id = message.getThisId();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }
    //提交按钮
    public void endButton(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Password = password.getText().toString();
                PasswordAffirm = passwordAffirm.getText().toString();
                UserId = userId.getText().toString();
                if (Password.equals("")){
                    password.setError("密码不能为空");
                }else if (PasswordAffirm.equals("")){
                    passwordAffirm.setError("密码不能为空");
                }else if (Password.equals(PasswordAffirm)){
                    register();
                }else {
                    passwordAffirm.setError("两次密码不相同");
                }
            }
        });
    }
    //初始化
    public void init(View view){
    password = (EditText) view.findViewById(R.id.pressword);
    passwordAffirm = (EditText) view.findViewById(R.id.presswordTwo);
    userId = (EditText) view.findViewById(R.id.userid);
    button = (Button) view.findViewById(R.id.end);
}
    public void register(){
        String url = getString(R.string.ip)+"user/useradd";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                "{\n" +
                        "\t\"id\":\""+id+"\",\n" +
                        "\t\"userid\":\""+UserId+"\",\n" +
                        "\t\"phone\":\""+Phone+"\",\n" +
                        "\t\"name\":\""+Name+"\",\n" +
                        "\t\"password\":\""+Password+"\",\n" +
                        "\t\"number\":"+Job_Number+",\n" +
                        "\t\"numberid\":\""+ID+"\"\n" +
                        "}",
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String UserList;
                            UserList = response.getString("UserList");
                            if(UserList.equals("1")){
                               userId.setError("用户名重复，请重新输入");
                            }else{
                                Toast.makeText(EndMessageFragment.this.getActivity(),"注册成功",Toast.LENGTH_LONG).show();

                            }
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
