package com.jdose.rcall.rmi;

import java.io.Serializable;

/**
 * Created by abhishek.i on 5/19/2015.
 */
public enum RMIExecutionStatus implements Serializable
{
    EXECUTION_SUCCESSFUL( 0 ),
    EXECUTION_EXCEPTION( 1 ),
    EXECUTION_WARNING( 2 );

    int existStatus;

    private  RMIExecutionStatus( int existStatus )
    {
        this.existStatus = existStatus;
    }

    public int getExistStatus()
    {
        return existStatus;
    }
}
