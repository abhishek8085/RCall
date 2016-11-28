package com.jdose.rcall.rmi;

import com.jdose.rcall.commons.Initialisable;
import com.jdose.rcall.commons.ReInitialisationException;
import com.jdose.rcall.commons.ReInitialiseable;
import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.frame.FrameType;
import com.jdose.rcall.frame.FramesProvider;
import com.jdose.rcall.frame.SerializableFrame;
import com.jdose.rcall.frame.commandframe.RMIFrame;
import com.jdose.rcall.network.NetworkHelper;
import com.jdose.rcall.network.NetworkRequestListener;
import com.jdose.rcall.network.TransmissionStatusListener;

/**
 * Created by abhishek.i on 5/19/2015.
 */
public class RMIService implements NetworkRequestListener, Initialisable, ReInitialiseable
{
    private RMIServlet rmiServlet;

    @Override
    public void initialise() throws InitialisationException
    {
        RMIServlet rmiServlet = new RMICommonServlet();
        registerService( rmiServlet );
    }

    @Override
    public void reInitialise() throws ReInitialisationException
    {
        RMIServlet rmiServlet = new RMICommonServlet();
        registerService( rmiServlet );
    }


    @Override
    public void onArrivalOfNetworkRequest( SerializableFrame serializableFrame )
    {

        RMIFrame rmiFrame = ( RMIFrame ) serializableFrame;

        if ( rmiFrame.isResponse() )
        {
            return;
        }

        RMIRequest rmiRequest = rmiFrame.getPayloadObject();




        RMIResponse rmiResponse = getRMIResponseTemplate( rmiRequest );
        //no null check
        rmiServlet.serviceRMI(rmiRequest, rmiResponse);


        try
        {
            RMIFrame respRmiFrame = FramesProvider.getProvider().getInstance( FrameType.RMI_FRAME, serializableFrame.getSourceMachine() );
            respRmiFrame.setRMIRequest( rmiResponse );
            respRmiFrame.setIsResponse( true );
            NetworkHelper.sendFrame(respRmiFrame, new TransmissionStatusListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Throwable t) {
                    //  e.printStackTrace();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private RMIResponse getRMIResponseTemplate( RMIRequest rmiRequest )
    {
        return new RMIResponse( rmiRequest.getRmiId() );
    }

    public void registerService( RMIServlet rmiServlet )
    {
        this.rmiServlet = rmiServlet;
    }

    public void unRegisterRMIService()
    {
        this.rmiServlet = null;
    }

}
