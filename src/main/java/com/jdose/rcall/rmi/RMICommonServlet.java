package com.jdose.rcall.rmi;

import java.lang.reflect.Method;

/**
 * Created by abhishek.i on 5/19/2015.
 */
public class RMICommonServlet implements RMIServlet
{
    @Override
    public void serviceRMI( RMIRequest rmiRequest, RMIResponse rmiResponse )
    {
        processCall( rmiRequest, rmiResponse );
    }

    private void processCall( RMIRequest rmiRequest, RMIResponse rmiResponse )
    {
        try
        {
            Object object = executeMethod( rmiRequest );
            rmiResponse.setExistStatus( RMIExecutionStatus.EXECUTION_SUCCESSFUL );
            rmiResponse.setOutput( object );
        }
        catch ( Throwable t )
        {
            rmiResponse.setExistStatus( RMIExecutionStatus.EXECUTION_EXCEPTION );
            rmiResponse.setThrowable( t );
        }
    }

    private Object executeMethod( RMIRequest rmiRequest ) throws Throwable
    {
        Class clazz = Class.forName( rmiRequest.getRmiClass().canonicalName );
        Method method = getRMIMethod( clazz, rmiRequest );
        method.setAccessible( true );
        return method.invoke( clazz.newInstance(), rmiRequest.getArgs() );
    }

    private Method getRMIMethod( Class clazz, RMIRequest rmiRequest ) throws NoSuchMethodException
    {
        return clazz.getMethod( rmiRequest.getRmiMethod().getName(), rmiRequest.getRmiMethod().getParameterTypes() );
    }
}
