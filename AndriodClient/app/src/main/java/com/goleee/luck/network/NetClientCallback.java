package com.goleee.luck.network;


import com.yang.easyhttp.callback.EasyCustomCallback;


public abstract class NetClientCallback<T> extends EasyCustomCallback<T>
{
    @Override
    public void onStart()
    {
        //ui thread.
        //  dialog.show();
    }


    @Override
    public void onFinish()
    {
        //ui thread.
        // dialog.cancel();
    }


    @Override
    public void onSuccess(T content)
    {
        //ui thread
        // ui operation using content object.
    }


    @Override
    public void onFailure(Throwable error, String content)
    {//ui thread.
        // body.setText(content + "\n" + error.toString());
    }


    private void checkNetStatus()
    {

    }
}
