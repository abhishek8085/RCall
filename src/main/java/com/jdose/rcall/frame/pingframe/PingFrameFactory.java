package com.jdose.rcall.frame.pingframe;

import com.jdose.rcall.Machine;
import com.jdose.rcall.frame.AbstractSerializableFrameFactory;
import com.jdose.rcall.frame.FactoryProperties;
import com.jdose.rcall.frame.FrameFactoryException;
import com.jdose.rcall.frame.SerializableFrame;

/**
 * Created by abhishek.i on 2/12/2015.
 */
public class PingFrameFactory extends AbstractSerializableFrameFactory
{
    @Override
    public SerializableFrame getFrameInstance(Machine destinationMachine) {
        return null;
    }

    @Override
    public void initialise(FactoryProperties factoryProperties) throws FrameFactoryException {

    }

    @Override
    public void reInitialise(FactoryProperties factoryProperties) throws FrameFactoryException {

    }
/*    private FactoryProperties factoryProperties;

    @Override
    public void initialise() throws InitialisationException
    {

    }

    @Override
    public void reInitialise() throws ReInitialisationException
    {

    }


    @Override
    public SerializableFrame getFrameInstance( FactoryProperties factoryProperties )
    {
        return new PingFrame( UUID.randomUUID().toString(), FrameType.PING_FRAME.getFrameIdentifier(), MachineHelper.getMachineInstance(), new DateTime() );
    }*/


}