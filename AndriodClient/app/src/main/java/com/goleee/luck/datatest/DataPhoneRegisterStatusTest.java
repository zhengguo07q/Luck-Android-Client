package com.goleee.luck.datatest;


import com.goleee.luck.data.DataPhoneRegisterStatus;


public class DataPhoneRegisterStatusTest extends DataBaseTest
{
    public DataPhoneRegisterStatus getData()
    {
        DataPhoneRegisterStatus data = new DataPhoneRegisterStatus();
        data.status = 0;    //成功
        return data;
    }
}
