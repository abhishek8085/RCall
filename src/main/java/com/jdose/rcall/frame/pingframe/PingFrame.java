/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdose.rcall.frame.pingframe;

import com.jdose.rcall.Machine;
import com.jdose.rcall.frame.AbstractFrame;

/**
 *
 * @author abhishek.i
 */
public class PingFrame extends AbstractFrame
{
    public PingFrame(long frameId, int frameIdentifier, Machine destinationMachine, Machine sourceMachine, long timeStamp) {
        super(frameId, frameIdentifier, destinationMachine, sourceMachine, timeStamp);
    }

    @Override
    public byte[] getPayloadData() {
        return new byte[0];
    }

    @Override
    public <T> T getPayloadObject() {
        return null;
    }

    @Override
    protected Class<?> getPayloadObjectType() {
        return null;
    }

    @Override
    protected boolean isObjectPayload() {
        return false;
    }
/*    private int port = Integer.parseInt( ConfigProperties.DEFAULT_PING_PORT );

    public PingFrame(String uuid, int frameIdentifier, Machine SourceAddress, DateTime createdDatetime )
    {
        super(uuid, frameIdentifier, SourceAddress, createdDatetime );
    }


    @Override
    protected byte[] getPayloadData() 
    {
        int pingSize = Integer.parseInt( ConfigPropertiesHelper.getProperty(ConfigProperties.PING_SIZE) );
        
        return DataGenerator.generateRandomData(pingSize);
    }

    @Override
    protected Object getPayloadObject() {
        return null;
    }

    @Override
    protected Class<?> getPayloadObjectType()
    {
        return null;
    }

    @Override
    protected boolean isObjectPayload()
    {
        return false;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }*/


}
