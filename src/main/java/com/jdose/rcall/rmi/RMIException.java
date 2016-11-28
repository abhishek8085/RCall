package com.jdose.rcall.rmi;

import com.jdose.rcall.commons.exception.SDSRuntimeException;

/**
 * Created by abhishek.i on 5/18/2015.
 */
public class RMIException extends SDSRuntimeException
{
    public RMIException()
    {
    }

    public RMIException(Throwable cause)
    {
        super(cause);
    }

    public RMIException(String message)
    {
        super(message);
    }

    public RMIException(String message, Object... args)
    {
        super(message, args);
    }

    public RMIException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public RMIException(String message, Throwable cause, Object... args)
    {
        super(message, cause, args);
    }
}
