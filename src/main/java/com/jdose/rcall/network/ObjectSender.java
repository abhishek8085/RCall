package com.jdose.rcall.network;

import com.jdose.rcall.commons.Initialisable;
import com.jdose.rcall.commons.ReInitialisationException;
import com.jdose.rcall.commons.ReInitialiseable;
import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.frame.SerializableFrame;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by abhishek.i on 1/27/2015.
 */
public class ObjectSender implements Initialisable, ReInitialiseable
{
    private String address;
    private int port;
    private Selector selector;
    private SocketChannel channel;
    private Socket socket;
    private NetworkResponseListener networkResponseListener;

    private BlockingObjectAdapterStream in = null;
    private ObjectOutputStream out = null;

    public ObjectSender( String address, int port )
    {
        this.address = address;
        this.port = port;
    }

    public void registerNetworkResponseListener( NetworkResponseListener networkResponseListener )
    {
        this.networkResponseListener = networkResponseListener;
    }

    public void unRegisterNetworkResponseListener()
    {
        networkResponseListener = null;
    }

    public void sendObject( SerializableFrame serializableFrame ) throws IOException
    {
        out.writeObject( serializableFrame );
        out.flush();
    }

    public void close() throws IOException
    {
        socket.close();
    }

    @Override
    public void reInitialise() throws ReInitialisationException
    {

    }

    @Override
    public void initialise() throws InitialisationException
    {
        try
        {
            socket = new Socket( address, port );

            in =  new BlockingObjectAdapterStream( socket.getInputStream() );
            in.initialise();

            in.setReturnedObjectCallBack( new ReturnedObjectCallBack()
            {
                @Override
                public void returnedObject( Object object )
                {
                    onNetworkResponseArrival( ( SerializableFrame ) object );
                }
            });

            out = new ObjectOutputStream( socket.getOutputStream() );
        }
        catch (IOException e)
        {
           throw new InitialisationException( e );
        }
    }

    private void onNetworkResponseArrival( SerializableFrame serializableFrame  )
    {
        NetworkResponse networkResponse = getNetworkResponse( serializableFrame );
        notifyNetworkResponseListener( networkResponse );
    }


    private NetworkResponse getNetworkResponse( SerializableFrame serializableFrame  )
    {
        return new NetworkResponse( serializableFrame.getFrameUUID(),  serializableFrame );
    }


    private void notifyNetworkResponseListener( NetworkResponse networkResponse )
    {
        try
        {
            networkResponseListener.onArrivalOfNetworkResponse( networkResponse );
        }
        catch( Throwable t )
        {
            //Ignore exception
        }
    }









/*    @Override
    public void initialise() throws InitialisationException
    {
        try {
            selector = Selector.open();
            channel = SocketChannel.open();
            channel.configureBlocking(false);

            channel.register(selector, SelectionKey.OP_CONNECT);
            channel.connect(new InetSocketAddress(address, port));

            selector.select( 1000 );

            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

            while (keys.hasNext()){
                SelectionKey key = keys.next();
                keys.remove();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isConnectable()){
                    System.out.println("I am connected to the server");
                    connect(key);
                }
                if (key.isWritable()){
                    write(key);
                }
                if (key.isReadable()){
                    read(key);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
;

    }*/








}



class BlockingObjectAdapterStream implements Initialisable
{
    private InputStream inputStream;
    private ReturnedObjectCallBack returnedObjectCallBack;
    private ObjectInputStream objectInputStream ;


    public BlockingObjectAdapterStream( InputStream inputStream )
    {
        this.inputStream = inputStream;
    }

    @Override
    public void initialise() throws InitialisationException
    {
/*        try
        {
            objectInputStream = new ObjectInputStream( inputStream );
        }
        catch (IOException e)
        {
            throw new InitialisationException( e );
        }*/
    }

    public void setReturnedObjectCallBack( ReturnedObjectCallBack returnedObjectCallBack )
    {
        this.returnedObjectCallBack = returnedObjectCallBack;
    }

    private class CallBackThread extends Thread
    {
        @Override
        public void run()
        {
            while ( true )
            {
                try
                {
                    if ( inputStream.available()!= 0 )
                    {
                        objectInputStream = new ObjectInputStream(inputStream);
                        returnedObjectCallBack.returnedObject(objectInputStream.readObject());
                    }
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
                catch ( ClassNotFoundException e )
                {
                    e.printStackTrace();
                }

            }

        }
    }
}

interface ReturnedObjectCallBack < T >
{
    public void returnedObject( T object );
}