package com.jdose.rcall.commons.thread;

/**
 * Created by abhishek.i on 4/21/2015.
 */
public enum SDSThreadStatus
{
    THREAD_NOT_STARTED( 0 ),
    THREAD_STARTED ( 1 ),
    THREAD_COMPLETED ( 2 ),
    THREAD_TERMINATION_SIGNAL_SET( 3 ),
    THREAD_EXCEPTION ( 4 );


    private int eventCode;

    SDSThreadStatus(int eventCode)
    {
        this.eventCode = eventCode;
    }

    public int getEventCode()
    {
        return eventCode;
    }
}
