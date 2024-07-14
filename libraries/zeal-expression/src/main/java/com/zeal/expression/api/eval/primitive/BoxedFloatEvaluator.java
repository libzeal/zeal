package com.zeal.expression.api.eval.primitive;

import com.zeal.expression.eval.Evaluation;
import com.zeal.expression.eval.base.BaseObjectEvaluator;
import com.zeal.expression.eval.primitive.DoubleEvaluation;
import com.zeal.expression.eval.primitive.FloatEvaluation;
import com.zeal.expression.eval.primitive.FloatEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.FloatEvaluationChain;

public class BoxedFloatEvaluator extends BaseObjectEvaluator<Float,
        BoxedFloatEvaluator> {

    public BoxedFloatEvaluator(Float value) {
        super(value);
    }

    private static Evaluation<Float> box(FloatEvaluation evaluation) {
        return subject -> subject != null && evaluation.evaluate(subject);
    }

    public BoxedFloatEvaluator exactlyEquals(float value) {
        return satisfies(box(FloatEvaluations.exactlyEquals(value)));
    }

    public BoxedFloatEvaluator equalsWithinDelta(float value, float delta) {
        return satisfies(box(FloatEvaluations.equalsWithinDelta(value, delta)));
    }

    public BoxedFloatEvaluator isLessThan(float value) {
        return satisfies(box(FloatEvaluations.isLessThan(value)));
    }

    public BoxedFloatEvaluator lt(float value) {
        return satisfies(box(FloatEvaluations.lt(value)));
    }

    public BoxedFloatEvaluator isGreaterThan(float value) {
        return satisfies(box(FloatEvaluations.isGreaterThan(value)));
    }

    public BoxedFloatEvaluator gt(float value) {
        return satisfies(box(FloatEvaluations.gt(value)));
    }

    public BoxedFloatEvaluator isLessThanOrEqualTo(float value) {
        return satisfies(box(FloatEvaluations.isLessThanOrEqualTo(value)));
    }

    public BoxedFloatEvaluator lte(float value) {
        return satisfies(box(FloatEvaluations.lte(value)));
    }

    public BoxedFloatEvaluator isGreaterThanOrEqualTo(float value) {
        return satisfies(box(FloatEvaluations.isGreaterThanOrEqualTo(value)));
    }

    public BoxedFloatEvaluator gte(float value) {
        return satisfies(box(FloatEvaluations.gte(value)));
    }

    public BoxedFloatEvaluator isMaxValue() {
        return satisfies(box(FloatEvaluations.isMaxValue()));
    }

    public BoxedFloatEvaluator isMinValue() {
        return satisfies(box(FloatEvaluations.isMinValue()));
    }

    public BoxedFloatEvaluator isZero() {
        return satisfies(box(FloatEvaluations.isZero()));
    }

    public BoxedFloatEvaluator isPositive() {
        return satisfies(box(FloatEvaluations.isPositive()));
    }

    public BoxedFloatEvaluator isNegative() {
        return satisfies(box(FloatEvaluations.isNegative()));
    }

    public BoxedFloatEvaluator isNotNegative() {
        return satisfies(box(FloatEvaluations.isNotNegative()));
    }

    public BoxedFloatEvaluator isNotPositive() {
        return satisfies(box(FloatEvaluations.isNotPositive()));
    }

    public BoxedFloatEvaluator isFinite() {
        return satisfies(box(FloatEvaluations.isFinite()));
    }

    public BoxedFloatEvaluator isInfinite() {
        return satisfies(box(FloatEvaluations.isInfinite()));
    }

    public BoxedFloatEvaluator isNan() {
        return satisfies(box(FloatEvaluations.isNan()));
    }
}
