package com.jdose.rcall.network;

import com.jdose.rcall.rmi.RMIRemoteService;

/**
 * Created by abhishek.i on 5/26/2015.
 */
public interface AService extends RMIRemoteService
{
    String test( String test );
}
