/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdose.rcall.commons;

import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.helper.ConfigPropertiesHelper;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abhishek.i
 */
public class InitialisationHelper 
{
    private static List <Initialisable> initialisables = new ArrayList<Initialisable>();
    
    static 
    {
       initialisables.add(ConfigPropertiesHelper.getInstance());
        
        
        
    }
    
    public static void initialise() throws InitialisationException
    {
        for (Initialisable initialisable :initialisables)
        {
            initialisable.initialise();
        }
    }
    
    
    
    
    
}
