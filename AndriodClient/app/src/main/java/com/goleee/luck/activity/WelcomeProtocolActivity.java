package com.goleee.luck.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.goleee.luck.R;


public class WelcomeProtocolActivity extends AppCompatActivity
{
    private Button returnButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_protocol);
        initView();
        addListener();
    }


    /**
     * 初始化控件
     */
    public void initView()
    {
        returnButton = findViewById(R.id.button_return);
    }


    /**
     * 添加监听
     */
    public void addListener()
    {
        returnButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(WelcomeProtocolActivity.this, WelcomeActivity.class);
            WelcomeProtocolActivity.this.startActivity(intent);
        });
    }
}
