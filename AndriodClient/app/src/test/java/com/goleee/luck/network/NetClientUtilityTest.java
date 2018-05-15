package com.goleee.luck.network;



import com.goleee.luck.data.DataBase;
import com.goleee.luck.data.DataPhoneRegisterStatus;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class NetClientUtilityTest
{
    @Test
    public void get() throws Exception
    {
        NetClientUtility.get(NetClientConstant.URL_LOGIN_VERIFY_PHONE_STATUS,
                             new NetClientCallback<DataBase>()
                             {
                                 @Override
                                 public void onSuccess(DataBase data)
                                 {
                                     super.onSuccess(data);
                                     if(data.status == 0)
                                     {
                                         assertEquals(data.status, 0);
                                     }else if(data.status == 1)
                                     {
                                         assertEquals(data.status, 1);
                                     }
                                 }
                             });
    }
}
