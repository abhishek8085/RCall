package com.jdose.rcall.rmi;

import com.jdose.rcall.network.ArgumentsAndCallBack;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by abhishek.i on 5/20/2015.
 */
public class RMIClientHelper
{
    private static AtomicLong rmiId = new AtomicLong();

    public static Long getRMIId()
    {
        return rmiId.getAndIncrement();
    }

    public static ArgumentsAndCallBack removeAsyncCallback( Object[] parameters )
    {
        Object [] newParameters = new Object[ parameters.length - 1 ];
        AsyncCallback asyncCallback = null;
        Class< ? > returnType =null;
        int k=0;

        for ( int i=0 ; i < parameters.length ; i++ )
        {
            if ( parameters[ i ] instanceof AsyncCallback )
            {
                if ( asyncCallback == null )
                {
                    asyncCallback  = ( AsyncCallback < ? > ) parameters[ i ];
                    //bit naive
                    //returnType = asyncCallback.getClass().getMethods()[ 1 ].getParameterTypes()[ 0 ];
                    //getReturnType(  asyncCallback );
                    continue;
                }
                else
                {
                    throw new RMIException( "RMI cannot have more than one Aync call back" );
                }
            }
            newParameters[ k++ ] = parameters[ i ];
        }
        parameters = newParameters;
        return new ArgumentsAndCallBack( parameters, asyncCallback, returnType );
    }

   private static void getReturnType( AsyncCallback < ? > asyncCallback )
    {
        final Class type = (Class) ((ParameterizedType) asyncCallback.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println(type);
    }
}
