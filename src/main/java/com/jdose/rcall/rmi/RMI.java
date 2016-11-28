package com.jdose.rcall.rmi;

import com.jdose.rcall.Machine;
import com.jdose.rcall.commons.exception.SDSRuntimeException;
import com.jdose.rcall.network.ArgumentsAndCallBack;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by abhishek.i on 3/7/2015.
 */
public class RMI
{
    private static Map<Machine, Map< String, Object > > asyncProxyMap = new ConcurrentHashMap < Machine, Map < String, Object > >();

    public static synchronized < T >  T create( Machine machine, Class < ? extends RMIRemoteService > clazz )
    {
        return getAsyncProxy (  machine, clazz );
    }

    private static < T > T getAsyncProxy( Machine machine, Class < ? extends RMIRemoteService > clazz )
    {
        Map< String, Object > serviceVsAsyncMap;
        if ( ( serviceVsAsyncMap = asyncProxyMap.get( machine ) ) != null )
        {
            Object asyncProxy;
            if ( ( asyncProxy = serviceVsAsyncMap.get( clazz.getCanonicalName() ) ) != null )
            {
                return  ( T ) asyncProxy;
            }
            else
            {
                asyncProxy = generateAsyncProxy(machine, clazz );
                serviceVsAsyncMap.put( clazz.getCanonicalName(), asyncProxy  );
                return ( T ) asyncProxy;
            }
        }
        serviceVsAsyncMap = new ConcurrentHashMap< String, Object >();
        asyncProxyMap.put( machine, serviceVsAsyncMap );

        Object asyncProxy = generateAsyncProxy(machine, clazz );
        serviceVsAsyncMap.put( clazz.getCanonicalName(), asyncProxy );

        return ( T ) asyncProxy;
    }

    private static < T > T generateAsyncProxy( Machine machine, Class < ? extends RMIRemoteService > clazz )
    {
        try
        {
            return ( T ) Proxy.newProxyInstance( clazz.getClassLoader(), new Class[]{ getAsyncClass(clazz) }, new RMIInvocationHandler( clazz, machine ) );
        }
        catch ( ClassNotFoundException e )
        {
            throw new SDSRuntimeException( e );
        }
    }

    private InvocationHandler getRMIInvocationHandler( Machine machine, Class < ? extends RMIRemoteService > clazz )
    {
        return new RMIInvocationHandler( clazz, machine ) ;
    }

    private static Class < ? > getAsyncClass( Class< ? > clazz ) throws ClassNotFoundException
    {
        return Class.forName( getAsyncPackage(clazz)+ "." + getAsyncClassName( clazz ) );
    }

    private static String getAsyncPackage( Class< ? > clazz )
    {
        String canonicalName = clazz.getCanonicalName();
        return canonicalName.substring( 0, canonicalName.lastIndexOf('.')  );
    }

    private static String getAsyncClassName( Class< ? > clazz )
    {
        return clazz.getSimpleName().replace( "Service", "Async" );
    }
}

class RMIInvocationHandler implements InvocationHandler
{
    Machine machine;
    String implPackage;

    public RMIInvocationHandler( Class< ? > implPackage, Machine machine )
    {
        this.machine = machine;
        this.implPackage = implPackage.getCanonicalName();
    }

    @Override
    public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
    {
        ArgumentsAndCallBack argumentsAndCallBack =  RMIClientHelper.removeAsyncCallback( args );
        AsyncCallManager.makeAsyncCall( machine, getRMIRequest( implPackage, method, argumentsAndCallBack.getParameters()), argumentsAndCallBack.getAsyncCallback() );
        return null;
    }

    private RMIRequest getRMIRequest( String implPackage, Method method, Object[] args )
    {
        return RMIEncoder.encodeRMI( implPackage, method, args);
    }
}
