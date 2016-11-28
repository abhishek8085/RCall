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
public class SDSException extends Exception
{
    public SDSException()
    {
    }

    public SDSException(String message)
    {
        super(message);
    }

    public SDSException(String message, Object... args)
    {
        super(MessageFormat.format(message, args));
    }

    public SDSException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SDSException(String message, Throwable cause, Object... args)
    {
        super( MessageFormat.format(message, args), cause);
    }

    public SDSException(Throwable cause)
    {
        super(cause);
    }
}
