package com.jdose.rcall.rmi;

/**
 * Created by abhishek.i on 3/6/2015.
 */
public interface AsyncCallback < T >
{
    public void onSuccess( T result );

    public void onFailure ( Throwable throwable );
}
