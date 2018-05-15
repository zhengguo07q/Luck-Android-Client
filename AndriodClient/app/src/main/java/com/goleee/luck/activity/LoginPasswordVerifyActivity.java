package com.goleee.luck.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.goleee.luck.R;
import com.goleee.luck.data.DataBase;
import com.goleee.luck.network.NetClientCallback;
import com.goleee.luck.network.NetClientConstant;
import com.goleee.luck.network.NetClientUtility;


public class LoginPasswordVerifyActivity extends AppCompatActivity
{
    private Button returnButton;

    private EditText passwordEditText;

    private Button loginButton;

    private Button forgetPasswordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password_verify);


        initView();
        initData();
        addListener();
    }


    private void initView()
    {
        returnButton = findViewById(R.id.button_return);
        passwordEditText = findViewById(R.id.editText_password);
        loginButton = findViewById(R.id.button_login);
        forgetPasswordButton = findViewById(R.id.textView_forgetPassword);
    }


    private void initData()
    {
        loginButton.setEnabled(false);
    }


    private void addListener()
    {
        //返回输入电话号码页
        returnButton.setOnClickListener(view -> {

            Intent intent = new Intent();
            intent.setClass(LoginPasswordVerifyActivity.this, LoginPhoneActivity.class);
            LoginPasswordVerifyActivity.this.startActivity(intent);
        });
        //跳转到电话号码验证页
        forgetPasswordButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(LoginPasswordVerifyActivity.this, LoginPhoneVerifyActivity.class);
            LoginPasswordVerifyActivity.this.startActivity(intent);
        });
        //输入密码后的改变
        passwordEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }


            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.length() >= 5)
                {
                    loginButton.setEnabled(true);
                } else
                {
                    if(editable.length() < 5)
                    {
                        loginButton.setEnabled(false);
                    }
                }
            }
        });

        //发送登录协议，跳转到注册界面或者主界面
        loginButton.setOnClickListener(view -> {

            NetClientUtility.get(NetClientConstant.URL_LOGIN_VERIFY_PHONE_STATUS,
                                 new NetClientCallback<DataBase>()
                                 {
                                     @Override
                                     public void onSuccess(DataBase data)
                                     {
                                         super.onSuccess(data);
                                         if(data.status == -1)
                                         {
                                             //需要注册基本信息
                                             Intent intent = new Intent();
                                             intent.setClass(LoginPasswordVerifyActivity.this, RegisterBaseInfoActivity.class);
                                             LoginPasswordVerifyActivity.this.startActivity(intent);
                                         }
                                         else if(data.status == 0)
                                         {
                                             //注册过，跳转到主界面
                                             Intent intent = new Intent();
                                             intent.setClass(LoginPasswordVerifyActivity.this, MainActivity.class);
                                             LoginPasswordVerifyActivity.this.startActivity(intent);
                                         }
                                     }
                                 });
        });
    }
}
