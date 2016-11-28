package com.jdose.rcall.network;

import com.jdose.rcall.frame.SerializableFrame;

/**
 * Created by abhishek.i on 5/11/2015.
 */
public interface NetworkRequestListener < T extends SerializableFrame>
{
    void onArrivalOfNetworkRequest( T serializableFrame );
}
