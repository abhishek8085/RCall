package com.jdose.rcall.frame;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by abhishek.i on 2/12/2015.
 */
public class FactoryProperties
{
    private Map< String, Object > properties =  new ConcurrentHashMap< String, Object >();

    public < T > T getPropertyValue ( String propertyName )
    {
        return ( T ) properties.get( propertyName );
    }

    public void setProperty( String propertyName, Object propertyValue  ) {
    }
}
