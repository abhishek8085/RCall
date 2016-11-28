package com.jdose.rcall.module;

import com.jdose.rcall.rmi.RMIRemoteService;

import java.util.List;

/**
 * Created by abhishek.i on 6/13/2015.
 */
public interface ExpressionEvaluatorService  extends RMIRemoteService
{
    Object evaluateExpression( String expression );

    Object evaluateExpression( List< String > expressions );

    void stopExpressionEvaluation( String expression );

    void stopAllExpressionEvaluation();
}
