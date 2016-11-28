/*
package com.subex.connectiontester.commons.scheduler;

import com.subex.connectiontester.Machine;
import com.subex.connectiontester.commons.ReInitialisationException;
import com.subex.connectiontester.commons.ReInitialiseable;
import com.subex.connectiontester.commons.pinger.PingThread;
import com.subex.connectiontester.commons.Initialisable;
import com.subex.connectiontester.commons.exception.InitialisationException;
import com.subex.connectiontester.helper.ConfigProperties;
import com.subex.connectiontester.helper.ConfigPropertiesHelper;
import com.subex.connectiontester.helper.MachineHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

*/
/**
 * Created by abhishek.i on 1/23/2015.
 *
 * The name default is given because the configuration is obtained from
 * configuration file.
 *//*

public class DefaultRecursiveScheduler implements RecursiveScheduler, Initialisable, ReInitialiseable
{
    private static ScheduledExecutorService scheduledExecutorService ;
    private ArrayList<Schedulable> tasksList = new ArrayList<Schedulable>();
    private long schedulableInterval = -1;
    private int threadCount = -1;

*/
/*    public static void startPinger()
    {
        long schedulableInterval = Long.parseLong(ConfigPropertiesHelper.getProperty(ConfigProperties.PING_INTERVAL));

        for( Machine machine : MachineHelper.getDestinationMachines() )
        {
            scheduledExecutorService.schedule(new SchedulerAdapter(new PingThread(machine)),schedulableInterval, TimeUnit.MILLISECONDS);
        }
    }*//*


    @Override
    public void initialise() throws InitialisationException
    {
        checkParameters();
        scheduledExecutorService = Executors.newScheduledThreadPool( threadCount );
    }

    @Override
    public void reInitialise() throws ReInitialisationException
    {
        stopScheduler();
        initialise();
    }

    @Override
    public void schedulableInterval( long interval )
    {
        schedulableInterval = interval;
    }

    @Override
    public void numberOfThreads( int threadCount )
    {
        this.threadCount = threadCount;
    }
    @Override
    public void addSchedulableTask( Schedulable schedulable )
    {
        tasksList.add( schedulable );
    }

    @Override
    public void startScheduler()
    {
        for( Schedulable schedulable : tasksList )
        {
            scheduledExecutorService.schedule( new SchedulerAdapter( schedulable ), schedulableInterval, TimeUnit.MILLISECONDS );
        }
    }

    @Override
    public void stopScheduler()
    {
        scheduledExecutorService.shutdown();
    }

    private void checkParameters() throws InitialisationException
    {
        if( ! ( threadCount < 1 && ( schedulableInterval < 1 || schedulableInterval > Long.MAX_VALUE ) ) )
        {
            throw new InitialisationException( "Illegal value in parameters." );
        }

    }

}
*/
