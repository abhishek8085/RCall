package com.jdose.rcall.network.sdssocket;

import com.jdose.rcall.commons.exception.SDSRuntimeException;

/**
 * Created by abhishek.i on 5/4/2015.
 */
public class SDSSocketException extends SDSRuntimeException
{
    public SDSSocketException()
    {
    }

    public SDSSocketException(Throwable cause)
    {
        super( cause );
    }

    public SDSSocketException(String message)
    {
        super( message );
    }

    public SDSSocketException(String message, Object... args)
    {
        super( message, args );
    }

    public SDSSocketException(String message, Throwable cause)
    {
        super( message, cause );
    }

    public SDSSocketException(String message, Throwable cause, Object... args)
    {
        super (message, cause, args );
    }
}
