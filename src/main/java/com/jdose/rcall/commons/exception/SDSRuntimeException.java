/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdose.rcall.commons.exception;

import java.text.MessageFormat;

/**
 *
 * @author abhishek.i
 */
public class SDSRuntimeException extends RuntimeException
{
    public SDSRuntimeException()
    {
    }

    public SDSRuntimeException(String message)
    {
        super(message);
    }

    public SDSRuntimeException(String message, Object... args)
    {
        super(MessageFormat.format(message, args));
    }

    public SDSRuntimeException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SDSRuntimeException(String message, Throwable cause, Object... args)
    {
        super( MessageFormat.format(message, args), cause);
    }

    public SDSRuntimeException(Throwable cause)
    {
        super(cause);
    } 
}
