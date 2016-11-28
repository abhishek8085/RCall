package com.jdose.rcall.network;

/**
 * Created by abhishek.i on 2/6/2015.
 */
public interface ResponseCallBack < T >
{
    public void onResponse( T responseObject);

    public void onTimeOut();
}
