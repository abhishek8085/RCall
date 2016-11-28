package com.jdose.rcall.network.sdssocket;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.Initialisable;
import com.jdose.rcall.commons.ReInitialiseable;
import com.jdose.rcall.frame.SerializableFrame;
import com.jdose.rcall.network.NetworkRequestListener;
import com.jdose.rcall.network.TransmissionStatusListener;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by abhishek.i on 6/4/2015.
 */
public interface SDSSocket extends Initialisable, ReInitialiseable, Closeable
{
    SocketType getSocketType();

    Machine getBindMachine();

    void sendFrame( SerializableFrame serializableFrame, TransmissionStatusListener transmissionStatusListener ) throws IOException;

    void registerNetworkRequestListener( NetworkRequestListener networkResponseListener );
}
