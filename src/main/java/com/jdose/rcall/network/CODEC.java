package com.jdose.rcall.network;

import com.jdose.rcall.commons.exception.DecodingException;
import com.jdose.rcall.commons.exception.EncodingException;
import com.jdose.rcall.frame.SerializableFrame;
import com.jdose.rcall.frame.commandframe.RMIFrame;
import com.jdose.rcall.frame.handshakeframe.HandShakeFrame;
import org.nustaq.serialization.FSTConfiguration;

import java.nio.ByteBuffer;
import java.util.Arrays;


/**
 * Created by abhishek.i on 1/23/2015.
 */

public class CODEC
{
    public static FSTConfiguration conf = null;

    static
    {
        conf = FSTConfiguration.createDefaultConfiguration();
        conf.registerClass( HandShakeFrame.class, RMIFrame.class );
    }





    public static byte[] encodeFrame( SerializableFrame serializableFrame ) throws EncodingException
    {
       // AbstractFrame abstractFrame = ( AbstractFrame ) serializableFrame;
        //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //FSTObjectOutput out = new FSTObjectOutput(byteArrayOutputStream);

        try
        {

            //out.writeObject( serializableFrame );
            //out.flush();
            //ObjectOutputStream objectOutputStream = new ObjectOutputStream( byteArrayOutputStream );
            //objectOutputStream.writeObject( serializableFrame );

            //byte[] byteArray = byteArrayOutputStream.toByteArray();

            byte[] byteArray = conf.asByteArray(serializableFrame);
            int length = byteArray.length;

            ByteBuffer b = ByteBuffer.allocate( 4 );
            b.putInt( length );

            return appendArrays( b.array(), byteArray );
        }
        catch ( Exception e )
        {
            throw  new EncodingException( e ) ;
        }

    }

   public static SerializableFrame decode( byte[] bytes ) throws DecodingException
    {
       // FSTObjectInput in = null;
        try
        {

            //ObjectInputStream objectInputStream = new ObjectInputStream( new ByteArrayInputStream( bytes ) );
           // in = new FSTObjectInput( new ByteArrayInputStream( bytes ) );
            //in.close();


           // System.out.println( bytes );
             return ( SerializableFrame ) conf.getObjectInput( bytes ).readObject();

            //return ( SerializableFrame ) in.readObject();
        }
        catch ( Exception e)
        {
            throw new DecodingException( e );
        }
        finally
        {

/*            if ( in != null ) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/

        }
    }

    private static byte[] appendArrays( byte[] length, byte[] byteArray )
    {
        byte[] bytes = Arrays.copyOf( length, byteArray.length + 4 );

        for ( int i =0; i < byteArray.length; i++ )
        {
            bytes[ i+4 ] =  byteArray[ i ];
        }
        return bytes;
    }
}




/*
package com.rit.smi.commons.network;

        import Decodeable;
        import Encodeable;
        import DecodingException;
        import EncodingException;
        import AbstractFrame;
        import SerializableFrame;
        import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

        import java.io.*;
        import java.nio.ByteBuffer;
        import java.util.Arrays;

/**
 * Created by abhishek.i on 1/23/2015.

public class CODEC
{
    public static byte[] encodeFrame( SerializableFrame serializableFrame ) throws EncodingException
    {
        AbstractFrame abstractFrame = ( AbstractFrame ) serializableFrame;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try
        {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream( byteArrayOutputStream );
            objectOutputStream.writeObject( serializableFrame );

            byte[] byteArray = byteArrayOutputStream.toByteArray();
            int length = byteArray.length;

            ByteBuffer b = ByteBuffer.allocate( 4 );
            b.putInt( length );

            return appendArrays( b.array(), byteArray );
        }
        catch ( IOException e )
        {
            throw  new EncodingException( e ) ;
        }
    }

    public static SerializableFrame decode( byte[] bytes ) throws DecodingException
    {
        try
        {
            ObjectInputStream objectInputStream = new ObjectInputStream( new ByteArrayInputStream( bytes ) );
            return ( SerializableFrame ) objectInputStream.readObject();
        }
        catch ( IOException | ClassNotFoundException e)
        {
            throw new DecodingException( e );
        }
    }

    private static byte[] appendArrays( byte[] length, byte[] byteArray )
    {
        byte[] bytes = Arrays.copyOf( length, byteArray.length + 4 );

        for ( int i =0; i < byteArray.length; i++ )
        {
            bytes[ i+4 ] =  byteArray[ i ];
        }
        return bytes;
    }
}
*/