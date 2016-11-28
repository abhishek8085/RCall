package com.jdose.rcall.frame.commandframe;

import com.jdose.rcall.Machine;
import com.jdose.rcall.frame.*;
import com.jdose.rcall.helper.MachineHelper;

/**
 * Created by abhishek.i on 2/19/2015.
 */
public class RMIFrameFactory extends AbstractSerializableFrameFactory
{
    int frameIdentifier  = FrameType.RMI_FRAME.getFrameIdentifier();
    Machine sourceMachine = MachineHelper.getThisMachine();

    @Override
    public SerializableFrame getFrameInstance( Machine destinationMachine )
    {
        return new RMIFrame( FrameHelper.getFrameId(), frameIdentifier, destinationMachine, sourceMachine, System.currentTimeMillis() );
    }

    @Override
    public void initialise( FactoryProperties factoryProperties ) throws FrameFactoryException
    {
        //this.sourceMachine = factoryProperties.getPropertyValue("SOURCE_MACHINE");
    }

    @Override
    public void reInitialise( FactoryProperties factoryProperties ) throws FrameFactoryException
    {
        //this.sourceMachine = factoryProperties.getPropertyValue( "SOURCE_MACHINE" );
    }
}
