package com.jdose.rcall.rmi;

import java.io.Serializable;

/**
 * Created by abhishek.i on 5/18/2015.
 */
public class RMIRequest implements Serializable
{
    private RMIClass rmiClass;
    private RMIMethod rmiMethod;
    private Object[] args;
    private int versionId;
    private long rmiId;

    public RMIRequest( RMIClass rmiClass, RMIMethod rmiMethod, Object[] args, long rmiId )
    {
        this.rmiClass = rmiClass;
        this.rmiMethod = rmiMethod;
        this.args = args;
        this.rmiId = rmiId;
    }

    public RMIClass getRmiClass()
    {
        return rmiClass;
    }

    public RMIMethod getRmiMethod()
    {
        return rmiMethod;
    }

    public Object[] getArgs()
    {
        return args;
    }

    public int getVersionId()
    {
        return versionId;
    }

    public long getRmiId() {
        return rmiId;
    }
}
