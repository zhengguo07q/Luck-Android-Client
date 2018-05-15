package com.goleee.luck.network;


import com.goleee.luck.datatest.DataBaseTest;
import com.yang.easyhttp.EasyHttpClient;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class NetClientUtility
{


    public static <T extends NetClientCallback> void get(String url, T callback)
    {
        //EasyHttpClient.get(url, callback);
        Type classType = callback.getClass().getGenericSuperclass();
        Type tArg = ((ParameterizedType) classType).getActualTypeArguments()[0];
        String clazzName = tArg.toString();
        Class clz = null;
        try
        {
            String a = "com.goleee.luck.datatest." + clazzName.substring(27);
            clz = Class.forName("com.goleee.luck.datatest." + clazzName.substring(27) + "Test");
            DataBaseTest test = (DataBaseTest)clz.newInstance();
            callback.onSuccess(test.getData());
        }
        catch(ClassNotFoundException e)
        {
        }
        catch(Exception e)
        {

        }
    }
}
