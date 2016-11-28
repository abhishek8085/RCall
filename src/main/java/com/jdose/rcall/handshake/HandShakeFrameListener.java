package com.jdose.rcall.handshake;

import com.jdose.rcall.frame.SerializableFrame;

/**
 * Created by abhishek.i on 6/4/2015.
 */
public interface HandShakeFrameListener
{
    void onArrivalOfHandShakeFrame( SerializableFrame serializableFrame );
}
