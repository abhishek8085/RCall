package com.jdose.rcall.network;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.ReInitialisationException;
import com.jdose.rcall.commons.ReInitialiseable;
import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.commons.exception.SDSRuntimeException;
import com.jdose.rcall.frame.FrameType;
import com.jdose.rcall.frame.SerializableFrame;
import com.jdose.rcall.helper.MachineHelper;
import com.jdose.rcall.network.sdssocket.ApplicationSocket;
import com.jdose.rcall.network.sdssocket.SDSSocketBuilder;
import com.jdose.rcall.network.sdssocket.SocketProviderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by abhishek.i on 5/26/2015.
 */
public class NetManager implements NetworkRequestListener
{
    private NetManager(){};
    private static boolean isInitialised = false;
    private static NetManager netManager;
    private static SocketProvider socketProvider;
    private Map< Integer, NetworkRequestListenWorker > frameTypeVsNetworkRequestListenWorker = new HashMap< Integer, NetworkRequestListenWorker >();
    private static final Logger logger = LogManager.getLogger( NetManager.class );


    public static NetManager getManager()
    {
        if( isInitialised )
        {
            return netManager;
        }

        throw new SDSRuntimeException( "Net manager is not initialised." );
    }


    public static synchronized void initialise( boolean serverMode ) throws InitialisationException
    {
        if( isInitialised )
        {
            throw new InitialisationException( "Has already initialised." );
        }

        if (netManager == null )
        {
            netManager = new NetManager();
            socketProvider = new SocketProvider( netManager );
            socketProvider.initialise( serverMode );
        }
        isInitialised = true;
    }

    public static void sendObject( Machine machine, SerializableFrame serializableFrame,TransmissionStatusListener transmissionStatusListener )
    {
        try
        {
            socketProvider.getSocket( machine ).sendFrame( serializableFrame, transmissionStatusListener );
        }
        catch ( IOException e )
        {
            transmissionStatusListener.onFailure( e );
        }
    }




    public void registerNetworkRequestListener( FrameType frameType, NetworkRequestListener networkResponseListener )
    {
        NetworkRequestListenWorker networkRequestListenWorker;

        if ( ( networkRequestListenWorker = frameTypeVsNetworkRequestListenWorker.get( frameType.getFrameIdentifier() ) ) != null )
        {
            networkRequestListenWorker.addNetworkRequestListenWorkers( networkResponseListener );
        }
        else
        {
            NetworkRequestListenWorker newNetworkRequestListenWorker = new NetworkRequestListenWorker();
            newNetworkRequestListenWorker.addNetworkRequestListenWorkers( networkResponseListener );
            frameTypeVsNetworkRequestListenWorker.put( frameType.getFrameIdentifier(), newNetworkRequestListenWorker );
        }
    }

    public void unRegisterNetworkRequestListener( FrameType frameType )
    {
        frameTypeVsNetworkRequestListenWorker.remove( frameType.getFrameIdentifier() );
    }


    @Override
    public void onArrivalOfNetworkRequest( SerializableFrame serializableFrame )
    {
        if ( frameTypeVsNetworkRequestListenWorker.get( serializableFrame.getFrameIdentifier() ) == null )
        {
            return;
        }

        frameTypeVsNetworkRequestListenWorker.get( serializableFrame.getFrameIdentifier() ).onArrivalOfNetworkRequest( serializableFrame );
    }
}


class SocketProvider implements  ReInitialiseable
{
    private Map< Machine, ApplicationSocket > machineVsApplicationSocketMap = new HashMap< Machine, ApplicationSocket >();
    private static final Logger logger = LogManager.getLogger( SocketProvider.class );

    NetworkRequestListener networkRequestListener;

    public SocketProvider( NetworkRequestListener networkRequestListener )
    {
        this.networkRequestListener = networkRequestListener;
    }


    public void initialise( boolean serverMode ) throws InitialisationException
    {
        if( serverMode )
        {
            Machine localMachine = MachineHelper.getThisMachine();
            ApplicationSocket applicationServerSocket = null;

            try
            {
                applicationServerSocket = SDSSocketBuilder.getServerSocket(localMachine);
            }
            catch ( SocketProviderException e )
            {
                throw new InitialisationException( e );
            }

            applicationServerSocket.registerNetworkRequestListener( networkRequestListener );
            machineVsApplicationSocketMap.put( localMachine, applicationServerSocket );
        }
        else
        {
            for ( Machine machine : MachineHelper.getDestinationMachines() )
            {
                ApplicationSocket applicationSocket = null;

                try
                {
                    applicationSocket = SDSSocketBuilder.getClientSocket( machine );
                    applicationSocket.registerNetworkRequestListener( networkRequestListener );
                    machineVsApplicationSocketMap.put( machine, applicationSocket );
                }
                catch ( SocketProviderException e )
                {
                    machine.setDisabled( true );
                    machine.setDisableReason(e);
                    logger.error("**********************************************************************************");
                    logger.error("Machine '{}' disable because of following reason :", machine.getMachineAddress());
                    logger.error( e );
                    logger.error("**********************************************************************************");
                }
            }
        }

    }

    @Override
    public void reInitialise() throws ReInitialisationException
    {
        machineVsApplicationSocketMap.clear();

        //initialise( );
    }

    public ApplicationSocket getSocket( Machine machine )
    {
        return machineVsApplicationSocketMap.get( machine );
    }
}


class NetworkRequestListenWorker implements NetworkRequestListener
{
    private BlockingQueue<SerializableFrame> queue = new LinkedBlockingQueue< SerializableFrame >();
    private List< NetworkRequestListener > networkRequestListenerList = new ArrayList< NetworkRequestListener >();
    private ListenWorkerThread listenWorkerThread;

    public NetworkRequestListenWorker()
    {
        listenWorkerThread = new ListenWorkerThread( networkRequestListenerList, queue );
        listenWorkerThread.start();
    }

    public void addNetworkRequestListenWorkers( NetworkRequestListener networkResponseListener )
    {
        networkRequestListenerList.add(networkResponseListener );
    }

    @Override
    public void onArrivalOfNetworkRequest( SerializableFrame serializableFrame )
    {
        try
        {
            queue.put( serializableFrame );
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

class ListenWorkerThread extends Thread
{
    private BlockingQueue< SerializableFrame > queue;
    private List< NetworkRequestListener > networkRequestListenerList;



    public ListenWorkerThread( List< NetworkRequestListener > networkRequestListenerList, BlockingQueue<SerializableFrame> queue )
    {
        super( "ListenerWorkerThread" );
        this.networkRequestListenerList = networkRequestListenerList;
        this.queue = queue;
    }

    @Override
    public void run()
    {
        while ( true )
        {
            try
            {
                SerializableFrame serializableFrame = queue.take();
                for ( final NetworkRequestListener networkRequestListener : networkRequestListenerList )
                {
                    networkRequestListener.onArrivalOfNetworkRequest( serializableFrame );
                }

            }
            catch ( InterruptedException e )
            {
                e.printStackTrace();
            }
            catch (Throwable t)
            {
                t.printStackTrace();
                //ignore exception
            }
        }
    }
}
