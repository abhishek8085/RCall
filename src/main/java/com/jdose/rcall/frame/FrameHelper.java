package com.jdose.rcall.frame;

import com.jdose.rcall.commons.exception.SDSRuntimeException;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by abhishek.i on 5/20/2015.
 */
public class FrameHelper
{
    private static AtomicLong frameId = new AtomicLong();
    private static GenericObjectPool< MessageDigest > md5Pool = new GenericObjectPool<MessageDigest>( new MD5Factory() );

    static
    {
        //md5Pool.setBlockWhenExhausted( false );
    }

    public static long getFrameId()
    {
        return frameId.getAndIncrement();
    }

    public static boolean verifyCheckSum( SerializableFrame serializableFrame )
    {
        try
        {
            byte [] calculatedCheckSum = calculateCurrentCheckSum( serializableFrame.getCompleteDataWithoutChecksum());
            return Arrays.equals( calculatedCheckSum, serializableFrame.getCheckSum() );
        }
        catch ( Exception e )
        {
            throw new SDSRuntimeException( e );
        }
    }

    public static byte[] calculateCurrentCheckSum( byte[] data )
    {
        MessageDigest checkSumProvider = null;
        try
        {
            checkSumProvider = md5Pool.borrowObject();
            return checkSumProvider.digest( data );
        }
        catch ( Exception e )
        {
            throw new SDSRuntimeException( e );
        }
        finally
        {
            //at exception can check in null object
            md5Pool.returnObject( checkSumProvider );
        }
    }
}


class MD5Factory extends BasePooledObjectFactory< MessageDigest >
{
    @Override
    public MessageDigest create() throws Exception
    {
        synchronized ( this )
        {
            return MessageDigest.getInstance( "MD5" );
        }
    }

    @Override
    public PooledObject<MessageDigest> wrap( MessageDigest messageDigest )
    {
        return new DefaultPooledObject< MessageDigest >( messageDigest );
    }
}