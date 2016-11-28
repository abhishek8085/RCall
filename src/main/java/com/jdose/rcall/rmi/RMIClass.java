package com.jdose.rcall.rmi;

import java.io.Serializable;

/**
 * Created by abhishek.i on 5/18/2015.
 */
public class RMIClass implements Serializable
{
    String canonicalName;

    public RMIClass( String canonicalName )
    {
        this.canonicalName = canonicalName;
    }
}
