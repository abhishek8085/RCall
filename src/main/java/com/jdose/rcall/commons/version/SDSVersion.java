package com.jdose.rcall.commons.version;

/**
 * Created by abhishek.i on 6/2/2015.
 */
public class SDSVersion implements Comparable
{
    private int majorVersion;
    private int minorVersion;
    private int revisionVersion;
    private int servicePack;


    public SDSVersion( String version )
    {
        String[] versionInfo = version.split( "\\." );

        this.majorVersion = Integer.parseInt( versionInfo[ 0 ] );
        this.minorVersion = Integer.parseInt( versionInfo[ 1 ] );
        this.revisionVersion = Integer.parseInt( versionInfo[ 2 ] );
        this.servicePack = Integer.parseInt( versionInfo[ 3 ] );
    }

    @Override
    public int compareTo( Object o )
    {
        if ( !o.equals( this ) )
        {
            SDSVersion sdsVersion = ( SDSVersion ) o;

            if ( sdsVersion.majorVersion > this.majorVersion )
            {
                if ( sdsVersion.minorVersion > this.minorVersion )
                {
                    if ( sdsVersion.revisionVersion > this.revisionVersion  )
                    {
                        if ( sdsVersion.servicePack > this.servicePack )
                        {
                            return 1;
                        }
                    }
                }
            }
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
        {
            return false;
        }

        if ( !( obj instanceof SDSVersion ) )
        {
            return false;
        }

        SDSVersion sdsVersion = ( SDSVersion ) obj;

        if ( sdsVersion.majorVersion == this.majorVersion )
        {
            if ( sdsVersion.minorVersion == this.minorVersion )
            {
                if ( sdsVersion.revisionVersion == this.revisionVersion  )
                {
                    if ( sdsVersion.servicePack == this.servicePack )
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return 31 * majorVersion * minorVersion * revisionVersion * servicePack;
    }

    @Override
    public String toString()
    {
        return "SDSVersion{" +
                "majorVersion=" + majorVersion +
                ", minorVersion=" + minorVersion +
                ", revisionVersion=" + revisionVersion +
                ", servicePack=" + servicePack +
                '}';
    }
}
