/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdose.rcall.helper;

import com.jdose.rcall.commons.Initialisable;
import com.jdose.rcall.commons.ReInitialisationException;
import com.jdose.rcall.commons.ReInitialiseable;
import com.jdose.rcall.commons.exception.InitialisationException;
import com.jdose.rcall.commons.exception.PropertyHelperException;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author abhishek.i
 */
public class ConfigPropertiesHelper implements Initialisable, ReInitialiseable
{
    private static ConfigPropertiesHelper configPropertiesHelper;
    private static Properties properties = new Properties();
    private static InputStream inputStream;
    private static boolean initialsed =false;
    
    private ConfigPropertiesHelper() {}; 
    
    public static ConfigPropertiesHelper getInstance()
    {
        if (configPropertiesHelper!=null)
        {
            return configPropertiesHelper;
        }
        else
        {
            return configPropertiesHelper = new ConfigPropertiesHelper();
        }
    }
    
    @Override
    public void initialise() throws InitialisationException
    {
       // try
      //  {
            //inputStream = new FileInputStream(new File("../config/ConnectionTester.properties"));
            //properties.load(inputStream);
            initialsed = true;
/*        }
        catch (FileNotFoundException e) 
        {
            throw new InitialisationException("SDSAgentProperty.property not found.");
        } 
        catch (IOException ex) 
        {
            throw new InitialisationException(ex);
        }
        */
        
       // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reInitialise() throws ReInitialisationException
    {
        initialise();
    }
    
    public static String getProperty(String propertyName)
    {
        if(!initialsed)
        {
            throw new PropertyHelperException("ConfigPropertiesHelper not initialise.");
        }
        
        return properties.getProperty(propertyName);
    }

    public static void setProperty(String propertyName , String value)
    {
        if(!initialsed)
        {
            throw new PropertyHelperException("ConfigPropertiesHelper not initialise.");
        }

        properties.setProperty(propertyName, value );
    }
}
