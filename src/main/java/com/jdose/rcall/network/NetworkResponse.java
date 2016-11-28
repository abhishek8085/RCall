package com.jdose.rcall.network;

/**
 * Created by abhishek.i on 2/9/2015.
 */
public class NetworkResponse
{
    private long frameUUID ;
    private Object rawFrame;

    public NetworkResponse( long frameUUID, Object rawFrame )
    {
        this.frameUUID = frameUUID;
        this.rawFrame = rawFrame;
    }

    public long getFrameUUID()
    {
        return frameUUID;
    }

    public Object getRawFrame()
    {
        return rawFrame;
    }
}
