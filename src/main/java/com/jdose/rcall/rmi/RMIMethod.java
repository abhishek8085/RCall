package com.jdose.rcall.rmi;

import java.io.Serializable;

/**
 * Created by abhishek.i on 5/18/2015.
 */
public class RMIMethod implements Serializable
{
    private String name;
    private Class < ?  > returnType;
    private Class < ? extends Serializable > parameterTypes[];
    private boolean isVarArg;

    public RMIMethod( String name, Class < ?  > returnType,  Class < ? extends Serializable > parameterTypes[], boolean isVarArg )
    {
        this.name = name;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.isVarArg = isVarArg;
    }

    public String getName()
    {
        return name;
    }

    public Class< ? extends Serializable >[] getParameterTypes()
    {
        return parameterTypes;
    }

    public Class< ? > getReturnType()
    {
        return returnType;
    }

    public boolean isVarArg()
    {
        return isVarArg;
    }
}
