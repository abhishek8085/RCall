package com.jdose.rcall.commons.scheduler;

/**
 * Created by abhishek.i on 1/24/2015.
 */
public class SchedulerAdapter implements Runnable
{
    private Schedulable schedulable;

    public SchedulerAdapter( Schedulable schedulable )
    {
        this.schedulable = schedulable;
    }

    @Override
    public void run()
    {
        schedulable.schedule();
    }
}
