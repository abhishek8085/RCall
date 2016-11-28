package com.jdose.rcall.commons.exception;

/**
 * Created by abhishek.i on 1/23/2015.
 */
public class EncodingException extends SDSException
{
    public EncodingException() {
    }

    public EncodingException(Throwable cause) {
        super(cause);
    }

    public EncodingException(String message) {
        super(message);
    }

    public EncodingException(String message, Object... args) {
        super(message, args);
    }

    public EncodingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncodingException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }
}
