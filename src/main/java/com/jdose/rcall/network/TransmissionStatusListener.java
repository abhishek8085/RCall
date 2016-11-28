package com.jdose.rcall.network;

/**
 * Created by abhishek.i on 1/27/2015.
 */
public interface TransmissionStatusListener < T extends NetworkResponse >
{
    public void onSuccess();

    public void onFailure( Throwable t );
}
