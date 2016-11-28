package com.jdose.rcall.handshake;

import com.jdose.rcall.commons.exception.SDSException;

/**
 * Created by abhishek.i on 6/4/2015.
 */
public class HandshakeException extends SDSException
{
    public HandshakeException() {
    }

    public HandshakeException(Throwable cause) {
        super(cause);
    }

    public HandshakeException(String message) {
        super(message);
    }

    public HandshakeException(String message, Object... args) {
        super(message, args);
    }

    public HandshakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandshakeException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }
}
