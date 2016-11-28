package com.jdose.rcall;

import java.io.Serializable;

/**
 * Created by abhishek.i on 1/23/2015.
 */
public class Machine implements Serializable
{
    private String machineName;
    private String machineAddress;
    private Integer machinePort;
    private boolean isDisabled;
    private Object disableReason;

    public Machine( String machineAddress, String machineName, int machinePort )
    {
        this.machineAddress = machineAddress;
        this.machineName = machineName;
        this.machinePort = machinePort;
    }

    public String getMachineAddress()
    {
        return machineAddress;
    }

    public String getMachineName()
    {
        return machineName;
    }

    public int getMachinePort()
    {
        return machinePort;
    }

    public < T >  T getDisableReason()
    {
        return ( T ) disableReason;
    }

    public void setDisableReason( Object disableReason )
    {
        this.disableReason = disableReason;
    }

    public void setIsDisabled( boolean isDisabled )
    {
        this.isDisabled = isDisabled;
    }

    public boolean isDisabled()
    {
        return isDisabled;
    }

    public void setDisabled(boolean isDisabled)
    {
        //once disabled always disabled
        if( ! isDisabled )
        {
            this.isDisabled = isDisabled;
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if ( obj == null )
        {
            return false;
        }

        if ( ! ( obj instanceof  Machine ) )
        {
            return false;
        }

        if ( ( ( ( Machine ) obj ).machineAddress.equals( machineAddress ) ) && ( ( ( Machine ) obj ).getMachinePort() == machinePort ) )
        {
            return true;
        }
         return false;
    }

    @Override
    public int hashCode()
    {
        return 31 * machineAddress.hashCode() * machinePort.hashCode();
    }
}
