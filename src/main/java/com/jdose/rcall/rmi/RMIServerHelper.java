package com.jdose.rcall.rmi;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by abhishek.i on 5/18/2015.
 */
public class RMIServerHelper
{

    public static RMIClass toRMIClass ( String implPacakage )
    {
        return new RMIClass( implPacakage+ "Impl");
    }

    public static String getRMIServiceImpl ( Class < ? > clazz )
    {
        return clazz.getCanonicalName() +"Impl";
    }

    public static RMIMethod toRMIMethod( Object[] args, Method method )
    {
        return new RMIMethod( getMethodName( method ), getSafeMethodReturnType( method ), getSafeMethodParametersTypes( args ), method.isVarArgs() );
    }

    public static String getMethodName( Method method )
    {
        return method.getName();
    }

/*    public static  < T extends Class< Serializable > >  T getSafeMethodReturnType( Method method )
    {
        if ( ! Serializable.class.isAssignableFrom( method.getReturnType() ) )
        {
            throw new RMIException( "Return type is not instance of 'Serializable'" );
        }

        //return ( T ) method.getReturnType() ;

        return ( T ) Object.class;
    }*/

    public static  Class< ? >  getSafeMethodReturnType( Method method )
    {
        return Object.class;
    }

    public static < T extends Class< Serializable > >  T[] getSafeMethodParametersTypes( Object[] parameters )
    {
        try
        {
            return getSafeSerializables(parameters);
        }
        catch ( RMIException e )
        {
            throw new RMIException( "Method Parameter is not instance of 'Serializable'" );
        }
    }

    public static < T extends Class< Serializable > >  T[] getSafeSerializables( Object [] parameters )
    {
        Class< ? > parameterTypes [] = new Class [ parameters.length ] ;

        for ( int i = 0; i < parameters.length; i++ )
        {
            if ( ! ( Serializable.class.isAssignableFrom( parameters[ i ].getClass()  ) ) )
            {
                throw new RMIException( "Parameter '"+parameters[ i ].getClass()+"' is not instance of 'Serializable or Serializable" );
            }
            parameterTypes[ i ] = parameters[ i ].getClass();
        }
        return ( T[] ) parameterTypes;
    }


    public static Object[] toRMISerializables( Object[] parameters )
    {
        return parameters;
    }
}


