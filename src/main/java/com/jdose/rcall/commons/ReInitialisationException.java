package com.jdose.rcall.commons;

import com.jdose.rcall.commons.exception.SDSRuntimeException;

/**
 * Created by abhishek.i on 1/28/2015.
 */
public class ReInitialisationException extends SDSRuntimeException
{
    public ReInitialisationException() {
    }

    public ReInitialisationException(Throwable cause) {
        super(cause);
    }

    public ReInitialisationException(String message) {
        super(message);
    }

    public ReInitialisationException(String message, Throwable cause) {
        super(message, cause);
    }
}
