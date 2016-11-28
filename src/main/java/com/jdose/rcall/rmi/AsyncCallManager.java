package com.jdose.rcall.rmi;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.Initialisable;
import com.jdose.rcall.commons.ReInitialisationException;
import com.jdose.rcall.commons.ReInitialiseable;
import com.jdose.rcall.commons.Shutdownable;
import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.commons.thread.SDSThread;
import com.jdose.rcall.frame.FrameType;
import com.jdose.rcall.frame.FramesProvider;
import com.jdose.rcall.frame.SerializableFrame;
import com.jdose.rcall.frame.commandframe.RMIFrame;
import com.jdose.rcall.helper.ConfigProperties;
import com.jdose.rcall.helper.ConfigPropertiesHelper;
import com.jdose.rcall.network.NetworkHelper;
import com.jdose.rcall.network.NetworkRequestListener;
import com.jdose.rcall.network.TransmissionStatusListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * Created by abhishek.i on 5/20/2015.
 */
public class AsyncCallManager implements Initialisable, ReInitialiseable, Shutdownable, NetworkRequestListener
{
    private static ConcurrentHashMap< Long, AsyncCallBackAdapter > rmiIdVsAsyncCallMap = new ConcurrentHashMap< Long, AsyncCallBackAdapter >();
    private static ExecutorService rmiRequestExecutor = Executors.newFixedThreadPool( Integer.parseInt( ConfigPropertiesHelper.getProperty(ConfigProperties.MAX_RMI_REQUESTS) ) );

    private static AsyncCallManager asyncCallManager = new AsyncCallManager();

    private AsyncCallManager(){}

    private Logger logger = LogManager.getLogger( AsyncCallManager.class );

    public static AsyncCallManager getAsyncCallManager()
    {
        return asyncCallManager;
    }

    @Override
    public void initialise() throws InitialisationException
    {
        AsyncCallTimeOutChecker asyncCallTimeOutChecker = new AsyncCallTimeOutChecker( rmiIdVsAsyncCallMap );
        asyncCallTimeOutChecker.start();
    }

    @Override
    public void reInitialise() throws ReInitialisationException
    {

    }

    public static void makeAsyncCall( Machine destinationMachine, RMIRequest rmiRequest, AsyncCallback asyncCallback )
    {
        AsyncCallBackAdapter asyncCallBackAdapter = new AsyncCallBackAdapter( asyncCallback ) ;
        rmiIdVsAsyncCallMap.put( rmiRequest.getRmiId(), asyncCallBackAdapter );
        rmiRequestExecutor.submit( new AsyncCallAdapter( new AsyncCall( destinationMachine, rmiRequest, asyncCallback ), asyncCallBackAdapter ) );
    }


    @Override
    public void shutdown()
    {
        rmiRequestExecutor.shutdownNow();
    }

    @Override
    public void onArrivalOfNetworkRequest( SerializableFrame serializableFrame )
    {
        RMIFrame rmiFrame = ( RMIFrame ) serializableFrame;

        if ( !rmiFrame.isResponse() )
        {
            return;
        }

        RMIResponse rmiResponse = rmiFrame.getPayloadObject();

        AsyncCallBackAdapter asyncCallBackAdapter = rmiIdVsAsyncCallMap.get( rmiResponse.getRmiId() );
        AsyncCallback asyncCallBack =asyncCallBackAdapter.getAsyncCallback();

        if ( rmiResponse.getExistStatus() == RMIExecutionStatus.EXECUTION_SUCCESSFUL.existStatus  )
        {
            asyncCallBack.onSuccess( rmiResponse.getOutput() );
        }
        else
        {
            asyncCallBack.onFailure( rmiResponse.getThrowable() );
        }
    }
}



class AsyncCallAdapter implements Runnable
{
    private AsyncCall asyncCall;
    private AsyncCallScheduleListener asyncCallScheduleListener;

