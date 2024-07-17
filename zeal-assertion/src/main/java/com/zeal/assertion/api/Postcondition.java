package com.zeal.assertion.api;

import com.zeal.assertion.exception.PostconditionFailedException;
import com.zeal.expression.BooleanExpression;

public class Postcondition {

    private static final String POSTCONDITION_FAILED_MESSAGE = "Postcondition failed";

    private Postcondition() {}

    public static void ensure(BooleanExpression condition) {
        ensure(condition, POSTCONDITION_FAILED_MESSAGE);
    }

    public static void ensure(BooleanExpression condition, String message) {

        if (condition.isFalse()) {
            throw new PostconditionFailedException(POSTCONDITION_FAILED_MESSAGE + ": " + message);
        }
    }
}
