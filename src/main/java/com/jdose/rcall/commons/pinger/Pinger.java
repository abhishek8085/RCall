/*
package com.subex.connectiontester.commons.pinger;

import com.subex.connectiontester.Machine;
import com.subex.connectiontester.commons.Initialisable;
import com.subex.connectiontester.commons.NetworkHelperException;
import com.subex.connectiontester.commons.ReInitialisationException;
import com.subex.connectiontester.commons.ReInitialiseable;
import com.subex.connectiontester.commons.exception.InitialisationException;
import com.subex.connectiontester.commons.exception.NoSuchFrameTypeException;
import com.subex.connectiontester.commons.exception.PingerException;
import com.subex.connectiontester.commons.frame.FramesProvider;
import com.subex.connectiontester.commons.frame.pingframe.PingFrame;
import com.subex.connectiontester.commons.network.NetworkHelper;
import com.subex.connectiontester.commons.network.NetworkResponse;
import com.subex.connectiontester.commons.network.TransmissionStatusListener;

*/
/**
 * Created by abhishek.i on 1/23/2015.
 *//*

public class Pinger implements Initialisable, ReInitialiseable
{
    private Machine machine;
    private  PingFrame pingFrame;
    private int sizeOfPayload;
    private int sequenceNumber = 0;

    public Pinger( Machine machine, int sizeOfPayload )
    {
        this.machine = machine;
        this.sizeOfPayload = sizeOfPayload;
    }

    @Override
    public void initialise() throws InitialisationException
    {
*/
/*        try
        {
          //  pingFrame = ( PingFrame ) FramesProvider.getProvider().getInstance( "PingFrame" );
         //   pingFrame.setDestinationMachine( machine );
        }
        catch (NoSuchFrameTypeException e)
        {
            throw new InitialisationException( e );
        }*//*

    }


    public void ping() throws PingerException
    {
        if( machine.isDisabled() )
        {
            return;
        }

        // update sequence regardless of success or failure.
        pingFrame.setSequenceNumber( sequenceNumber++ );

        try
        {
            NetworkHelper.sendFrame( pingFrame, new TransmissionStatusListener < NetworkResponse > ()
            */
/**//*
{
                @Override
                public void onSuccess( NetworkResponse networkAcknowledgement )
                {

                }

                @Override
                public void onFailure( Throwable e )
                {

                }
            });
        }
        catch ( NetworkHelperException e )
        {
            e.printStackTrace();
        }
    }



    @Override
    public void reInitialise() throws ReInitialisationException
    {

    }
}
*/
