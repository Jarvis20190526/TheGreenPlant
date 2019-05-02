package com.example.a95795.thegreenplant.Logon;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.a95795.thegreenplant.custom.CustomVideoView;
import com.example.a95795.thegreenplant.MainActivity;
import com.example.a95795.thegreenplant.custom.MyApplication;
import com.example.a95795.thegreenplant.R;
import com.example.a95795.thegreenplant.register.phoneFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import me.yokeyword.swipebackfragment.SwipeBackFragment;


public class LogonFragment extends SwipeBackFragment {
    private CustomVideoView videoview;
    private EditText userName, passWord;
    private String UserName, PassWord;
    private CheckBox box;
    private Button button, button2;
    private ImageView imageView;
    File file = null;
    private TextInputLayout mTILUsername, mTILUsername2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logon, container, false);
        init(view);user();password();Load();initView();registerButton();logonButton();imageButton();
        return attachToSwipeBack(view);
    }
    //注册按钮
    public void registerButton(){
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity ) getActivity()).replaceFragment(new phoneFragment());
            }
        });

    }
    //手机登录按钮
    public void imageButton(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    //登录按钮
    public void logonButton(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }
    //控件初始化
    public void init(View view) {
        userName = (EditText) view.findViewById(R.id.User);
        passWord = (EditText) view.findViewById(R.id.Password);
        box = (CheckBox) view.findViewById(R.id.cb_remerber);
        button = (Button) view.findViewById(R.id.button);
        button2 = (Button) view.findViewById(R.id.button2);
        mTILUsername = (TextInputLayout) view.findViewById(R.id.textInputLayout);
        mTILUsername2 = (TextInputLayout) view.findViewById(R.id.textInputLayout2);
        videoview = (CustomVideoView) view.findViewById(R.id.videoview);
        imageView = (ImageView) view.findViewById(R.id.phone);

    }
    //获得字符串
    public void init() {
        UserName = userName.getText().toString();
        PassWord = passWord.getText().toString();
    }
    //登录规则
    public void Login() {
        init();
        if (UserName.equals("")) {
            mTILUsername.setError("账号不能为空");
        } else if (PassWord.equals("")) {
            mTILUsername2.setError("密码不能为空");
        } else {
            LoginHttp();
        }

    }
    //登录验证
    public void LoginHttp() {
        String url = getString(R.string.ip) + "user/userlogin";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                "{\n" +
                        "\t\"userid\":\"" + UserName + "\",\n" +
                        "\t\"password\":\"" + PassWord + "\"\n" +
                        "}",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String UserList;
                            UserList = response.getString("UserList");
                            if (UserList.equals("0")) {
                                String url = getString(R.string.ip) + "user/userloginagain";
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                        Request.Method.POST,
                                        url,
                                        "{\n" +
                                                "\t\"phone\":\"" + UserName + "\",\n" +
                                                "\t\"password\":\"" + PassWord + "\"\n" +
                                                "}",
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    String UserList;
                                                    UserList = response.getString("UserList");
                                                    if (UserList.equals("0")) {
                                                        Toast.makeText(LogonFragment.this.getActivity(), "密码错误，请检查账号或密码", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(LogonFragment.this.getActivity(), "登录成功", Toast.LENGTH_LONG).show();
                                                        remeber();
                                                    }
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
                            } else {
                                Toast.makeText(LogonFragment.this.getActivity(), "登录成功", Toast.LENGTH_LONG).show();
                                remeber();
                            }
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
    //记住密码
    public void load() throws IOException {
        FileInputStream fiStream = null;
        BufferedReader br = null;
        file = new File(getContext().getFilesDir(), "info.txt");
        if (file.exists()) {
            try {
                fiStream = new FileInputStream(file);
                br = new BufferedReader(new InputStreamReader(fiStream));
                String str = br.readLine();
                String arr[] = str.split("##");
                userName.setText(arr[0]);
                passWord.setText(arr[1]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    br.close();
                }
            }
        } else {

        }
    }
    //记住密码规则
    public void Load() {
        try {
            load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //记住密码相关操作
    public void remeber() {
        {
            String name = userName.getText().toString();
            String pwd = passWord.getText().toString();
            FileOutputStream fos = null;
            if (box.isChecked()) {
                try {
                    file = new File(getContext().getFilesDir(), "info.txt");
                    fos = new FileOutputStream(file);
                    fos.write((name + "##" + pwd).getBytes());


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (file.exists()) {
                    file.delete();
                } else {
                }
            }
        }
    }
    //账号动态监听
    public void user() {
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    mTILUsername.setError("账号不能为空");
                } else {
                    mTILUsername.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    //密码动态监听
    public void password() {
        passWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    mTILUsername2.setError("密码不能为空");
                } else {
                    mTILUsername2.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    //动态壁纸
    private void initView() {

        videoview.setVideoURI(Uri.parse("android.resource://"+getContext().getPackageName()+"/"+R.raw.sport));

        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });

    }
}