    public AsyncCallAdapter( AsyncCall asyncCall, AsyncCallScheduleListener asyncCallScheduleListener)
    {
        this.asyncCall = asyncCall;
        this.asyncCallScheduleListener = asyncCallScheduleListener;
    }

    @Override
    public void run()
    {
        asyncCall.doCall();
        asyncCallScheduleListener.callMade( System.currentTimeMillis() );
    }
}

class AsyncCallBackAdapter implements AsyncCallScheduleListener
{
    private AsyncCallback asyncCallback;
    private long timeStamp = 0;

    public AsyncCallBackAdapter( AsyncCallback asyncCallback  )
    {
        this.asyncCallback = asyncCallback;
    }

    @Override
    public void callMade( long timeStamp )
    {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp()
    {
        return timeStamp;
    }

    public AsyncCallback getAsyncCallback()
    {
        return asyncCallback;
    }
}

interface AsyncCallScheduleListener
{
    public void callMade( long timeStamp );
}

class AsyncCall
{
    private RMIRequest rmiRequest;
    private Machine destinationMachine;
    private AsyncCallback asyncCallback;

    public AsyncCall( Machine destinationMachine, RMIRequest rmiRequest, AsyncCallback asyncCallback )
    {
        this.rmiRequest = rmiRequest;
        this.destinationMachine = destinationMachine;
        this.asyncCallback = asyncCallback;
    }

    public void doCall()
    {
        try
        {
            RMIFrame rmiFrame = FramesProvider.getProvider().getInstance( FrameType.RMI_FRAME, destinationMachine );
            rmiFrame.setRMIRequest( rmiRequest );
            rmiFrame.setIsResponse( false );
            NetworkHelper.sendFrame(rmiFrame, new TransmissionStatusListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Throwable t) {
                    asyncCallback.onFailure(t);
                }
            });
        }
        catch ( Exception e )
        {
            asyncCallback.onFailure( e );
        }
    }
}

class AsyncCallTimeOutChecker extends SDSThread
{
    private ConcurrentHashMap< Long, AsyncCallBackAdapter > rmiIdvsAsyncCallMap;
    private long asyncTimeout = Long.parseLong( ConfigPropertiesHelper.getProperty(ConfigProperties.ASYNCH_CALL_TIMEOUT) );

    private Logger logger = LogManager.getLogger( AsyncCallTimeOutChecker.class );

    public AsyncCallTimeOutChecker( ConcurrentHashMap< Long, AsyncCallBackAdapter > rmiIdvsAsyncCallMap )
    {
        super( "AsyncCall-TimeOut-Checker" );
    }

    @Override
    public void threadWork()
    {
        while ( true  & ! isTerminateSignalSet() )
        {
            try
            {
                Thread.sleep( Long.parseLong( ConfigPropertiesHelper.getProperty( ConfigProperties.ASYNCH_CALL_TIMEOUT_CHECK_INTERVAL ) ) );
            }
            catch ( InterruptedException e )
            {
                if( !isTerminateSignalSet() )
                {
                    logger.info("Interrupted Sleep", e);
                }
            }

            long currentTimeStamp = System.currentTimeMillis();

            for ( Map.Entry< Long, AsyncCallBackAdapter > entry : rmiIdvsAsyncCallMap.entrySet() )
            {
                AsyncCallBackAdapter asyncCallBackAdapter = entry.getValue();

                if ( ( asyncCallBackAdapter.getTimeStamp() != 0 ) && ( currentTimeStamp - asyncCallBackAdapter.getTimeStamp() > asyncTimeout ) )
                {
                    rmiIdvsAsyncCallMap.remove(entry.getKey());
                    try
                    {
                        asyncCallBackAdapter.getAsyncCallback().onFailure( new RMIException("AsyncCall Time out") );
                    }
                    catch ( Throwable t )
                    {
                        //Ignore exception
                    }
                }
            }
        }
    }
    @Override
    protected void terminateSignalSet()
    {
        if ( rmiIdvsAsyncCallMap != null )
        {
            rmiIdvsAsyncCallMap.clear();
        }
    }
}