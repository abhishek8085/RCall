package com.jdose.rcall.commons.version;

import com.jdose.rcall.commons.exception.SDSRuntimeException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Created by abhishek.i on 6/2/2015.
 */
public class VersionHelper
{
    private static volatile String binaryVersion;

    public static boolean checkCompatibility( String versionToTest )
    {
        return CompatibilityChecker.areVersionCompatible( new SDSVersion( versionToTest ) );
    }

    public static String getPresentBinariesVersion()
    {
        if ( binaryVersion == null )
        {
            synchronized ( VersionHelper.class )
            {
                if ( binaryVersion == null )
                {
                    binaryVersion = getBinariesVersion( VersionHelper.class );
                }
            }
        }
        return binaryVersion;
    }


    public static String getBinariesVersion( Class < ? >  clazz )
    {
        File file;
        try
        {
            file = new File( clazz.getProtectionDomain().getCodeSource().getLocation().toURI() );
        }
        catch ( URISyntaxException e )
        {
            throw new SDSRuntimeException( e );
        }

        if ( !file.exists() || !file.isFile() )
        {
            return "0.0.0.0";
        }

        JarFile jarFile;
        String version;

        try
        {
            jarFile = new JarFile( file );
            Manifest manifest = jarFile.getManifest();
            version = manifest.getMainAttributes().getValue( "SDS-Version" );
        }
        catch ( IOException e )
        {
            throw new SDSRuntimeException( e );
        }

        if ( version == null )
        {
            return "0.0.0.0";
        }

        return version;
    }
}

class CompatibilityChecker
{
    private static HashSet< SDSVersion > compatibilitySet = new HashSet < SDSVersion > ();

    static
    {
        //populate compatibility set
    }

    public static boolean areVersionCompatible( SDSVersion versionToTest )
    {
        if ( !versionToTest.equals( new SDSVersion( VersionHelper.getPresentBinariesVersion() ) ) )
        {
            if ( compatibilitySet.contains( VersionHelper.getPresentBinariesVersion() ) )
            {
                return true;
            }
            return false;
        }
        return true;
    }
}