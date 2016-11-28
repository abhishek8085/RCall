package com.jdose.rcall.network;

import com.jdose.rcall.rmi.AsyncCallback;

public class ArgumentsAndCallBack
    {
        private Object[] parameters;
        private AsyncCallback asyncCallback;
        private Class< ? > returnType;
        public ArgumentsAndCallBack( Object[] parameters, AsyncCallback asyncCallback, Class< ? > returnType )
        {
            this.asyncCallback = asyncCallback;
            this.parameters = parameters;
            this.returnType = returnType;
        }

        public AsyncCallback getAsyncCallback() 
        {
            return asyncCallback;
        }

        public Object[] getParameters() 
        {
            return parameters;
        }

        public Class< ? > getReturnType()
        {
            return returnType;
        }
    }