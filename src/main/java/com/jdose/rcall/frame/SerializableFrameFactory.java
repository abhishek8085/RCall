package com.jdose.rcall.frame;

import com.jdose.rcall.Machine;

/**
 * Created by abhishek.i on 2/12/2015.
 */
public interface SerializableFrameFactory
{
    public SerializableFrame getFrameInstance ( Machine destinationMachine );

    public void  initialise( FactoryProperties factoryProperties ) throws FrameFactoryException;

    public void reInitialise( FactoryProperties factoryProperties ) throws FrameFactoryException;
}
