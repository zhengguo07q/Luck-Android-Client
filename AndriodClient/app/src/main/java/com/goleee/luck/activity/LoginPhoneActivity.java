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
import com.goleee.luck.utility.NumberUtility;


/**
 * 电话号码输入正确后， 显示继续按钮，点击继续，发送到服务器进行号码验证
 */

public class LoginPhoneActivity extends AppCompatActivity
{
    /**
     * 电话号码输入框
     */
    private EditText phoneEditText;

    /**
     * 继续
     */
    private Button continueButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        initView();
        initData();
        addListener();
    }


    private void initView()
    {
        phoneEditText = findViewById(R.id.editText_phone);
        continueButton = findViewById(R.id.button_continue);
    }


    private void initData()
    {
        continueButton.setEnabled(false);
    }


    private void addListener()
    {
        phoneEditText.addTextChangedListener(new TextWatcher()
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
                if(NumberUtility.isPhone(editable.toString()))
                {
                    continueButton.setEnabled(true);
                } else
                {
                    continueButton.setEnabled(false);
                }
            }
        });

        continueButton.setOnClickListener(view -> {
            NetClientUtility.get(NetClientConstant.URL_LOGIN_VERIFY_PHONE_STATUS,
                                 new NetClientCallback<DataBase>()
                                 {
                                     @Override
                                     public void onSuccess(DataBase data)
                                     {
                                         super.onSuccess(data);
                                        if(data.status == -1)
                                        {
                                            //没有注册， 跳转到短信验证
                                            Intent intent = new Intent();
                                            intent.setClass(LoginPhoneActivity.this, LoginPhoneVerifyActivity.class);
                                            LoginPhoneActivity.this.startActivity(intent);
                                        }
                                        else if(data.status == 0)
                                        {
                                            //注册过，跳转到输入密码页面
                                            Intent intent = new Intent();
                                            intent.setClass(LoginPhoneActivity.this, LoginPasswordVerifyActivity.class);
                                            LoginPhoneActivity.this.startActivity(intent);
                                        }
                                     }
                                 });
        });
    }
}
