package com.zeal.expression.api.eval.primitive;

import com.zeal.expression.eval.Evaluation;
import com.zeal.expression.eval.base.BaseObjectEvaluator;
import com.zeal.expression.eval.primitive.DoubleEvaluation;

public class BoxedDoubleEvaluator extends BaseObjectEvaluator<Double,
        BoxedDoubleEvaluator> {

    public BoxedDoubleEvaluator(Double value) {
        super(value);
    }

    private static Evaluation<Double> box(DoubleEvaluation evaluation) {
        return subject -> subject != null && evaluation.evaluate(subject);
    }

    public BoxedDoubleEvaluator exactlyEquals(double value) {
        return satisfies(box(DoubleEvaluations.exactlyEquals(value)));
    }

    public BoxedDoubleEvaluator equalsWithinDelta(double value, double delta) {
        return satisfies(box(DoubleEvaluations.equalsWithinDelta(value, delta)));
    }

    public BoxedDoubleEvaluator isLessThan(double value) {
        return satisfies(box(DoubleEvaluations.isLessThan(value)));
    }

    public BoxedDoubleEvaluator lt(double value) {
        return satisfies(box(DoubleEvaluations.lt(value)));
    }

    public BoxedDoubleEvaluator isGreaterThan(double value) {
        return satisfies(box(DoubleEvaluations.isGreaterThan(value)));
    }

    public BoxedDoubleEvaluator gt(double value) {
        return satisfies(box(DoubleEvaluations.gt(value)));
    }

    public BoxedDoubleEvaluator isLessThanOrEqualTo(double value) {
        return satisfies(box(DoubleEvaluations.isLessThanOrEqualTo(value)));
    }

    public BoxedDoubleEvaluator lte(double value) {
        return satisfies(box(DoubleEvaluations.lte(value)));
    }

    public BoxedDoubleEvaluator isGreaterThanOrEqualTo(double value) {
        return satisfies(box(DoubleEvaluations.isGreaterThanOrEqualTo(value)));
    }

    public BoxedDoubleEvaluator gte(double value) {
        return satisfies(box(DoubleEvaluations.gte(value)));
    }

    public BoxedDoubleEvaluator isMaxValue() {
        return satisfies(box(DoubleEvaluations.isMaxValue()));
    }

    public BoxedDoubleEvaluator isMinValue() {
        return satisfies(box(DoubleEvaluations.isMinValue()));
    }

    public BoxedDoubleEvaluator isZero() {
        return satisfies(box(DoubleEvaluations.isZero()));
    }

    public BoxedDoubleEvaluator isPositive() {
        return satisfies(box(DoubleEvaluations.isPositive()));
    }

    public BoxedDoubleEvaluator isNegative() {
        return satisfies(box(DoubleEvaluations.isNegative()));
    }

    public BoxedDoubleEvaluator isNotNegative() {
        return satisfies(box(DoubleEvaluations.isNotNegative()));
    }

    public BoxedDoubleEvaluator isNotPositive() {
        return satisfies(box(DoubleEvaluations.isNotPositive()));
    }

    public BoxedDoubleEvaluator isFinite() {
        return satisfies(box(DoubleEvaluations.isFinite()));
    }

    public BoxedDoubleEvaluator isInfinite() {
        return satisfies(box(DoubleEvaluations.isInfinite()));
    }

    public BoxedDoubleEvaluator isNan() {
        return satisfies(box(DoubleEvaluations.isNan()));
    }
}
