package com.jdose.rcall.handshake;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.ApplicationManager;
import com.jdose.rcall.commons.exception.NoSuchFrameTypeException;
import com.jdose.rcall.commons.exception.SDSRuntimeException;
import com.jdose.rcall.commons.version.VersionHelper;
import com.jdose.rcall.frame.FrameType;
import com.jdose.rcall.frame.FramesProvider;
import com.jdose.rcall.frame.SerializableFrame;
import com.jdose.rcall.frame.handshakeframe.HandShakeFrame;
import com.jdose.rcall.network.TransmissionStatusListener;
import com.jdose.rcall.network.sdssocket.ApplicationSocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by abhishek.i on 6/2/2015.
 */
public class HandshakeManager implements HandShakeFrameListener
{
    private final String binaryVersion = VersionHelper.getPresentBinariesVersion() ;
    private static final Logger logger = LogManager.getLogger( HandshakeManager.class );

    private  volatile boolean handshakeFrameReceived = false;
    private  volatile Throwable storedException;

    private ApplicationSocket applicationSocket;

    private Object mutex = new Object();

    public HandshakeManager( ApplicationSocket applicationSocket )
    {
        this.applicationSocket = applicationSocket;
        applicationSocket.setHandShakeFrameListener(this);
    }

    /*

    public static HandshakeManager getHandshakeManager()
    {
        if ( handshakeManager == null )
        {
            synchronized ( HandshakeManager.class )
            {
                if ( handshakeManager == null  )
                {
                    handshakeManager = new HandshakeManager();
                }
            }
        }
        return handshakeManager;
    }
*/

/*


    public void performHandshake() throws HandshakeException
    {
        if  ( ApplicationManager.isAgentMode() )
        {
            throw new SDSRuntimeException( "Agent cannot initiate handShake" );
        }

        for ( Machine machine : MachineHelper.getDestinationMachines() )
        {
            //performHandshake( machine );
        }
    }
*/


    public void performHandshake() throws HandshakeException
    {
        HandShakeFrame handShakeFrame;

        final Machine machine = applicationSocket.getBindMachine();
            try
            {
                logger.info( "Sending handshake frame from '{}' with manager version '{}'" , machine.getMachineAddress(), binaryVersion );
                handShakeFrame = FramesProvider.getProvider().getInstance( FrameType.HANDSHAKE_FRAME, machine );
            }
            catch ( NoSuchFrameTypeException e )
            {
                throw new SDSRuntimeException(e);
            }

            handShakeFrame.setVersion( binaryVersion );

        synchronized ( mutex )
        {
            try
            {
                applicationSocket.sendFrame( handShakeFrame, new TransmissionStatusListener()
                {
                    @Override
                    public void onSuccess()
                    {
                        //Transmission successful
                    }

                    @Override
                    public void onFailure( Throwable t )
                    {
                        machine.setDisabled( true );
                        machine.setDisableReason(t);
                    }
                });
            }
            catch ( IOException e )
            {
                throw new HandshakeException( e );
            }

            while ( !handshakeFrameReceived )
            {
                try
                {
                    mutex.wait();

                    if ( storedException != null )
                    {
                        throw new  HandshakeException( storedException );
                    }
                }
                catch ( InterruptedException e )
                {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onArrivalOfHandShakeFrame( SerializableFrame serializableFrame ) {
        HandShakeFrame handShakeFrame = (HandShakeFrame) serializableFrame;
        String version = handShakeFrame.getPayloadObject();


        if (ApplicationManager.isAgentMode()) {
            logger.info("Received handshake frame from '{}' with manager version '{}'", serializableFrame.getSourceMachine().getMachineAddress(), version);

            HandShakeFrame respHandShakeFrame = null;
            try {
                respHandShakeFrame = FramesProvider.getProvider().getInstance(FrameType.HANDSHAKE_FRAME, serializableFrame.getSourceMachine());
                respHandShakeFrame.setVersion( binaryVersion );
                System.out.println( binaryVersion );
                applicationSocket.sendFrame(respHandShakeFrame, new TransmissionStatusListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        throw new SDSRuntimeException(t);
                    }
                });
                return;
            } catch (Exception e) {
                throw new SDSRuntimeException(e);
            }
        }
        else
        {


            if (!VersionHelper.checkCompatibility(version)) {
                storedException = new HandshakeException("Version incompatible: agent version is '" + binaryVersion + "' and controller version is '" + version + "'");

                synchronized (mutex) {
                    handshakeFrameReceived = true;
                    mutex.notify();
                    // dataLinkSocket.unRegisterNetworkRequestListener();
                }

 /*           try
            {
                Machine machine = MachineHelper.resloveMachineAddressToMachine( serializableFrame.getDestinationMachine().getMachineAddress() );
                machine.setDisabled( true );
                machine.setDisableReason( "Version Mismatch" );
                logger.error( "Machine '{}' is disables as the agent version is '{}' and controller version is '{}' ", serializableFrame.getSourceMachine().getMachineAddress(), version );
            }
            catch ( MachineNotFoundException e )
            {
                storedException = e;
                logger.error( e );
            }*/
            } else {
                logger.info(" Received handshake frame from '{}' with manager version '{}'", version, binaryVersion );
                synchronized (mutex) {
                    handshakeFrameReceived = true;
                    mutex.notify();
                    // dataLinkSocket.unRegisterNetworkRequestListener();
                }
            }
        }
    }
}
