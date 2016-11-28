package com.jdose.rcall.helper;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.MachineNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhishek.i on 1/23/2015.
 */
public class MachineHelper
{
    private static int countOfDestinationMachine;
    private static String inetAddress1;
    private static Machine machineInstance;
    private static Machine thisMachine = new Machine("129.21.88.34", "abhishek", 7777 );

    public static List< Machine > getDestinationMachines()
    {
        List< Machine > machineList = new ArrayList< Machine>();
        machineList.add( new Machine( "129.21.88.34", "abhishek", 7777 ) );
        return machineList;
    }

    public static int getCountOfDestinationMachine()
    {
        return countOfDestinationMachine;
    }

    public static Machine resloveMachineAddressToMachine(String destinationAddress) throws MachineNotFoundException
    {
        return null;
    }

    public static String getInetAddress()
    {
        return inetAddress1;
    }

    public static Machine getMachineInstance()
    {
        return machineInstance;
    }

    public static Machine getThisMachine()
    {
        return thisMachine;
    }
}
