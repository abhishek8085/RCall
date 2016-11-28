package com.jdose.rcall.network.sdssocket;

import com.jdose.rcall.commons.exception.SDSException;

/**
 * Created by abhishek.i on 6/4/2015.
 */
public class SocketProviderException extends SDSException
{
    public SocketProviderException() {
    }

    public SocketProviderException(Throwable cause) {
        super(cause);
    }

    public SocketProviderException(String message) {
        super(message);
    }

    public SocketProviderException(String message, Object... args) {
        super(message, args);
    }

    public SocketProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocketProviderException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }
}
