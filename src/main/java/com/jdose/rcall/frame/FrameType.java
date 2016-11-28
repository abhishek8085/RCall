package com.jdose.rcall.frame;

/**
 * Created by abhishek.i on 2/12/2015.
 */
public enum FrameType
{
    HANDSHAKE_FRAME( 0 ), ACK_FRAME( 1 ), NAK_FRAME( -1 ),RMI_FRAME( 2 ) ,PING_FRAME( 3 );

    private int frameIdentifier;


    FrameType( int frameIdentifier )
    {
        this.frameIdentifier = frameIdentifier;
    }

    public int getFrameIdentifier()
    {
        return frameIdentifier;
    }

}