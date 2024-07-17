package com.zeal.assertion.api;

import com.zeal.assertion.exception.VerificationFailedException;
import com.zeal.expression.eval.Evaluator;
import com.zeal.expression.eval.primitive.DoubleEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.FloatEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.IntEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.LongEvaluationBooleanExpression;

public class Verification {

    private static final String VERIFICATION_FAILED_MESSAGE = "Verification failed";

    private Verification() {}

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
}
