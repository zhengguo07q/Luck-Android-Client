package com.goleee.luck.activity;


import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.goleee.luck.R;
import com.goleee.luck.data.DataBase;
import com.goleee.luck.network.NetClientCallback;
import com.goleee.luck.network.NetClientConstant;
import com.goleee.luck.network.NetClientUtility;


public class LoginPhoneVerifyActivity extends AppCompatActivity
{
    private Button returnButton;
    private TextView phoneNumberText;
    private EditText verifyNumberEditText;
    private Button sendAgainButton;
    private CountDownTimer sendAgainTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_verify);

        initView();
        initData();
        addListener();
    }


    /**
     * 初始化控件
     */
    public void initView()
    {
        returnButton = findViewById(R.id.button_return);
        phoneNumberText = findViewById(R.id.textView_phoneNumber);
        verifyNumberEditText = findViewById(R.id.editText_password);
        sendAgainButton = findViewById(R.id.button_sendAgain);
    }


    /**
     * 初始化数据
     */
    public void initData()
    {
        sendAgainTimer = new CountDownTimer(60000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                String strMsg = getResources().getString(R.string.luck_login_send_again);

                StringBuilder msgBuilder = new StringBuilder();
                msgBuilder.append("(").append(millisUntilFinished / 1000).append(")");

                sendAgainButton.setText(String.format(strMsg, msgBuilder.toString()));
                sendAgainButton.setEnabled(false);
            }


            public void onFinish()
            {
                String strMsg = getResources().getString(R.string.luck_login_send_again);

                sendAgainButton.setText(String.format(strMsg, ""));
                sendAgainButton.setEnabled(true);
            }

        };

        //第一次的时候启用
        sendAgainTimer.start();
    }


    /**
     * 添加监听
     */
    public void addListener()
    {
        //返回填写电话号码页面
        returnButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(LoginPhoneVerifyActivity.this, LoginPhoneActivity.class);
            LoginPhoneVerifyActivity.this.startActivity(intent);
        });
        //发送再次发送验证码协议
        sendAgainButton.setOnClickListener(view -> {
            //在这里再次发送协议
            NetClientUtility.get(NetClientConstant.URL_LOGIN_GET_AUTH_CODE,
                                 new NetClientCallback<DataBase>()
                                 {
                                     @Override
                                     public void onFinish()
                                     {
                                         super.onFinish();
                                     }
                                 });
            sendAgainTimer.start();

        });

        //文本改变
        verifyNumberEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }


            //文本改变完成后发送验证到服务器
            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.length() >= 4)
                {
                    NetClientUtility.get(NetClientConstant.URL_LOGIN_VERIFY_AUTH_CODE,
                                         new NetClientCallback<DataBase>()
                                         {
                                             @Override
                                             public void onFinish()
                                             {
                                                 super.onFinish();
                                             }
                                         });

                }
            }
        });
    }

}
