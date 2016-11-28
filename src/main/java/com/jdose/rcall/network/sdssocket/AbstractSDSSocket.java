package com.jdose.rcall.network.sdssocket;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.ReInitialisationException;
import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.frame.SerializableFrame;
import com.jdose.rcall.network.NetworkRequestListener;
import com.jdose.rcall.network.TransmissionStatusListener;

import java.io.IOException;

/**
 * Created by abhishek.i on 6/4/2015.
 */
public abstract class AbstractSDSSocket implements SDSSocket, NetworkRequestListener
{
    private SDSSocket sdsSocket;

    @Override
    public void initialise() throws InitialisationException
    {
        sdsSocket.initialise();
    }

    @Override
    public void reInitialise() throws ReInitialisationException
    {
        sdsSocket.reInitialise();
    }

    @Override
    public void close() throws IOException
    {
        sdsSocket.close();
    }

    @Override
    public Machine getBindMachine()
    {
        return sdsSocket.getBindMachine();
    }

    @Override
    public SocketType getSocketType()
    {
        return sdsSocket.getSocketType();
    }


    public void setChildSDSSocket( SDSSocket sdsSocket )
    {
        this.sdsSocket = sdsSocket;
    }


    public SDSSocket getChildSDSSocket( SDSSocket sdsSocket )
    {
        return sdsSocket;
    }

    @Override
    public void registerNetworkRequestListener( NetworkRequestListener networkResponseListener )
    {
    }

    @Override
    public void sendFrame( SerializableFrame serializableFrame, TransmissionStatusListener transmissionStatusListener) throws IOException
    {
        sdsSocket.sendFrame( serializableFrame, transmissionStatusListener );
    }
}
