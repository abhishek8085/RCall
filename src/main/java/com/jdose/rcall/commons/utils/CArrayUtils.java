/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdose.rcall.commons.utils;

import java.util.Arrays;

/**
 *
 * @author abhishek.i
 */
public class CArrayUtils 
{
    
    public static byte [] concate( byte [] a, byte [] b )
    {
         byte[] placeHolder =  Arrays.copyOf(a, a.length+b.length);
        
        for(int i=a.length; i<placeHolder.length; i++ )
        {
            placeHolder[i] = b[i-a.length];
        }
        
        return placeHolder;
    }
    
    
}
