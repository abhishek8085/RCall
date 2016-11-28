package com.jdose.rcall.commons.utils;

import java.util.Arrays;

/**
 * Created by abhishek.i on 5/19/2015.
 */
public class MultiKey
{
    private Object[] keys;

    public MultiKey( Object... keys )
    {
        this.keys = keys;
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode( keys );
    }

    @Override
    public boolean equals( Object obj )
    {
       if( obj == null )
       {
           return false;
       }

        if( !( obj instanceof MultiKey ) )
        {
            return false;
        }

        return Arrays.equals( keys, ( ( MultiKey ) obj ).keys );
    }
}
