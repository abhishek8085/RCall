package com.jdose.rcall.frame.commandframe;

import java.io.Serializable;

/**
 * Created by abhishek.i on 2/25/2015.
 */
public class SDSExecutable implements Serializable
{
    private String commandExpression;

    public SDSExecutable(String commandExpression)
    {
        this.commandExpression = commandExpression;
    }

    public String getCommandExpression()
    {
        return commandExpression;
    }
}
