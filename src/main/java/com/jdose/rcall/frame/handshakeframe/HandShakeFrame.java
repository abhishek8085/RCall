
package com.jdose.rcall.frame.handshakeframe;

import com.jdose.rcall.Machine;
import com.jdose.rcall.frame.AbstractFrame;


/**
 * Created by abhishek.i on 2/19/2015.
 */

public class HandShakeFrame extends AbstractFrame
{

    private String version;

    public HandShakeFrame( long frameId, int frameIdentifier, Machine destinationMachine, Machine sourceMachine, long timeStamp )
    {
        super(frameId, frameIdentifier, destinationMachine, sourceMachine, timeStamp);
    }

    @Override
    public byte[] getPayloadData()
    {
        return new byte[0];
    }

    @Override
    public < T > T getPayloadObject()
    {
        return ( T ) version ;
    }

    @Override
    protected Class<?> getPayloadObjectType()
    {
        return String.class;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    @Override
    protected boolean isObjectPayload()
    {
        return true;
    }
}
