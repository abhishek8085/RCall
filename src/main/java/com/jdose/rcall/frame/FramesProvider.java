package com.jdose.rcall.frame;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.Initialisable;
import com.jdose.rcall.commons.ReInitialisationException;
import com.jdose.rcall.commons.ReInitialiseable;
import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.commons.exception.NoSuchFrameTypeException;
import com.jdose.rcall.frame.commandframe.RMIFrameFactory;
import com.jdose.rcall.frame.handshakeframe.HandShakeFrameFactory;
import com.jdose.rcall.frame.pingframe.PingFrameFactory;

/**
 * Created by abhishek.i on 1/23/2015.
 */
public class FramesProvider implements Initialisable, ReInitialiseable
{
    private static FramesProvider framesProvider;

    private FramesProvider(){}

    public synchronized static FramesProvider getProvider()
    {
        if ( framesProvider == null)
        {
            framesProvider = new FramesProvider();
        }

        return framesProvider;
    }


    @Override
    public void initialise() throws InitialisationException
    {
        initialiseFactoriesLookUp();
        FrameFactoryLookupRegister.initialiseAllFrameFactories();
    }


    @Override
    public void reInitialise() throws ReInitialisationException
    {
        FrameFactoryLookupRegister.unregisterAllFrameFactory();
        initialiseFactoriesLookUp();
        initialise();
    }


    public void initialiseFactoriesLookUp()
    {
        FrameFactoryLookupRegister.registerFrameFactory( new SerializableFrameFactoryLookUpObject( FrameType.PING_FRAME.getFrameIdentifier(), new PingFrameFactory() ) );
        FrameFactoryLookupRegister.registerFrameFactory( new SerializableFrameFactoryLookUpObject( FrameType.HANDSHAKE_FRAME.getFrameIdentifier(), new HandShakeFrameFactory() ) );
        FrameFactoryLookupRegister.registerFrameFactory( new SerializableFrameFactoryLookUpObject( FrameType.RMI_FRAME.getFrameIdentifier(), new RMIFrameFactory() ) );
    }


    public synchronized < T extends AbstractFrame > T getInstance( FrameType frameType, Machine destinationMachine ) throws NoSuchFrameTypeException
    {
        SerializableFrameFactory serializableFrameFactory;

        if ( ( serializableFrameFactory =  FrameFactoryLookupRegister.getFrameFactory( frameType.getFrameIdentifier() )  ) != null  )
        {
            return ( T ) serializableFrameFactory.getFrameInstance( destinationMachine );
        }

        throw new NoSuchFrameTypeException();
    }
}



class SerializableFrameFactoryLookUpObject
{
    private int frameType;
    private AbstractSerializableFrameFactory abstractSerializableFrameFactory;

    public SerializableFrameFactoryLookUpObject( int frameType, AbstractSerializableFrameFactory abstractSerializableFrameFactory)
    {
        this.frameType = frameType;
        this.abstractSerializableFrameFactory = abstractSerializableFrameFactory;
    }

    public AbstractSerializableFrameFactory getAbstractSerializableFrameFactory()
    {
        return abstractSerializableFrameFactory;
    }

    public int getFrameType()
    {
        return frameType;
    }

    /*    public void initialiseFrameFactories() throws InitialisationException
    {
        try
        {
            for ( Map.Entry < String, SerializableFrameFactoryLookUpObject > lookUp : factoriesLookUpMap.entrySet() )
            {
                //TODO: Log it
                // Instantiation can be done using just Class.forName, it is done the below way to be extensible.
                Constructor < SerializableFrameFactory > constructor = ( Constructor < SerializableFrameFactory >  ) Class.forName( lookUp.getValue().getClassLocation() ).getConstructor();
                factoriesMap.put( lookUp.getKey(), constructor.newInstance() ) ;
            }
        }
        catch (Exception e)
        {
            throw new InitialisationException( e );
        }
    }*/

}
