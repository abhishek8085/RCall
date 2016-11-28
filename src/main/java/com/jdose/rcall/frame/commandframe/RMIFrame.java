package com.jdose.rcall.frame.commandframe;

import com.jdose.rcall.Machine;
import com.jdose.rcall.frame.AbstractFrame;
import com.jdose.rcall.rmi.RMIRequest;

/**
 * Created by abhishek.i on 2/19/2015.
 */
public class RMIFrame extends AbstractFrame
{
    private Object rmi;
    private boolean isResponse;

    public RMIFrame( long frameId, int frameIdentifier, Machine destinationMachine, Machine sourceMachine, long timeStamp )
    {
        super( frameId, frameIdentifier, destinationMachine, sourceMachine, timeStamp );
    }

    @Override
    public byte[] getPayloadData()
    {
        return new byte[0];
    }

    public void setRMIRequest ( Object rmi )
    {
        this.rmi = rmi;
    }

    @Override
    public < T > T getPayloadObject()
    {
        return ( T ) rmi;
    }

    @Override
    protected Class< ? > getPayloadObjectType()
    {
        return RMIRequest.class;
    }

    @Override
    protected boolean isObjectPayload()
    {
        return true;
    }

    @Override
    public boolean isResponse()
    {
        return isResponse;
    }

    public void setIsResponse(boolean isResponse)
    {
        this.isResponse = isResponse;
    }
}
