package com.jdose.rcall.network.sdssocket;

import com.jdose.rcall.Machine;
import com.jdose.rcall.network.sdssocket.clientsocket.SDSClientSocket;
import com.jdose.rcall.network.sdssocket.serversocket.SDSServerSocket;

/**
 * Created by abhishek.i on 5/26/2015.
 */
public class SDSSocketBuilder
{
    public static ApplicationSocket getClientSocket( Machine remoteMachine ) throws SocketProviderException
    {
        try
        {
            ApplicationSocket applicationSocket = new ApplicationSocket( new DataLinkSocket( new SDSClientSocket( remoteMachine ) ) );
            applicationSocket.initialise();
            return applicationSocket;
        }
        catch ( Exception e )
        {
            throw new SocketProviderException( e );
        }
    }

    public static ApplicationSocket getServerSocket( Machine localMachine ) throws SocketProviderException
    {
        try
        {
            ApplicationSocket applicationSocket = new ApplicationSocket( new DataLinkSocket( new SDSServerSocket( localMachine ) ) );
            applicationSocket.initialise();
            return applicationSocket;
        }
        catch ( Exception e )
        {
            throw  new SocketProviderException( e );
        }
    }
}
