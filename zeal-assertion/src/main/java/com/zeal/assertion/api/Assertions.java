package com.zeal.assertion.api;

import com.zeal.assertion.check.*;
import com.zeal.assertion.exception.PostconditionFailedException;
import com.zeal.assertion.exception.PreconditionFailedException;
import com.zeal.assertion.exception.VerificationFailedException;
import com.zeal.expression.BooleanExpression;
import com.zeal.expression.eval.Evaluator;
import com.zeal.expression.eval.primitive.DoubleEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.FloatEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.IntEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.LongEvaluationBooleanExpression;

public final class Assertions {

    private static final String PRECONDITION_FAILED_MESSAGE = "Precondition failed";
    private static final String POSTCONDITION_FAILED_MESSAGE = "Postcondition failed";
    private static final String VERIFICATION_FAILED_MESSAGE = "Verification failed";

    private Assertions() {}

    public static void require(BooleanExpression condition) {
        require(condition, PRECONDITION_FAILED_MESSAGE);
    }

    public static void require(BooleanExpression condition, String message) {

        if (condition.isFalse()) {
            throw new PreconditionFailedException(message);
        }
    }

    public static void ensure(BooleanExpression condition) {
        ensure(condition, POSTCONDITION_FAILED_MESSAGE);
    }

    public static void ensure(BooleanExpression condition, String message) {

        if (condition.isFalse()) {
            throw new PostconditionFailedException(message);
        }
    }

    // ------------------------------------------------------------------------
    // verify
    // ------------------------------------------------------------------------

    public static <S> S verify(Evaluator<S> condition, String message) {

        if (condition.isFalse()) {
            throw new VerificationFailedException(message);
        }

        return condition.subject();
    }

    public static <S> S verify(Evaluator<S> condition) {
        return verify(condition, VERIFICATION_FAILED_MESSAGE);
    }

    public static int verify(IntEvaluationBooleanExpression condition,
                             String message) {

        if (condition.isFalse()) {
            throw new VerificationFailedException(message);
        }

        return condition.subject();
    }

    public static int verify(IntEvaluationBooleanExpression condition) {
        return verify(condition, VERIFICATION_FAILED_MESSAGE);
    }

    public static long verify(LongEvaluationBooleanExpression condition, String message) {

        if (condition.isFalse()) {
            throw new VerificationFailedException(message);
        }

        return condition.subject();
    }

    public static long verify(LongEvaluationBooleanExpression condition) {
        return verify(condition, VERIFICATION_FAILED_MESSAGE);
    }

    public static float verify(FloatEvaluationBooleanExpression condition,
                               String message) {

        if (condition.isFalse()) {
            throw new VerificationFailedException(message);
        }

        return condition.subject();
    }

    public static float verify(FloatEvaluationBooleanExpression condition) {
        return verify(condition, VERIFICATION_FAILED_MESSAGE);
    }

    public static double verify(DoubleEvaluationBooleanExpression condition,
                                String message) {

        if (condition.isFalse()) {
            throw new VerificationFailedException(message);
        }

        return condition.subject();
    }

    public static double verify(DoubleEvaluationBooleanExpression condition) {
        return verify(condition, VERIFICATION_FAILED_MESSAGE);
    }

    // TODO: [verify] Add byte
    // TODO: [verify] Add short
    // TODO: [verify] Add char
    // TODO: [verify] Add boolean

    // ------------------------------------------------------------------------
    // check
    // ------------------------------------------------------------------------

    public static <S> ObjectCheckableAssertion<S> check(Evaluator<S> condition) {
        return new ObjectCheckableAssertion<>(condition);
    }

    public static IntCheckableAssertion check(IntEvaluationBooleanExpression condition) {
        return new IntCheckableAssertion(condition);
    }

    public static LongCheckableAssertion check(LongEvaluationBooleanExpression condition) {
        return new LongCheckableAssertion(condition);
    }

    public static FloatCheckableAssertion check(FloatEvaluationBooleanExpression condition) {
        return new FloatCheckableAssertion(condition);
    }

    public static DoubleCheckableAssertion check(DoubleEvaluationBooleanExpression condition) {
        return new DoubleCheckableAssertion(condition);
    }

    // TODO: [check] Add byte
    // TODO: [check] Add short
    // TODO: [check] Add char
    // TODO: [check] Add boolean
}
