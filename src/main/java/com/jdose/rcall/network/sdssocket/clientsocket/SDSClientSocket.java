package com.jdose.rcall.network.sdssocket.clientsocket;

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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by abhishek.i on 5/26/2015.
 */
public class SDSClientSocket implements SDSSocket
{
    private Socket clientSocket;

    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    private NetworkRequestListener networkRequestListener;

    private ObjectReceiverThread objectReceiverThread;

    private Machine machine;
    private String remoteAddress;
    private int port;

    private static final Logger logger = LogManager.getLogger( SDSClientSocket.class );

    public SDSClientSocket( Machine machine )
    {
        this.machine = machine;
        this.remoteAddress = machine.getMachineAddress();
        this.port = machine.getMachinePort();
    }

    @Override
    public void initialise() throws InitialisationException
    {
        try
        {
            clientSocket = new Socket( remoteAddress, port  );
            clientSocket.setTrafficClass( 0x04 );

            logger.info("Connected to '{}' on port  '{}'", remoteAddress.toString(), port);

            dataInputStream = new DataInputStream( clientSocket.getInputStream() );
            dataOutputStream = new DataOutputStream( clientSocket.getOutputStream() );

            objectReceiverThread = new ObjectReceiverThread();
            objectReceiverThread.start();
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
            if( !clientSocket.isClosed() )
            {
                objectOutputStream.close();
                clientSocket.close();
            }

            clientSocket = new Socket( remoteAddress, port  );
            clientSocket = new Socket( remoteAddress, port  );
            objectOutputStream = new ObjectOutputStream( clientSocket.getOutputStream() );
            objectInputStream = new ObjectInputStream( clientSocket.getInputStream() );
            ObjectReceiverThread objectReceiverThread = new ObjectReceiverThread();
            objectReceiverThread.start();
        }
        catch ( IOException e )
        {
            throw new InitialisationException( e );
        }*/
    }

    @Override
    public SocketType getSocketType()
    {
        return SocketType.CLIENT_SOCKET;
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
            synchronized ( this )
            {
                byte[] bytes = CODEC.encodeFrame(serializableFrame);
                dataOutputStream.write(bytes);
                dataOutputStream.flush();
            }
        }
        catch ( EncodingException e )
        {
            new IOException( e );
        }
    }

    @Override
    public void registerNetworkRequestListener( NetworkRequestListener networkResponseListener )
    {
        this.networkRequestListener = networkResponseListener;
    }

    @Override
    public void close() throws IOException
    {
        objectReceiverThread.terminate();
        clientSocket.close();
    }

    private class ObjectReceiverThread extends SDSThread
    {
        public ObjectReceiverThread( )
        {
            super( "Client-Socket-Object-Receiver-Thread" );
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
                        logger.error( e );
                    }
                }
            }
            catch ( SocketException e )
            {
                logger.error( e );
            }
            catch ( IOException e )
            {
                logger.error( e );
            }

            finally
            {
                try
                {
                    dataInputStream.close();
                }
                catch ( IOException e )
                {
                    logger.warn("Unable to close Data Input Stream", e);
                }

/*                try
                {
                    dataOutputStream.close();
                }
                catch ( IOException e )
                {
                    logger.warn("Unable to close Data Output Stream", e);
                }*/
            }
        }

        @Override
        protected void terminateSignalSet()
        {
            logger.info( "Terminating Client-Socket-Object-Receiver-Thread" );

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

        private void onNetworkRequestArrival( SerializableFrame serializableFrame )
        {
            networkRequestListener.onArrivalOfNetworkRequest(serializableFrame);
        }
    }
}
