package com.zeal.assertion.api;

import com.zeal.expression.api.eval.ObjectEvaluator;
import com.zeal.expression.api.eval.StringEvaluator;
import com.zeal.expression.api.eval.primitive.*;

public final class Evaluators {

    private Evaluators() {}

    public static <S> ObjectEvaluator<S> that(S value) {
        return new ObjectEvaluator<>(value);
    }

    public static StringEvaluator that(String value) {
        return new StringEvaluator(value);
    }

    public static IntEvaluator that(int value) {
        return new IntEvaluator(value);
    }

    public static LongEvaluator that(long value) {
        return new LongEvaluator(value);
    }

    public static FloatEvaluator that(float value) {
        return new FloatEvaluator(value);
    }

    public static DoubleEvaluator that(double value) {
        return new DoubleEvaluator(value);
    }

    public static BoxedIntEvaluator that(Integer value) {
        return new BoxedIntEvaluator(value);
    }

    public static BoxedLongEvaluator that(Long value) {
        return new BoxedLongEvaluator(value);
    }

    public static BoxedFloatEvaluator that(Float value) {
        return new BoxedFloatEvaluator(value);
    }

    public static BoxedDoubleEvaluator that(Double value) {
        return new BoxedDoubleEvaluator(value);
    }
}
