/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdose.rcall.frame;

import java.util.Random;

/**
 *
 * @author abhishek.i
 */
public class DataGenerator
{
    public static byte[] generateRandomData( int bytes )
    {
       byte [] data = new byte [bytes];
       new Random().nextBytes(data);
       return data;
    }
    
}
