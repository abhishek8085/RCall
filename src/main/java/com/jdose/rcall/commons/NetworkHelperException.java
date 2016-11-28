package com.jdose.rcall.commons;

import com.jdose.rcall.commons.exception.SDSTesterException;

/**
 * Created by abhishek.i on 1/28/2015.
 */
public class NetworkHelperException extends SDSTesterException
{
    public NetworkHelperException() {
    }

    public NetworkHelperException(Throwable cause) {
        super(cause);
    }

    public NetworkHelperException(String message) {
        super(message);
    }

    public NetworkHelperException(String message, Throwable cause) {
        super(message, cause);
    }
}
