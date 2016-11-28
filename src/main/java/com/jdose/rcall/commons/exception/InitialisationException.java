/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdose.rcall.commons.exception;

/**
 *
 * @author abhishek.i
 */
public class InitialisationException extends SDSRuntimeException
{

    public InitialisationException() {
    }

    public InitialisationException(String message) {
        super(message);
    }

    public InitialisationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitialisationException(Throwable cause) {
        super(cause);
    }
    
}
