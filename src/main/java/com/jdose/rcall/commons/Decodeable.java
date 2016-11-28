package com.jdose.rcall.commons;

import com.jdose.rcall.commons.exception.DecodingException;

/**
 * Created by abhishek.i on 1/23/2015.
 */
public interface Decodeable
{
    public void decode( byte[] bytes) throws DecodingException;
}
