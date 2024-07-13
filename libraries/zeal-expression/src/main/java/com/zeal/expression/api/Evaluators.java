package com.zeal.expression.api;

import com.zeal.expression.subject.values.ObjectEvaluator;
import com.zeal.expression.subject.values.StringEvaluator;

public final class Evaluators {

    private Evaluators() {}

    public static StringEvaluator that(String value) {
        return new StringEvaluator(value);
    }

    public static <T> ObjectEvaluator<T> that(T value) {
        return new ObjectEvaluator<>(value);
    }
}
