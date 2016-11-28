package com.jdose.rcall.network.sdssocket;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.ReInitialisationException;
import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.commons.thread.SDSThread;
import com.jdose.rcall.commons.utils.FixedHashSet;
import com.jdose.rcall.commons.utils.MultiKey;
import com.jdose.rcall.frame.AbstractFrame;
import com.jdose.rcall.frame.FrameHelper;
import com.jdose.rcall.frame.SerializableFrame;
import com.jdose.rcall.helper.ConfigProperties;
import com.jdose.rcall.helper.ConfigPropertiesHelper;
import com.jdose.rcall.helper.ConfigPropertyChangeListener;
import com.jdose.rcall.network.NetworkRequestListener;
import com.jdose.rcall.network.TransmissionStatusListener;
import com.jdose.rcall.network.internalframes.ACKFrame;
import com.jdose.rcall.network.internalframes.NAKFrame;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by abhishek.i on 4/1/2015.
 * Error Control and Flow Control
 */
public class DataLinkSocket implements SDSSocket, NetworkRequestListener
{
    private int port;
    private SDSSocket physicalSocket;
    private NetworkRequestListener networkResponseListener;
    private Set<MultiKey> receivedFrames = new FixedHashSet( 10000 );
    private Map< Long, SentNetworkRequest  > sentFrames = new ConcurrentHashMap< Long, SentNetworkRequest >();

    public DataLinkSocket(SDSSocket physicalSocket)
    {
        this.physicalSocket = physicalSocket;
    }

    public void initialise() throws InitialisationException
    {
        physicalSocket.initialise();
    }

    @Override
    public SocketType getSocketType()
    {
        return physicalSocket.getSocketType();
    }

    @Override
    public Machine getBindMachine()
    {
        return physicalSocket.getBindMachine();
    }

    @Override
    public void registerNetworkRequestListener( NetworkRequestListener networkResponseListener )
    {
        this.networkResponseListener = networkResponseListener;
        physicalSocket.registerNetworkRequestListener( this );
    }

    public void unRegisterNetworkRequestListener()
    {
        networkResponseListener = null;
    }

    @Override
    public void reInitialise() throws ReInitialisationException
    {
        try
        {
            physicalSocket.close();
            initialise();
        }
        catch ( IOException e )
        {
            throw new ReInitialisationException( e );
        }
    }

    @Override
    public void sendFrame( SerializableFrame serializableFrame, TransmissionStatusListener transmissionStatusListener ) throws IOException
    {
        ( (AbstractFrame) serializableFrame ).generateCheckSum();
        physicalSocket.sendFrame( serializableFrame, null );
        //sentFrames.put( serializableFrame.getFrameUUID(), new SentNetworkRequest( System.currentTimeMillis(), transmissionStatusListener ) );
    }

    @Override
    public void onArrivalOfNetworkRequest( SerializableFrame serializableFrame )
    {
        if( verifyChecksum( serializableFrame ) )
        {
            try
            {
                if( ( serializableFrame instanceof ACKFrame) ||  ( serializableFrame instanceof NAKFrame) )
                {
                    return;
                }

                if( sentFrames.containsKey( serializableFrame ) )
                {

                    sentFrames.remove(serializableFrame.getFrameUUID()).getTransmissionStatusListener().onSuccess();
                }

                if( ! receivedFrames.contains( serializableFrame ) )
                {
//                    physicalSocket.sendFrame( ACKFrame.getACKFrame( serializableFrame ) );
                   // receivedFrames.add( new MultiKey( serializableFrame.getFrameUUID(), serializableFrame.getDestinationMachine().getMachineAddress() )  );
                    networkResponseListener.onArrivalOfNetworkRequest(serializableFrame);
                }
                else
                {
                   // physicalSocket.sendFrame( ACKFrame.getACKFrame( serializableFrame ), null );
                }
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                physicalSocket.sendFrame( NAKFrame.getNAKFrame( serializableFrame ), null );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    private boolean verifyChecksum( SerializableFrame serializableFrame )
    {
        return FrameHelper.verifyCheckSum(serializableFrame);
    }

    @Override
    public void close() throws IOException
    {
        physicalSocket.close();
    }
}


class NetworkTimeoutChecker extends SDSThread implements ConfigPropertyChangeListener
{
    private Map< Long, SentNetworkRequest > sentFrames;
    private long networkTimeout = Long.parseLong( ConfigPropertiesHelper.getProperty(ConfigProperties.NETWORK_TIMEOUT) );

    public NetworkTimeoutChecker( Map<Long, SentNetworkRequest> sentFrames )
    {
        super( " Network Timeout Checker " );
        this.sentFrames = sentFrames;
    }

    @Override
    public void run()
    {
        //To maintain consistency
        long scheduledTimeStamp = System.currentTimeMillis();

        for( Map.Entry < Long, SentNetworkRequest > entry : sentFrames.entrySet() )
        {
            SentNetworkRequest sentNetworkRequest = entry.getValue();
            if ( ( scheduledTimeStamp - sentNetworkRequest.getCreatedTimeStamp() ) > networkTimeout )
            {
                sentFrames.remove( entry.getKey() ).getTransmissionStatusListener().onFailure( new SDSSocketException( "Network Timeout Occurred" ) );
            }
        }
    }

    @Override
    public void propertyChanged()
    {
        networkTimeout = Long.parseLong( ConfigPropertiesHelper.getProperty( ConfigProperties.NETWORK_TIMEOUT ) );
    }
}


class SentNetworkRequest
{
    private long createdTimeStamp;
    private TransmissionStatusListener transmissionStatusListener;

    public SentNetworkRequest( long createdTimeStamp, TransmissionStatusListener transmissionStatusListener )
    {
        this.createdTimeStamp = createdTimeStamp;
        this.transmissionStatusListener = transmissionStatusListener;
    }

    public long getCreatedTimeStamp()
    {
        return createdTimeStamp;
    }

    public TransmissionStatusListener getTransmissionStatusListener()
    {
        return transmissionStatusListener;
    }
}






