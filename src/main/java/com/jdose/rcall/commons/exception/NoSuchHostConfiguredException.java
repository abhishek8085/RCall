package com.jdose.rcall.commons.exception;

/**
 * Created by abhishek.i on 1/27/2015.
 */
public class NoSuchHostConfiguredException extends NetManagerException
{
    public NoSuchHostConfiguredException()
    {
    }

    public NoSuchHostConfiguredException(Throwable cause)
    {
        super(cause);
    }

    public NoSuchHostConfiguredException(String message)
    {
        super(message);
    }

    public NoSuchHostConfiguredException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
