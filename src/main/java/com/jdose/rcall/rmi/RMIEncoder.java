package com.jdose.rcall.rmi;

import java.lang.reflect.Method;

/**
 * Created by abhishek.i on 5/15/2015.
 */
public class RMIEncoder
{
    public static RMIRequest encodeRMI( String implPacakage, Method method, Object[] args )
    {
        return new RMIRequest( RMIServerHelper.toRMIClass( implPacakage ), RMIServerHelper.toRMIMethod( args,  method), RMIServerHelper.toRMISerializables(args), RMIClientHelper.getRMIId() );
    }
}

