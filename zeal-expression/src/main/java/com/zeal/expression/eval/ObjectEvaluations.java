package com.zeal.expression.eval;

import java.util.Objects;

public final class ObjectEvaluations {

    private ObjectEvaluations() {}

    public static <S> Evaluation<S> isNull() {
        return Evaluation.of(Objects::isNull);
    }

    public static <S> Evaluation<S> isNotNull() {
        return Evaluation.of(Objects::nonNull);
    }

    public static <S> Evaluation<S> hasType(Class<?> type) {
        return Evaluation.of(o -> o != null && o.getClass().equals(type));
    }

    public static <S> Evaluation<S> isInstanceOf(Class<?> type) {
        return Evaluation.of(o -> o != null && o.getClass().isInstance(type));
    }

    public static <S> Evaluation<S> is(Object other) {
        return Evaluation.of(o -> o != null && o == other);
    }

    public static <S> Evaluation<S> isEqualTo(Object other) {
        return Evaluation.of(o -> o != null && o.equals(other));
    }

    public static <S> Evaluation<S> hasHashCode(int hashCode) {
        return Evaluation.of(o -> o != null && o.hashCode() == hashCode);
    }
}
