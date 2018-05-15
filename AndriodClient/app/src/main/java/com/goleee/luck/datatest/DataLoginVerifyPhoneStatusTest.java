package com.goleee.luck.datatest;


import com.goleee.luck.data.DataLoginVerifyPhoneStatus;


/**
 * 登录后验证登录状态， 是验证过基本信息还是没有验证过基本信息
 */
public class DataLoginVerifyPhoneStatusTest extends DataBaseTest
{
    public DataLoginVerifyPhoneStatus getData()
    {
        DataLoginVerifyPhoneStatus data = new DataLoginVerifyPhoneStatus();
        data.status = 0;    //成功
        return data;
    }
}
