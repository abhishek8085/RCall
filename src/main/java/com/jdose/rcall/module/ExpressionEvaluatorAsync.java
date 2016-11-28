package com.jdose.rcall.module;

import com.jdose.rcall.rmi.AsyncCallback;

import java.util.List;

/**
 * Created by abhishek.i on 6/13/2015.
 */
public interface ExpressionEvaluatorAsync
{
    void evaluateExpression( String expression, AsyncCallback< Object > asyncCallback);

    void evaluateExpression( List< String > expressions,AsyncCallback< Object >  asyncCallback );

    void stopExpressionEvaluation( String expression );

    void stopAllExpressionEvaluation();
}
