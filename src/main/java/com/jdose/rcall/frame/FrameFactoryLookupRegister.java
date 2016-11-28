package com.jdose.rcall.frame;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.ReInitialisationException;
import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.helper.MachineHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by abhishek.i on 2/23/2015.
 */
public class FrameFactoryLookupRegister
{
    private static Map< Integer, AbstractSerializableFrameFactory > factoriesLookUpMap = new ConcurrentHashMap< Integer, AbstractSerializableFrameFactory >();
    private static final Logger logger = LogManager.getLogger( FrameFactoryLookupRegister.class );
    private Machine sourceMachine = MachineHelper.getThisMachine();
    private static FactoryProperties factoryProperties = new FactoryProperties();

    static
    {
        factoryProperties.setProperty( "SOURCE_MACHINE" , MachineHelper.getThisMachine()  );
    }

    public static void registerFrameFactory ( SerializableFrameFactoryLookUpObject frameFactoryLookUpObject )
    {
        factoriesLookUpMap.put(frameFactoryLookUpObject.getFrameType(), frameFactoryLookUpObject.getAbstractSerializableFrameFactory());
    }

    public static void unregisterFrameFactory ( String frameType )
    {
        factoriesLookUpMap.remove(frameType);
    }

    public static void unregisterAllFrameFactory ( )
    {
        factoriesLookUpMap.clear();
    }

    public static synchronized void initialiseFrameFactory ( String frameType ) throws InitialisationException
    {
        try
        {
            factoriesLookUpMap.get(frameType).initialise( factoryProperties );
            logger.info("Frame factory {} has been successfully initialised", frameType);
        }
        catch ( FrameFactoryException e )
        {
            throw new InitialisationException( e );
        }
    }

    public static synchronized void reinitialiseFrameFactory ( String frameType )
    {
        try
        {
            factoriesLookUpMap.get(frameType).reInitialise( factoryProperties );
            logger.info("Frame factory '{}' has been successfully reinitialised", factoriesLookUpMap.get(frameType).getClass().getSimpleName());
        }
        catch ( FrameFactoryException e )
        {
            throw new InitialisationException( e );
        }
    }

    public static synchronized void initialiseAllFrameFactories ( ) throws InitialisationException
    {
        for ( Map.Entry < Integer, AbstractSerializableFrameFactory > factoryLookUp : factoriesLookUpMap.entrySet() )
        {
            try
            {
                factoryLookUp.getValue().initialise( factoryProperties );
                logger.info("Frame factory '{}' has been successfully initialised", factoryLookUp.getValue().getClass().getSimpleName());
            }
            catch ( FrameFactoryException e )
            {
                throw new InitialisationException( e );
            }

        }
        logger.info( "All Frame factories have been successfully initialised" );
    }

    public static synchronized void reInitialiseAllFrameFactories ( ) throws ReInitialisationException
    {
        for ( Map.Entry < Integer, AbstractSerializableFrameFactory > factoryLookUp : factoriesLookUpMap.entrySet() )
        {
            try
            {
                factoryLookUp.getValue().reInitialise( factoryProperties );
                logger.info("Frame factory {} has been successfully initialised", factoryLookUp.getKey());
            }
            catch ( FrameFactoryException e )
            {
                throw new InitialisationException( e );
            }
        }
        logger.info( "All Frame factories have been successfully reinitialised" );
    }


    public static AbstractSerializableFrameFactory getFrameFactory( int frameType )
    {
        return factoriesLookUpMap.get( frameType );
    }

}
