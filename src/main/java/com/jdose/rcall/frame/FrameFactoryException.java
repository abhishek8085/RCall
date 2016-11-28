package com.jdose.rcall.frame;

import com.jdose.rcall.commons.exception.SDSException;

/**
 * Created by abhishek.i on 5/20/2015.
 */
public class FrameFactoryException extends SDSException
{
    public FrameFactoryException()
    {
    }

    public FrameFactoryException( Throwable cause )
    {
        super( cause );
    }

    public FrameFactoryException( String message )
    {
        super( message );
    }

    public FrameFactoryException( String message, Object... args )
    {
        super( message, args );
    }

    public FrameFactoryException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public FrameFactoryException( String message, Throwable cause, Object... args )
    {
        super( message, cause, args );
    }
}
