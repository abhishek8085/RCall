package com.jdose.rcall.commons.exception;

/**
 * Created by abhishek.i on 1/28/2015.
 */
public class NoSuchFrameTypeException extends SDSTesterException
{
    public NoSuchFrameTypeException() {
    }

    public NoSuchFrameTypeException(Throwable cause) {
        super(cause);
    }

    public NoSuchFrameTypeException(String message) {
        super(message);
    }

    public NoSuchFrameTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
