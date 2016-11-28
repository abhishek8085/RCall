package com.jdose.rcall.network;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.Initialisable;
import com.jdose.rcall.commons.ReInitialisationException;
import com.jdose.rcall.commons.ReInitialiseable;
import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.commons.exception.NoSuchHostConfiguredException;
import com.jdose.rcall.helper.MachineHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by abhishek.i on 1/27/2015.
 */
public class ObjectSenderProvider implements Initialisable, ReInitialiseable, NetworkResponseListener
{

    private Map <Machine,ObjectSender> hostAddressSocketMap = new ConcurrentHashMap <Machine,ObjectSender>();
    private List< NetworkResponseListener > networkResponseListeners = new ArrayList< NetworkResponseListener >();


    @Override
    public void initialise() throws InitialisationException
    {
        for ( Machine machine : MachineHelper.getDestinationMachines() )
        {
            try
            {
                ObjectSender osr = getSenderReceiver( machine.getMachineAddress(), machine.getMachinePort() );
                osr.initialise();
                osr.registerNetworkResponseListener( this );
                hostAddressSocketMap.put( machine, osr );
            }
            catch (IOException e)
            {
                machine.setDisabled( true );
                throw new InitialisationException( e );
            }
        }

    }

    @Override
    public void reInitialise() throws ReInitialisationException
    {
        for ( Map.Entry<Machine, ObjectSender> entry : hostAddressSocketMap.entrySet() )
        {
            try
            {
                entry.getValue().close();
            }
            catch (IOException e)
            {
                throw new InitialisationException( e );
            }
        }

        initialise();
    }


    public ObjectSender get( Machine machine ) throws NoSuchHostConfiguredException
    {
        if ( hostAddressSocketMap.get( machine ) != null )
        {
            return hostAddressSocketMap.get( machine );
        }

        throw new NoSuchHostConfiguredException();
    }


    private ObjectSender getSenderReceiver ( String address, int port ) throws IOException
    {
        return new ObjectSender( address, port );
    }

    public void registerNetworkResponseListener( NetworkResponseListener networkResponseListener )
    {
        networkResponseListeners.add( networkResponseListener );
    }

    public void unRegisterNetworkResponseListener( NetworkResponseListener networkResponseListener )
    {
        networkResponseListeners.remove( networkResponseListener );
    }


    @Override
    public void onArrivalOfNetworkResponse( NetworkResponse networkResponse )
    {
        try
        {
            for ( NetworkResponseListener networkResponseListener : networkResponseListeners )
            {
                networkResponseListener.onArrivalOfNetworkResponse( networkResponse );
            }
        }
        catch( Throwable t )
        {

        }
    }
}
