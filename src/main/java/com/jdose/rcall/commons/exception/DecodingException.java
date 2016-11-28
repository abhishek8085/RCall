package com.jdose.rcall.commons.exception;

/**
 * Created by abhishek.i on 1/23/2015.
 */
public class DecodingException extends SDSException
{
    public DecodingException() {
    }

    public DecodingException(Throwable cause) {
        super(cause);
    }

    public DecodingException(String message) {
        super(message);
    }

    public DecodingException(String message, Object... args) {
        super(message, args);
    }

    public DecodingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecodingException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }
}
