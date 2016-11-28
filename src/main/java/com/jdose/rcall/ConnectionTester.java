/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdose.rcall;

import com.jdose.rcall.commons.InitialisationHelper;
import com.jdose.rcall.commons.exception.InitialisationException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abhishek.i
 */
public class ConnectionTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        try 
        {
            InitialisationHelper.initialise();
        } 
        catch (InitialisationException ex)
        {
            Logger.getLogger(ConnectionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
