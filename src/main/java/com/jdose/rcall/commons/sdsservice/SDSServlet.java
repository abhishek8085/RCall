package com.jdose.rcall.commons.sdsservice;

/**
 * Created by abhishek.i on 6/3/2015.
 */
public interface SDSServlet < S, R >
{
    void serviceRMI( S request, R response );
}
