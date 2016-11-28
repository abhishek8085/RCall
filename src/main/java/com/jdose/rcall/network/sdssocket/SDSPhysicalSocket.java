package com.jdose.rcall.network.sdssocket;

import com.jdose.rcall.commons.Initialisable;
import com.jdose.rcall.commons.ReInitialiseable;
import com.jdose.rcall.frame.SerializableFrame;
import com.jdose.rcall.network.NetworkRequestListener;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by abhishek.i on 5/26/2015.
 */
public interface SDSPhysicalSocket extends Initialisable, ReInitialiseable, Closeable
{
    void sendFrame( SerializableFrame serializableFrame ) throws IOException;

    void registerNetworkRequestListener( NetworkRequestListener networkResponseListener );
}
