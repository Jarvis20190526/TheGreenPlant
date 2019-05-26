package com.example.a95795.thegreenplant.Login;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.a95795.thegreenplant.HomeActivity;
import com.example.a95795.thegreenplant.MainActivity;
import com.example.a95795.thegreenplant.custom.CustomVideoView;
import com.example.a95795.thegreenplant.custom.Id;
import com.example.a95795.thegreenplant.custom.MyApplication;
import com.example.a95795.thegreenplant.R;
import com.example.a95795.thegreenplant.custom.User;
import com.example.a95795.thegreenplant.register.EndMessageFragment;
import com.example.a95795.thegreenplant.register.ForgetFragment;
import com.example.a95795.thegreenplant.register.MessageFragment;
import com.example.a95795.thegreenplant.register.PhoneFragment;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.yokeyword.fragmentation.SupportFragment;


public class LoginFragment extends SupportFragment {
    private CustomVideoView videoview;
    private EditText userName, passWord;
    private String UserName, PassWord;
    private TextView textView1, textView2;
    private CheckBox box;
    private SpinKitView spinKitView;
    private Button button;
    private ImageView imageView;
    File file = null;
    private TextInputLayout mTILUsername, mTILUsername2;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logon, container, false);
        init(view);
        user();
        password();
        Load();
        initView();
        registerButton();
        logonButton();
        imageButton();
        forgetButton();

        return view;
    }

    //忘记密码
    public void forgetButton() {
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(ForgetFragment.newInstance());
            }
        });
    }

    //注册按钮
    public void registerButton() {
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(PhoneFragment.newInstance());
            }
        });
    }

    //手机登录按钮
    public void imageButton() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    //登录按钮
    public void logonButton() {
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
        mTILUsername = (TextInputLayout) view.findViewById(R.id.textInputLayout);
        mTILUsername2 = (TextInputLayout) view.findViewById(R.id.textInputLayout2);
        videoview = (CustomVideoView) view.findViewById(R.id.videoview);
        imageView = (ImageView) view.findViewById(R.id.phone);
        textView1 = (TextView) view.findViewById(R.id.tv_register);
        textView2 = (TextView) view.findViewById(R.id.tv_find_pwd);
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
                        "\t\"userNum\": \"" + UserName + "\",\n" +
                        "\t \"userPassword\": \"" + PassWord + "\"\n" +
                        "}",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String UserList;
                            UserList = response.getString("UserList");
                            if (UserList.equals("0")) {
                                loginPhone();
                            } else {
                                skip();
                                Toast.makeText(LoginFragment.this.getActivity(), "登录成功", Toast.LENGTH_LONG).show();
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

    //跳转主界面
    public void skip() {
        Intent intent = new Intent(LoginFragment.this.getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();

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

        videoview.setVideoURI(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.sport));

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

    //工号登录
    public void loginUserid() {
        String url = getString(R.string.ip) + "user/UseridLogin";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                "{\"userId\": \"" + UserName + "\",\"userPassword\": \"" + PassWord + "\"\n" +
                        "}",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String UserList;
                            UserList = response.getString("UserList");
                            Gson gson = new Gson();

                            if (UserList.equals("0")) {
                                Toast.makeText(LoginFragment.this.getActivity(), "密码错误，请检查账号或密码", Toast.LENGTH_LONG).show();
                            } else {
                                skip();
                                Toast.makeText(LoginFragment.this.getActivity(), "登录成功", Toast.LENGTH_LONG).show();
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

    //手机号登录
    public void loginPhone() {
        String url = getString(R.string.ip) + "user/PhoneLogin";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                "{\n" +
                        "\t\"userPassword\": \"" + PassWord + "\",\n" +
                        "\t  \"userCall\": \"" + UserName + "\"\n" +
                        "}",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String UserList;
                            UserList = response.getString("UserList");
                            if (UserList.equals("0")) {
                                loginUserid();
                            } else {
                                skip();
                                Toast.makeText(LoginFragment.this.getActivity(), "登录成功", Toast.LENGTH_LONG).show();
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

}
