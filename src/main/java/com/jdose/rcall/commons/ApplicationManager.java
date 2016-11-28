package com.jdose.rcall.commons;

/**
 * Created by abhishek.i on 5/29/2015.
 */
public class ApplicationManager
{
    private static volatile boolean isAgentMode;

    public static boolean isAgentMode()
    {
        return isAgentMode;
    }

    public static void setIsAgentMode( boolean isAgentMode )
    {
        ApplicationManager.isAgentMode = isAgentMode;
    }

    public static void shutdownApplication()
    {

    }
}
