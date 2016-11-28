package com.jdose.rcall.commons.thread;

/**
 * Created by abhishek.i on 4/1/2015.
 */
 public class SDSThread extends Thread
{
    private boolean setTerminateSignal;
    private String threadName;
    private Runnable runnable;
    
    private SDSThreadStatus sdsThreadStatus;
    private Throwable returnedException;

    private SDSThreadListener sdsThreadListener;

    public SDSThread( String name )
    {
        super( name );
        sdsThreadStatus = SDSThreadStatus.THREAD_NOT_STARTED;
    }

   public SDSThread( String threadName, Runnable runnable )
   {
       this ( threadName );
       this.threadName = threadName;
       this.runnable = runnable;

   }

    public SDSThread( String threadName, Runnable runnable, SDSThreadListener sdsThreadListener )
    {
        this ( threadName );
        this.threadName = threadName;
        this.runnable = runnable;
        this.sdsThreadListener = sdsThreadListener;
    }

    protected boolean isTerminateSignalSet()
    {
        return setTerminateSignal;
    }

    public void terminate()
    {
        this.setTerminateSignal = true;
        this.interrupt();
        terminateSignalSet();
        sdsThreadStatus = SDSThreadStatus.THREAD_TERMINATION_SIGNAL_SET;
        notifyListenersOnThreadStatus();
    }

     protected void terminateSignalSet()
     {

     }

    @Override
    public void run()
    {
        sdsThreadStatus = SDSThreadStatus.THREAD_STARTED;
        notifyListenersOnThreadStatus();

        try
        {
            if (runnable != null)
            {
                runnable.run();
            }
            else
            {
                threadWork();
            }
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
            returnedException = e;
            sdsThreadStatus = SDSThreadStatus.THREAD_EXCEPTION;
            notifyListenersOnThreadStatus();
        }

        sdsThreadStatus = SDSThreadStatus.THREAD_COMPLETED;
        notifyListenersOnThreadStatus();
    }

    public void threadWork()
    {
        // do thread work
    }

    private void notifyListenersOnThreadStatus()
    {
        if ( sdsThreadListener !=null )
        {
            sdsThreadListener.onSDSThreadEvent(sdsThreadStatus);
        }
    }

}
