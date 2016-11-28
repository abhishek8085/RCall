package com.jdose.rcall.frame.handshakeframe;

import com.jdose.rcall.Machine;
import com.jdose.rcall.frame.*;
import com.jdose.rcall.helper.MachineHelper;

/**
 * Created by abhishek.i on 2/19/2015.
 */
public class HandShakeFrameFactory extends AbstractSerializableFrameFactory
{
    int frameIdentifier  = FrameType.HANDSHAKE_FRAME.getFrameIdentifier();
    Machine sourceMachine = MachineHelper.getThisMachine();

    @Override
    public SerializableFrame getFrameInstance( Machine destinationMachine )
    {
        return new HandShakeFrame( FrameHelper.getFrameId(), frameIdentifier, destinationMachine, sourceMachine, System.currentTimeMillis() );
    }

    @Override
    public void initialise(FactoryProperties factoryProperties) throws FrameFactoryException {

    }

    @Override
    public void reInitialise(FactoryProperties factoryProperties) throws FrameFactoryException {

    }

/*    @Override
    public SerializableFrame getFrameInstance(FactoryProperties factoryProperties)
    {
        return new HandShakeFrame( UUID.randomUUID().toString(), FrameType.HANDSHAKE_FRAME.getFrameIdentifier(), MachineHelper.getMachineInstance(), new DateTime() );
    }

    @Override
    public void initialise() throws InitialisationException
    {

    }

    @Override
    public void reInitialise() throws ReInitialisationException 
    {

    }*/
}
