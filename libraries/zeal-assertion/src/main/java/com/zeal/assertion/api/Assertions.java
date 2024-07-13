package com.zeal.assertion.api;

import com.zeal.assertion.check.ObjectCheckableAssertion;
import com.zeal.assertion.exception.PostconditionFailedException;
import com.zeal.assertion.exception.PreconditionFailedException;
import com.zeal.assertion.exception.VerificationFailedException;
import com.zeal.expression.BooleanExpression;
import com.zeal.expression.subject.SingleSubjectBooleanExpression;

public final class Assertions {

    private Assertions() {}

    public static void require(BooleanExpression condition) {
        require(condition, "Precondition failed");
    }

    public static void require(BooleanExpression condition, String message) {

        if (condition.isFalse()) {
            throw new PreconditionFailedException(message);
        }
    }

    public static void ensure(BooleanExpression condition) {
        ensure(condition, "Postcondition failed");
    }

    public static void ensure(BooleanExpression condition, String message) {

        if (condition.isFalse()) {
            throw new PostconditionFailedException(message);
        }
    }

    public static <S> S verify(SingleSubjectBooleanExpression<S> condition, String message) {

        if (condition.isFalse()) {
            throw new VerificationFailedException(message);
        }

        return condition.subject();
    }

    public static <S> S verify(SingleSubjectBooleanExpression<S> condition) {
        return verify(condition, "Verification failed");
    }

    // TODO: [verify] Add unboxed primitive types

    public static <S> ObjectCheckableAssertion<S> check(SingleSubjectBooleanExpression<S> condition) {
        return new ObjectCheckableAssertion<>(condition);
    }

    // TODO: [check] Add unboxed primitive types
}
