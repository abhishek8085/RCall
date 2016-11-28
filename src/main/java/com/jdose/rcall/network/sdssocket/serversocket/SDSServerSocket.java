package com.jdose.rcall.network.sdssocket.serversocket;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.ReInitialisationException;
import com.jdose.rcall.commons.exception.DecodingException;
import com.jdose.rcall.commons.exception.EncodingException;
import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.commons.thread.SDSThread;
import com.jdose.rcall.frame.SerializableFrame;
import com.jdose.rcall.network.CODEC;
import com.jdose.rcall.network.NetworkRequestListener;
import com.jdose.rcall.network.TransmissionStatusListener;
import com.jdose.rcall.network.sdssocket.SDSSocket;
import com.jdose.rcall.network.sdssocket.SocketType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by abhishek.i on 1/28/2015.
 */
public class SDSServerSocket implements SDSSocket
{
    private ServerSocket serverSocket;

    private NetworkRequestListener networkResponseListener;


    private int port;
    private int backLog;
    private Machine machine;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    private SocketConnectionListenerThread socketConnectionListenerThread;

    private static final Logger logger = LogManager.getLogger( SDSServerSocket.class );

    public SDSServerSocket( Machine machine )
    {
        this( machine.getMachinePort(), 1 );
        this.machine = machine;
    }

    private SDSServerSocket( int port, int backLog )
    {
        this.port = port;
        this.backLog = backLog;
    }

    /**
     * ObjectReceiverThread is started by SocketConnectionListenerThread
     *  when new connection is received.
     * @throws InitialisationException
     */
    @Override
    public void initialise() throws InitialisationException
    {
        try
        {
            ObjectReceiverThread objectReceiverThread = new ObjectReceiverThread();
            serverSocket = new ServerSocket( port, backLog, InetAddress.getLocalHost() );
            socketConnectionListenerThread = new SocketConnectionListenerThread( objectReceiverThread );
            socketConnectionListenerThread.start();
        }
        catch ( IOException e )
        {
            throw new InitialisationException( e );
        }
    }

    @Override
    public void reInitialise() throws ReInitialisationException
    {
/*        try
        {
            socketConnectionListenerThread.setStopSignal( true );
            socketConnectionListenerThread.join();
            serverSocket = new ServerSocket( port, backLog, InetAddress.getLocalHost());
        }
        catch ( IOException | InterruptedException e )
        {
            e.printStackTrace();
        }*/
    }

    @Override
    public void registerNetworkRequestListener( NetworkRequestListener networkResponseListener )
    {
        this.networkResponseListener = networkResponseListener;
    }

    public void unRegisterNetworkRequestListener()
    {
        networkResponseListener = null;
    }

    @Override
    public SocketType getSocketType()
    {
        return SocketType.SERVER_SOCKET;
    }

    @Override
    public Machine getBindMachine()
    {
        return machine;
    }

    @Override
    public void sendFrame( SerializableFrame serializableFrame, TransmissionStatusListener transmissionStatusListener ) throws IOException
    {
        try
        {
            byte[] bytes = CODEC.encodeFrame(serializableFrame);

            synchronized ( this )
            {
                dataOutputStream.write(bytes);
                dataOutputStream.flush();
            }
        }
        catch ( EncodingException e )
        {
            throw new IOException( e );
        }
    }

    @Override
    public void close() throws IOException
    {
        socketConnectionListenerThread.terminate();
    }

    private class ObjectReceiverThread extends SDSThread implements ServerSocketConnectionListener
    {
        private DisconnectListener disconnectListener;

        public ObjectReceiverThread()
        {
            super( "Server-Socket-Object-Receiver-Thread" );
        }

        public void registerServerSocketExceptionListener( DisconnectListener disconnectListener )
        {
            this.disconnectListener = disconnectListener;
        }

        @Override
        public void run()
        {
            try
            {
                while ( true && !isTerminateSignalSet() )
                {
                    try
                    {
                        byte[] byteArray = new byte[ dataInputStream.readInt() ];
                        dataInputStream.readFully( byteArray );
                        onNetworkRequestArrival( CODEC.decode( byteArray ) );
                    }
                    catch( DecodingException e )
                    {
                        logger.error( "Error decoding Frame", e );
                    }
                }
            }
            catch( SocketException e )
            {
                logger.error( e );
                disconnectListener.onDisconnect();
            }
            catch ( IOException e )
            {
                logger.error( e );
                disconnectListener.onDisconnect();
            }
            finally
            {
                try
                {
                    dataInputStream.close();
                }
                catch ( IOException e )
                {
                    logger.warn( "Unable to close Data Input Stream", e );
                }

                try
                {
                    dataOutputStream.close();
                }
                catch ( IOException e )
                {
                    logger.warn( "Unable to close Data Output Stream", e );
                }
            }
        }

