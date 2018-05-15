package com.goleee.luck;


import android.app.Application;
import android.content.Context;

import com.yang.easyhttp.EasyHttpClient;


public class LuckApplication extends Application
{
    private static Context content;


    @Override
    public void onCreate()
    {
        super.onCreate();
        EasyHttpClient.init(this);
        EasyHttpClient.initDownloadEnvironment(2);
        content = getApplicationContext();
    }


    public static Context getInstance()
    {
        return content;
    }
}
