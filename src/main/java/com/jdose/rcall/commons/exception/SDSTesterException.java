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
public class SDSTesterException extends Exception
{
    public SDSTesterException()
    {
    }

    public SDSTesterException(String message)
    {
        super(message);
    }

    public SDSTesterException(String message, Throwable cause )
    {
        super( message, cause);
    }

    public SDSTesterException(String message, Throwable cause, Object...args)
    {
        super( MessageFormat.format(message, args), cause);
    }

    public SDSTesterException(Throwable cause)
    {
        super(cause);
    } 
}
