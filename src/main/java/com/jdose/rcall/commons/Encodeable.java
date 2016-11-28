/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdose.rcall.commons;

import com.jdose.rcall.commons.exception.EncodingException;

/**
 *
 * @author abhishek.i
 */
public interface Encodeable 
{
    public byte[] encode() throws EncodingException;
}
