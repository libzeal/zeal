package com.zeal.expression.api.eval.primitive;

import com.zeal.expression.eval.primitive.DoubleEvaluation;
import com.zeal.expression.eval.primitive.DoubleEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.DoubleEvaluationChain;

public class DoubleEvaluator implements DoubleEvaluationBooleanExpression {

    private final double subject;
    private final DoubleEvaluationChain chain;

    public DoubleEvaluator(double subject) {
        this.subject = subject;
        this.chain = new DoubleEvaluationChain();
    }

    protected DoubleEvaluator chain(DoubleEvaluation evaluation) {
        chain.append(evaluation);
        return this;
    }

    public double subject() {
        return subject;
    }

    @Override
    public boolean isTrue() {
        return chain.evaluate(subject);
    }

    public DoubleEvaluator satisfies(DoubleEvaluation evaluation) {
        return chain(evaluation);
    }

    public DoubleEvaluator exactlyEquals(double value) {
        return satisfies(DoubleEvaluations.exactlyEquals(value));
    }

    public DoubleEvaluator equalsWithinDelta(double value, double delta) {
        return satisfies(DoubleEvaluations.equalsWithinDelta(value, delta));
    }

    public DoubleEvaluator isLessThan(double value) {
        return satisfies(DoubleEvaluations.isLessThan(value));
    }

    public DoubleEvaluator lt(double value) {
        return satisfies(DoubleEvaluations.lt(value));
    }

    public DoubleEvaluator isGreaterThan(double value) {
        return satisfies(DoubleEvaluations.isGreaterThan(value));
    }

    public DoubleEvaluator gt(double value) {
        return satisfies(DoubleEvaluations.gt(value));
    }

    public DoubleEvaluator isLessThanOrEqualTo(double value) {
        return satisfies(DoubleEvaluations.isLessThanOrEqualTo(value));
    }

    public DoubleEvaluator lte(double value) {
        return satisfies(DoubleEvaluations.lte(value));
    }

    public DoubleEvaluator isGreaterThanOrEqualTo(double value) {
        return satisfies(DoubleEvaluations.isGreaterThanOrEqualTo(value));
    }

    public DoubleEvaluator gte(double value) {
        return satisfies(DoubleEvaluations.gte(value));
    }

    public DoubleEvaluator isMaxValue() {
        return satisfies(DoubleEvaluations.isMaxValue());
    }

    public DoubleEvaluator isMinValue() {
        return satisfies(DoubleEvaluations.isMinValue());
    }

    public DoubleEvaluator isZero() {
        return satisfies(DoubleEvaluations.isZero());
    }

    public DoubleEvaluator isPositive() {
        return satisfies(DoubleEvaluations.isPositive());
    }

    public DoubleEvaluator isNegative() {
        return satisfies(DoubleEvaluations.isNegative());
    }

    public DoubleEvaluator isNotNegative() {
        return satisfies(DoubleEvaluations.isNotNegative());
    }

    public DoubleEvaluator isNotPositive() {
        return satisfies(DoubleEvaluations.isNotPositive());
    }

    public DoubleEvaluator isFinite() {
        return satisfies(DoubleEvaluations.isFinite());
    }

    public DoubleEvaluator isInfinite() {
        return satisfies(DoubleEvaluations.isInfinite());
    }

    public DoubleEvaluator isNan() {
        return satisfies(DoubleEvaluations.isNan());
    }
}
