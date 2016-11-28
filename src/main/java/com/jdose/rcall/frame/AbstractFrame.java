/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdose.rcall.frame;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.utils.CArrayUtils;

import java.util.Arrays;

/**
 *
 * @author abhishek.i
 */
public abstract class AbstractFrame implements SerializableFrame, ChecksumEnabled
{
    protected long frameId;
    protected int frameIdentifier;

    protected long timeStamp;
    protected int sequenceNumber;
    protected Machine destinationMachine;
    protected Machine sourceMachine;
    protected byte[] transmittedCheckSum;
    private boolean isResponse;

    public AbstractFrame( long frameId, int frameIdentifier,Machine destinationMachine, Machine sourceMachine, long timeStamp )
    {
        this.frameId = frameId;
        this.frameIdentifier = frameIdentifier;
        this.sourceMachine = sourceMachine;
        this.destinationMachine = destinationMachine;
        this.timeStamp = timeStamp;
    }

    public void setSequenceNumber( int sequenceNumber )
    {
        this.sequenceNumber = sequenceNumber;
    }


    @Override
    public long getFrameUUID()
    {
        return frameId;
    }

    @Override
    public int getFrameIdentifier()
    {
        return frameIdentifier;
    }

    @Override
    public Machine getDestinationMachine()
    {
        return destinationMachine;
    }

    @Override
    public Machine getSourceMachine()
    {
        return sourceMachine;
    }

    @Override
    public long getCreatedDateTime()
    {
        return timeStamp;
    }

    @Override
    public int getSequenceNumber() 
    {
        return sequenceNumber;
    }

    @Override
    public long getLatency() 
    {
        return ( System.currentTimeMillis() - timeStamp );
    }

    @Override
    public byte[] getCheckSum()
    {
        return transmittedCheckSum ;
    }

    @Override
    public byte[] getCompleteDataWithoutChecksum()
    {
        String delimiter = getFieldDelimiter();
        
        byte[] metadata= new StringBuilder().append(getFrameUUID()).append(delimiter)
                                            .append(getFrameIdentifier()).append(delimiter)
                                            .append(getSourceMachine()).append(delimiter)
                                            .append(getCreatedDateTime()).append(delimiter)
                                            .append(getSequenceNumber()).append(delimiter).toString().getBytes();
                        
        return CArrayUtils.concate(metadata, getPayloadData());
    }

    @Override
    public void generateCheckSum()
    {
        transmittedCheckSum =  FrameHelper.calculateCurrentCheckSum( getCompleteDataWithoutChecksum() );
    }

    public boolean isResponse()
    {
        return isResponse;
    }

    public void setResponse( boolean isResponse )
    {
        this.isResponse = isResponse;
    }


    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
        {
            return false;
        }

        if ( !( obj instanceof AbstractFrame ) )
        {
            return false;
        }

        if ( Arrays.equals( ( ( AbstractFrame ) obj).getCompleteDataWithoutChecksum(), getCompleteDataWithoutChecksum() ) )
        {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode( getCompleteDataWithoutChecksum() );
    }


    @Override
    public boolean isRetransmission()
    {
        return false;
    }

    protected  String getFieldDelimiter()
    {
        return "|";
    }
    
    public abstract byte[] getPayloadData();

    public abstract < T > T getPayloadObject();

    protected abstract Class < ? > getPayloadObjectType();

    protected abstract boolean isObjectPayload();

}
