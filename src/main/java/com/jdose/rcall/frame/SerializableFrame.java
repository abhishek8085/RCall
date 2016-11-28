/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdose.rcall.frame;

import com.jdose.rcall.Machine;

import java.io.Serializable;

/**
 *
 * @author abhishek.i
 */
public interface SerializableFrame extends Serializable
{
    public long getFrameUUID();

    public boolean isRetransmission();
    
    public int getFrameIdentifier();

    public Machine getDestinationMachine();

    public Machine getSourceMachine();

    public long getCreatedDateTime();
    
    public int getSequenceNumber();

    public byte[] getCompleteDataWithoutChecksum();

    public byte[] getCheckSum();
    
    public long getLatency();
}
