/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdose.rcall.commons;

import com.jdose.rcall.commons.exception.InitialisationException;


/**
 *
 * @author abhishek.i
 */
public interface Initialisable 
{
    public void  initialise() throws InitialisationException;
}
