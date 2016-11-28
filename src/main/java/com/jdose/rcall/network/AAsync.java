package com.jdose.rcall.network;

import com.jdose.rcall.rmi.AsyncCallback;

/**
 * Created by abhishek.i on 5/26/2015.
 */
public interface AAsync
{
    void test( String test, AsyncCallback< String > responseString );
}
