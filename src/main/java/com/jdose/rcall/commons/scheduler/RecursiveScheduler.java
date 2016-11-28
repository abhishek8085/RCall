package com.jdose.rcall.commons.scheduler;

/**
 * Created by abhishek.i on 1/24/2015.
 */
public interface RecursiveScheduler extends Scheduler
{
    public void schedulableInterval( long interval );
}
