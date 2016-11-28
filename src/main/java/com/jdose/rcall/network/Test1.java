package com.jdose.rcall.network;

import com.jdose.rcall.commons.ApplicationManager;
import com.jdose.rcall.frame.FrameType;
import com.jdose.rcall.frame.FramesProvider;
import com.jdose.rcall.helper.ConfigProperties;
import com.jdose.rcall.helper.ConfigPropertiesHelper;
import com.jdose.rcall.helper.MachineHelper;
import com.jdose.rcall.module.ExpressionEvaluatorAsync;
import com.jdose.rcall.module.ExpressionEvaluatorService;
import com.jdose.rcall.rmi.AsyncCallManager;
import com.jdose.rcall.rmi.AsyncCallback;
import com.jdose.rcall.rmi.RMI;

/**
 * Created by abhishek.i on 3/18/2015.
 */
public class Test1
{

    static int  k = 0;

    public  static void main ( String[] args )
    {



        boolean serverMode =false;

        ApplicationManager.setIsAgentMode(false);

        ConfigPropertiesHelper.getInstance().initialise();

        ConfigPropertiesHelper.setProperty(ConfigProperties.MAX_RMI_REQUESTS, "10");

        FramesProvider.getProvider().initialise();

        NetManager.initialise(serverMode);


        ExpressionEvaluatorAsync aAsync;

        aAsync = RMI.create(MachineHelper.getDestinationMachines().get(0), ExpressionEvaluatorService.class);

        NetManager.getManager().registerNetworkRequestListener(FrameType.RMI_FRAME, AsyncCallManager.getAsyncCallManager());



        for (int i = 0; i < 1; i++) {
            aAsync.evaluateExpression("CpuInfoFun()", new AsyncCallback<Object>() {

                @Override
                public void onSuccess(Object result) {
                    System.out.println(result);


                }

                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("here");
                    throwable.printStackTrace();
                }
            });

        }




 /*       FunctionRegister functionRegister = new FunctionRegister();
        functionRegister.initialise();

        ExpressionProvider expressionProvider = ExpressionProvider.create();

        expressionProvider.registerExpressionMatcher( new FunctionMatcher(functionRegister) );
        expressionProvider.registerExpressionMatcher(new BooleanMatcher());


        expressionProvider.getExpression( "CpuInfoFun()" ).evaluate(null).toString();*/















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
















/*        try {
            HandshakeManager.getHandshakeManager().performHandshake();
        } catch (HandshakeException e) {
            e.printStackTrace();
        }*/

        //RMICommonServlet rmiCommonServlet = new RMICommonServlet();

       //RMIService rmiService = new RMIService();
        //rmiService.registerService(rmiCommonServlet);

       //HandshakeManager handshakeManager = new HandshakeManager();
        //NetManager.getManager().registerNetworkRequestListener(FrameType.HANDSHAKE_FRAME, handshakeManager);

if( serverMode ) {
    //NetManager.getManager().registerNetworkRequestListener(FrameType.RMI_FRAME, rmiService);
}
        else {

    //AsynchCallManager.getAsynchCallManager().initialise();

     //AAsync aAsync = RMI.create( MachineHelper.getDestinationMachines().get(0), AService.class );
    // = RMI.create( MachineHelper.getDestinationMachines().get(0), AService.class );

    // aAsync = RMI.create( MachineHelper.getDestinationMachines().get(0), AService.class );

    /*
    final long currenmtTimeStamp = System.currentTimeMillis();

    for (int i = 0; i < 100000; i++) {
        aAsync.test("Or( Or( false, false ) ,false)", new AsyncCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //System.out.println(result);

                if( Test1.k++ == 99999   )
                System.out.println(System.currentTimeMillis() - currenmtTimeStamp);

            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

    }
*/



    // final long currenmtTimeStamp = System.currentTimeMillis();

    //if (Test1.k++ == 99999)
    // System.out.println(System.currentTimeMillis() - currenmtTimeStamp);






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
}
