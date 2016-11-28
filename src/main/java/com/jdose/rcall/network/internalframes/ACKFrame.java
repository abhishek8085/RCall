package com.jdose.rcall.network.internalframes;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.exception.SDSRuntimeException;
import com.jdose.rcall.commons.utils.CArrayUtils;
import com.jdose.rcall.frame.FrameType;
import com.jdose.rcall.frame.SerializableFrame;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by abhishek.i on 5/11/2015.
 */
public class ACKFrame implements SerializableFrame
{
    private ACKFrame(){};
    private Machine destinationMachine;
    private Machine sourceMachine;
    private long createdDateTime;
    private long uuid;
    private MessageDigest checkSumProvider;

    public static ACKFrame getACKFrame( SerializableFrame serializableFrame  )
    {
        return new ACKFrame( serializableFrame.getFrameUUID(), serializableFrame.getSourceMachine(), serializableFrame.getDestinationMachine() );
    }

    private ACKFrame( long uuid, Machine destinationMachine, Machine sourceMachine )
    {
        this.destinationMachine = destinationMachine;
        this.sourceMachine = sourceMachine;
        this.uuid = uuid;
        createdDateTime = System.currentTimeMillis();
    }

    @Override
    public long getFrameUUID()
    {
        return uuid;
    }

    @Override
    public boolean isRetransmission()
    {
        return false;
    }

    @Override
    public int getFrameIdentifier()
    {
        return FrameType.ACK_FRAME.getFrameIdentifier();
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
        return createdDateTime;
    }

    @Override
    public int getSequenceNumber()
    {
        return 0;
    }

    @Override
    public byte[] getCheckSum()
    {
        return null;

        //return Arrays.equals(calculateCurrentCheckSum(getCompleteDataWithoutChecksum()), calculateCurrentCheckSum(getCompleteDataWithoutChecksum()));
    }

    @Override
    public long getLatency()
    {
        return 0;
    }

    private byte[] calculateCurrentCheckSum( byte[] data )
    {
        if( checkSumProvider == null )
        {
            try
            {
                checkSumProvider = MessageDigest.getInstance("MD5");
            }
            catch ( NoSuchAlgorithmException e )
            {
                throw new SDSRuntimeException( e );
            }
        }
        return checkSumProvider.digest(data);
    }

    @Override
    public byte[] getCompleteDataWithoutChecksum()
    {
        String delimiter = ",";

        byte[] metadata= new StringBuilder().append(getFrameUUID()).append(delimiter)
                .append(getFrameIdentifier()).append(delimiter)
                .append(getSourceMachine()).append(delimiter)
                .append(getCreatedDateTime()).append(delimiter)
                .append(getSequenceNumber()).append(delimiter).toString().getBytes();

        return CArrayUtils.concate(metadata, new byte[]{});
    }


}
