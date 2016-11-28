package com.jdose.rcall.network.sdssocket;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.ApplicationManager;
import com.jdose.rcall.commons.ReInitialisationException;
import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.frame.FrameType;
import com.jdose.rcall.frame.SerializableFrame;
import com.jdose.rcall.frame.handshakeframe.HandShakeFrame;
import com.jdose.rcall.handshake.HandShakeFrameListener;
import com.jdose.rcall.handshake.HandshakeException;
import com.jdose.rcall.handshake.HandshakeManager;
import com.jdose.rcall.network.NetworkRequestListener;
import com.jdose.rcall.network.TransmissionStatusListener;

import java.io.IOException;

/**
 * Created by abhishek.i on 5/12/2015.
 */
public class ApplicationSocket implements SDSSocket, NetworkRequestListener
{

    private int port;
    private DataLinkSocket dataLinkSocket;
    private NetworkRequestListener networkResponseListener;
    private HandshakeManager handshakeManager;
    private HandShakeFrameListener handShakeFrameListener;

    //private Map< Integer, NetworkRequestListenWorker > frameTypeVsNetworkRequestListenWorker = new HashMap< Integer, NetworkRequestListenWorker >();


    public ApplicationSocket( DataLinkSocket dataLinkSocket )
    {
        this.dataLinkSocket = dataLinkSocket;


    }

    @Override
    public void initialise() throws InitialisationException
    {
        dataLinkSocket.initialise();
        dataLinkSocket.registerNetworkRequestListener(this);

        if ( !ApplicationManager.isAgentMode() )
        {
            try
            {
                handshakeManager = new HandshakeManager ( this ) ;
                handshakeManager.performHandshake();
            }
            catch ( HandshakeException e )
            {
                throw new InitialisationException( e );
            }
        }
        else
        {
            handshakeManager = new HandshakeManager ( this ) ;
        }
    }

    public HandShakeFrameListener getHandShakeFrameListener()
    {
        return handShakeFrameListener;
    }

    public void setHandShakeFrameListener( HandShakeFrameListener handShakeFrameListener )
    {
        this.handShakeFrameListener = handShakeFrameListener;
    }

    @Override
    public SocketType getSocketType()
    {
        return dataLinkSocket.getSocketType();
    }

    @Override
    public Machine getBindMachine()
    {
        return dataLinkSocket.getBindMachine();
    }

    @Override
    public void registerNetworkRequestListener(  NetworkRequestListener networkResponseListener )
    {
        this.networkResponseListener = networkResponseListener;
    }

    public void unRegisterNetworkRequestListener( FrameType frameType )
    {
        this.networkResponseListener = null;
    }

    @Override
    public void reInitialise() throws ReInitialisationException
    {

    }

    @Override
    public void onArrivalOfNetworkRequest( SerializableFrame serializableFrame )
    {
        if ( !( serializableFrame instanceof HandShakeFrame) )
        {
            networkResponseListener.onArrivalOfNetworkRequest(serializableFrame);
        }
        else
        {
            handShakeFrameListener.onArrivalOfHandShakeFrame( serializableFrame );
        }
    }

    public void sendFrame( SerializableFrame serializableFrame, TransmissionStatusListener transmissionStatusListener ) throws IOException
    {
        dataLinkSocket.sendFrame( serializableFrame, transmissionStatusListener );
    }

    @Override
    public void close() throws IOException
    {
        dataLinkSocket.close();
    }
}
