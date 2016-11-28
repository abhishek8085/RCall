package com.jdose.rcall.network;

import com.jdose.rcall.commons.Initialisable;
import com.jdose.rcall.commons.exception.InitialisationException;

import java.nio.channels.Selector;

/**
 * Created by abhishek.i on 3/18/2015.
 */
public class ObjectServerSocket1 implements Initialisable
{
   // private SDSServerSocket sdsServerSocket;



    @Override
    public void initialise() throws InitialisationException
    {

    }

    private class ObjectReceiverThread implements Runnable
    {
        private Selector selector;
        private ObjectReceiverListener objectReceiverListener;


        @Override
        public void run()
        {
            while ( true )
            {
/*                try
                {
                    serverSocket.accept();


                } catch (IOException e)
                {
                    e.printStackTrace();
                }*/
            }
        }
    }
}
