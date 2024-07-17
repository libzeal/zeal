package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanResult;

public class FloatEvaluator implements FloatEvaluationBooleanExpression {

    private final float subject;
    private final FloatEvaluationChain chain;

    public FloatEvaluator(float subject) {
        this.subject = subject;
        this.chain = new FloatEvaluationChain();
    }

    protected FloatEvaluator chain(FloatEvaluation evaluation) {
        chain.append(evaluation);
        return this;
    }

    public float subject() {
        return subject;
    }

    @Override
    public BooleanResult result() {
        return chain.evaluate(subject).result();
    }
    
    public FloatEvaluator satisfies(FloatEvaluation evaluation) {
        return chain(evaluation);
    }

    public FloatEvaluator exactlyEquals(float value) {
        return satisfies(FloatEvaluations.exactlyEquals(value));
    }

    public FloatEvaluator equalsWithinDelta(float value, float delta) {
        return satisfies(FloatEvaluations.equalsWithinDelta(value, delta));
    }

    public FloatEvaluator isLessThan(float value) {
        return satisfies(FloatEvaluations.isLessThan(value));
    }

    public FloatEvaluator lt(float value) {
        return satisfies(FloatEvaluations.lt(value));
    }

    public FloatEvaluator isGreaterThan(float value) {
        return satisfies(FloatEvaluations.isGreaterThan(value));
    }

    public FloatEvaluator gt(float value) {
        return satisfies(FloatEvaluations.gt(value));
    }

    public FloatEvaluator isLessThanOrEqualTo(float value) {
        return satisfies(FloatEvaluations.isLessThanOrEqualTo(value));
    }

    public FloatEvaluator lte(float value) {
        return satisfies(FloatEvaluations.lte(value));
    }

    public FloatEvaluator isGreaterThanOrEqualTo(float value) {
        return satisfies(FloatEvaluations.isGreaterThanOrEqualTo(value));
    }

    public FloatEvaluator gte(float value) {
        return satisfies(FloatEvaluations.gte(value));
    }

    public FloatEvaluator isMaxValue() {
        return satisfies(FloatEvaluations.isMaxValue());
    }

    public FloatEvaluator isMinValue() {
        return satisfies(FloatEvaluations.isMinValue());
    }

    public FloatEvaluator isZero() {
        return satisfies(FloatEvaluations.isZero());
    }

    public FloatEvaluator isPositive() {
        return satisfies(FloatEvaluations.isPositive());
    }

    public FloatEvaluator isNegative() {
        return satisfies(FloatEvaluations.isNegative());
    }

    public FloatEvaluator isNotNegative() {
        return satisfies(FloatEvaluations.isNotNegative());
    }

    public FloatEvaluator isNotPositive() {
        return satisfies(FloatEvaluations.isNotPositive());
    }

    public FloatEvaluator isFinite() {
        return satisfies(FloatEvaluations.isFinite());
    }

    public FloatEvaluator isInfinite() {
        return satisfies(FloatEvaluations.isInfinite());
    }

    public FloatEvaluator isNan() {
        return satisfies(FloatEvaluations.isNan());
    }
}
