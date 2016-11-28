package com.jdose.rcall.network;


import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.NetworkHelperException;
import com.jdose.rcall.frame.SerializableFrame;

/**
 * Created by abhishek.i on 1/27/2015.
 */
public class NetworkHelper
{
    public static void sendFrame( SerializableFrame frame ) throws NetworkHelperException
    {
        sendFrame( frame, null);
    }

    public static void sendFrame( SerializableFrame frame, TransmissionStatusListener transmissionStatusListener)
    {
        sendObject( frame.getDestinationMachine(), frame, null);
    }

    private static void sendObject( Machine destinationMachine, SerializableFrame serializableFrame )
    {
        sendObject( destinationMachine, serializableFrame, null);
    }

    private static void sendObject( Machine destinationMachine, SerializableFrame serializableFrame, TransmissionStatusListener transmissionStatusListener)
    {


            NetManager.sendObject( destinationMachine, serializableFrame, transmissionStatusListener );


/*        if( transmissionStatus!= null )
        {
            try
            {
                if (netManagerException == null)
                {
                    transmissionStatus.onSuccess();
                }
                else
                {
                    transmissionStatus.onFailure(netManagerException);
                }

            }
            catch ( Throwable t )
            {
                t.printStackTrace();
            }
        }*/
    }







}
