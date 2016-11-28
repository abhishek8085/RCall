package com.jdose.rcall.rmi;

import java.io.Serializable;

/**
 * Created by abhishek.i on 5/18/2015.
 */
public class RMIResponse implements Serializable
{
    private int rmiExecutionStatus;
    private Object output;
    private Throwable throwable;
    private long rmiId;

    public RMIResponse( long rmiId )
    {
        this.rmiId = rmiId;
    }

    public long getRmiId() {
        return rmiId;
    }

    public Object getOutput()
    {
        return output;
    }

    public void setOutput( Object output )
    {
        this.output = output;
    }

    public int getExistStatus()
    {
        return rmiExecutionStatus;
    }

    public void setExistStatus( RMIExecutionStatus rmiExecutionStatus )
    {
        this.rmiExecutionStatus = rmiExecutionStatus.existStatus;
    }

    public Throwable getThrowable()
    {
        return throwable;
    }

    public void setThrowable(Throwable throwable)
    {
        this.throwable = throwable;
    }
}
