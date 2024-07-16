package com.zeal.expression.api.eval;

import com.zeal.expression.eval.Evaluation;

import java.util.Objects;

public final class ObjectEvaluations {

    private ObjectEvaluations() {}

    public static <S> Evaluation<S> isNull() {
        return Objects::isNull;
    }

    public static <S> Evaluation<S> isNotNull() {
        return Objects::nonNull;
    }

    public static <S> Evaluation<S> hasType(Class<?> type) {
        return o -> o != null && o.getClass().equals(type);
    }

    public static <S> Evaluation<S> isInstanceOf(Class<?> type) {
        return o -> o != null && o.getClass().isInstance(type);
    }

    public static <S> Evaluation<S> is(Object other) {
        return o -> o != null && o == other;
    }

    public static <S> Evaluation<S> isEqualTo(Object other) {
        return o -> o != null && o.equals(other);
    }

    public static <S> Evaluation<S> hasHashCode(int hashCode) {
        return o -> o != null && o.hashCode() == hashCode;
    }
}
