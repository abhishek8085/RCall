package com.jdose.rcall.commons.scheduler;

/**
 * Created by abhishek.i on 1/24/2015.
 */
public interface Scheduler
{
    public void addSchedulableTask(Schedulable schedulable);

    public void numberOfThreads (int threadCount);

    public void startScheduler();

    public void stopScheduler();
}