        @Override
        protected void terminateSignalSet()
        {
            logger.info( "Terminating Server-Socket-Object-Receiver-Thread" );
            try
            {
                dataInputStream.close();
            }
            catch ( IOException e )
            {
                logger.warn( "Unable to close Data Input Stream", e );
            }

            try
            {
                dataOutputStream.close();
            }
            catch ( IOException e )
            {
                logger.warn( "Unable to close Data Output Stream", e );
            }
        }

        @Override
        public void onConnection( DisconnectListener disconnectListener, ServerConnectionObject serverConnectionObject )
        {
            if ( this.isAlive() )
            {
                this.terminate();
                try
                {
                    this.join();
                }
                catch ( InterruptedException e )
                {
                    logger.warn( e );
                }
            }

            dataInputStream = new DataInputStream( serverConnectionObject.getInputStream() );
            dataOutputStream = new DataOutputStream( serverConnectionObject.getOutputStream() );

            ObjectReceiverThread objectReceiverThread =  new ObjectReceiverThread ();
            objectReceiverThread.registerServerSocketExceptionListener( disconnectListener );
            objectReceiverThread.start();
        }
    }


    private void onNetworkRequestArrival( SerializableFrame serializableFrame  )
    {
        notifyNetworkRequestListener( serializableFrame );
    }


    private void notifyNetworkRequestListener( SerializableFrame serializableFrame )
    {
        try
        {
            networkResponseListener.onArrivalOfNetworkRequest( serializableFrame );
        }
        catch( Throwable t )
        {
            //Ignore exception
            t.printStackTrace();
        }
    }


    private class SocketConnectionListenerThread extends SDSThread implements DisconnectListener
    {
        private ServerSocketConnectionListener serverSocketConnectionListener;
        private boolean connectionTerminated = true;


        public SocketConnectionListenerThread( ServerSocketConnectionListener serverSocketConnectionListener )
        {
            super( "Server-Socket-Connection-Listener-Thread" );
            this.serverSocketConnectionListener = serverSocketConnectionListener;
        }


        public void terminateSignalSet()
        {
            logger.info( "Terminating Server-Socket-Connection-Listener-Thread" );
            ( ( ObjectReceiverThread ) serverSocketConnectionListener ).terminate();
            try
            {
                if (  ( serverSocket != null ) && !( serverSocket.isClosed() ) )
                {
                    serverSocket.close();
                }
            }
            catch ( IOException e )
            {
                logger.warn( "Unable to close socket", e );
            }
        }

        @Override
        public void threadWork()
        {
            Socket server = null;

            try
            {
                while ( true )
                {
                    if ( ( server == null ) || connectionTerminated )
                    {
                        logger.info( "Listening for connection on port '{}' ", port );
                    }

                    Socket tempServer = serverSocket.accept();

                    if ( server != null && !connectionTerminated )
                    {
                        logger.info( "Declined connection from host '{}' ",tempServer.getInetAddress().getHostAddress() );
                        tempServer.close();
                        continue;
                    }

                    connectionTerminated = false;

                    server = tempServer;
                    server.setTrafficClass( 0x04 );

                    serverSocketConnectionListener.onConnection( this, new ServerConnectionObject( server.getInputStream() ,  server.getOutputStream()  ) );
                    logger.info("Accepted connection from host '{}' on port '{}'", tempServer.getInetAddress().getHostAddress(), tempServer.getLocalPort());
                }
            }
            catch ( IOException e )
            {
                if( isTerminateSignalSet() )
                {
                    //do nothing
                }
                else
                {
                    logger.error( e );
                }
            }
            finally
            {
                if ( server != null || !server.isClosed() )
                {
                    try
                    {
                        server.close();
                    }
                    catch ( IOException e )
                    {
                        logger.warn("Unable to close socket", e);
                    }
                }
            }
        }

        @Override
        public void onDisconnect()
        {
            // fix this
            logger.info( "Listening for connection on port '{}' ", port );
            connectionTerminated = true;
        }
    }
}

interface ServerSocketConnectionListener
{
    void onConnection( DisconnectListener disconnectListener, ServerConnectionObject serverConnectionObject);
}

interface DisconnectListener
{
    void onDisconnect();
}

class ServerConnectionObject
{
    private InputStream inputStream;
    private OutputStream outputStream;

    public ServerConnectionObject( InputStream inputStream, OutputStream outputStream )
    {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public InputStream getInputStream()
    {
        return inputStream;
    }

    public OutputStream getOutputStream()
    {
        return outputStream;
    }
}