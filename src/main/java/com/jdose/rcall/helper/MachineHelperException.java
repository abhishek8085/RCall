package com.jdose.rcall.helper;

import com.jdose.rcall.commons.exception.SDSTesterException;

/**
 * Created by abhishek.i on 1/28/2015.
 */
public class MachineHelperException extends SDSTesterException
{
    public MachineHelperException() {
    }

    public MachineHelperException(Throwable cause) {
        super(cause);
    }

    public MachineHelperException(String message) {
        super(message);
    }

    public MachineHelperException(String message, Throwable cause) {
        super(message, cause);
    }
}
