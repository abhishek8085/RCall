package com.jdose.rcall.network;

import com.jdose.rcall.commons.ApplicationManager;
import com.jdose.rcall.frame.FrameType;
import com.jdose.rcall.frame.FramesProvider;
import com.jdose.rcall.helper.ConfigProperties;
import com.jdose.rcall.helper.ConfigPropertiesHelper;
import com.jdose.rcall.rmi.RMICommonServlet;
import com.jdose.rcall.rmi.RMIService;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by abhishek.i on 3/18/2015.
 */
public class Test
{

    private static PrimeNumberStore primeNumberStore = new PrimeNumberStore();
    private static int counter = 1;

    public  static void main ( String[] args )
    {


/*        for (int i = 2; i < 100000; i++) {
            boolean prime = true;
            for (int p : primeNumberStore.getPrimeNumbers()) {
                if (i % p == 0) {
                    prime = false;
                    break;
                }
            }

            if (prime) {
                primeNumberStore.addPrimeNumberToStore(i,counter++);
                if ( primeNumberStore.getPrimeNumbers().contains(reverseNumber(i)) &&
                        ( reverseNumber (primeNumberStore.getPositionOfPrime( i ) )  ==  primeNumberStore.getPositionOfPrime(reverseNumber(i))) &&
                        ( reverseString( Integer.toBinaryString( i )).equals( Integer.toBinaryString( i ) ) ) &&
                        ( primeNumberStore.getPositionOfPrime( i ) == getProduct( i ) ))
                {
                    System.out.println( i+" Position = "+primeNumberStore.getPositionOfPrime( i) );
                    System.out.println( reverseNumber(i)+" Position = "+primeNumberStore.getPositionOfPrime( reverseNumber(i)) );
                }
                System.out.println(counter++);
            }

        }




    System.exit(0);*/



/*        SDSServerSocket SDSServerSocket;

try {
     SDSServerSocket = new SDSServerSocket();
    SDSServerSocket.initialise();
    SDSServerSocket.registerNetworkResponseListener(new NetworkResponseListener() {
        @Override
        public void onArrivalOfNetworkResponse(NetworkResponse networkResponse) {
            System.out.println(networkResponse.getFrameUUID());
        }
    });

    FramesProvider framesProvider = FramesProvider.getProvider();
    framesProvider.initialise();
    SerializableFrame serializableFrame = null;
    try {
        serializableFrame = framesProvider.getInstance("PingFrame");
    } catch (NoSuchFrameTypeException e) {
        e.printStackTrace();
    }

    ObjectSender objectSender = new ObjectSender("10.113.59.40", 8080);
    objectSender.initialise();
    try {
        objectSender.sendObject(serializableFrame);
        //objectSender.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
finally {

}

*/

        boolean serverMode =true;

        ApplicationManager.setIsAgentMode(true);
        ConfigPropertiesHelper.getInstance().initialise();

        ConfigPropertiesHelper.setProperty(ConfigProperties.MAX_RMI_REQUESTS, "10");

        FramesProvider.getProvider().initialise();

        NetManager.initialise( serverMode );



       RMICommonServlet rmiCommonServlet = new RMICommonServlet();

       RMIService rmiService = new RMIService();
        rmiService.registerService(rmiCommonServlet);

        //HandshakeManager handshakeManager = new HandshakeManager();

        System.setProperty("java.library.path", ".\\SDSComplete\\lib\\sigar-bin\\lib");

        try {
            //set sys_paths to null
            final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
            sysPathsField.setAccessible(true);
            sysPathsField.set(null, null);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        System.out.println("********************************************************************");
        System.out.println( System.getProperty("java.library.path") );
        System.out.println("********************************************************************");



if( serverMode ) {
    NetManager.getManager().registerNetworkRequestListener(FrameType.RMI_FRAME, rmiService);
   // NetManager.getManager().registerNetworkRequestListener(FrameType.HANDSHAKE_FRAME, handshakeManager );
}
        else {

    //AsynchCallManager.getAsynchCallManager().initialise();

    //final AAsync aAsync = RMI.create(MachineHelper.getDestinationMachines().get(0), AService.class);

    //NetManager.getManager().registerNetworkRequestListener(FrameType.RMI_FRAME, AsyncCallManager.getAsyncCallManager());


    //long currenmtTimeStamp = System.currentTimeMillis();
/*

    for (int i = 0; i < 100; i++) {
        aAsync.test("Or( Or( false, false ) ,false)", new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);


            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

    }

*/


}





    //  ExpressionProvider expressionProvider = ExpressionProvider.create();
       // expressionProvider.getExpression( "Or ( true , true )" );

/*
        aAsynch.test("hi abhishek", new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);


            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
*/
























    }


    public static Integer reverseNumber(Integer number) {
        return Integer.parseInt(reverseString(number.toString()));
    }

    public static String reverseString(String string) {
        StringBuffer stringBuffer = new StringBuffer(string);

        return stringBuffer.reverse().toString();
    }

    public static int getProduct(Integer input) {

        int product = 1;

        for (char c: input.toString().toCharArray())
        {
            product *= Character.getNumericValue( c );
        }
        return product;
    }

}


class PrimeNumberStore
{
    private Map<Integer,Integer> primeNumbersVsPosition = new HashMap<Integer,Integer>();

    public void addPrimeNumberToStore( int number, int position )
    {
        primeNumbersVsPosition.put( number, position );
    }

    public Set<Integer> getPrimeNumbers()
    {
        return primeNumbersVsPosition.keySet();
    }

    public boolean isPrimeNumberInStore( int number )
    {
        return primeNumbersVsPosition.containsKey(number);
    }

    public int getPositionOfPrime( int number )
    {
        return primeNumbersVsPosition.get( number );
    }
}