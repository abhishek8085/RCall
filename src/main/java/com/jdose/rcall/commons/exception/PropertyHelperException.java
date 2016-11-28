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
public class PropertyHelperException extends SDSRuntimeException
{

    public PropertyHelperException() {
    }

    public PropertyHelperException(String message) {
        super(message);
    }

    public PropertyHelperException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyHelperException(Throwable cause) {
        super(cause);
    }
    
}
