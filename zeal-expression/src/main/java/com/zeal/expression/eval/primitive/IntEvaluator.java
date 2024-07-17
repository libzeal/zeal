package com.zeal.expression.eval.primitive;

public class IntEvaluator implements IntEvaluationBooleanExpression {

    private final int subject;
    private final IntEvaluationChain chain;

    public IntEvaluator(int subject) {
        this.subject = subject;
        this.chain = new IntEvaluationChain();
    }

    protected IntEvaluator chain(IntEvaluation evaluation) {
        chain.append(evaluation);
        return this;
    }

    public int subject() {
        return subject;
    }

    @Override
    public boolean isTrue() {
        return chain.evaluate(subject).isTrue();
    }

    public IntEvaluator satisfies(IntEvaluation evaluation) {
        return chain(evaluation);
    }

    public IntEvaluator equals(int value) {
        return satisfies(IntEvaluations.equals(value));
    }

    public IntEvaluator isLessThan(int value) {
        return satisfies(IntEvaluations.isLessThan(value));
    }

    /**
     * Same as {@link #isLessThan}
     */
    public IntEvaluator lt(int value) {
        return satisfies(IntEvaluations.lt(value));
    }

    public IntEvaluator isGreaterThan(int value) {
        return satisfies(IntEvaluations.isGreaterThan(value));
    }

    /**
     * Same as {@link #isGreaterThan}
     */
    public IntEvaluator gt(int value) {
        return satisfies(IntEvaluations.gt(value));
    }

    public IntEvaluator isLessThanOrEqualTo(int value) {
        return satisfies(IntEvaluations.isLessThanOrEqualTo(value));
    }

    /**
     * Same as {@link #isLessThanOrEqualTo}
     */
    public IntEvaluator lte(int value) {
        return satisfies(IntEvaluations.lte(value));
    }

    public IntEvaluator isGreaterThanOrEqualTo(int value) {
        return satisfies(IntEvaluations.isGreaterThanOrEqualTo(value));
    }

    /**
     * Same as {@link #isGreaterThanOrEqualTo}
     */
    public IntEvaluator gte(int value) {
        return satisfies(IntEvaluations.gte(value));
    }

    public IntEvaluator isMaxValue() {
        return satisfies(IntEvaluations.isMaxValue());
    }

    public IntEvaluator isMinValue() {
        return satisfies(IntEvaluations.isMinValue());
    }

    public IntEvaluator isZero() {
        return satisfies(IntEvaluations.isZero());
    }

    public IntEvaluator isPositive() {
        return satisfies(IntEvaluations.isPositive());
    }

    public IntEvaluator isNegative() {
        return satisfies(IntEvaluations.isNegative());
    }

    public IntEvaluator isEven() {
        return satisfies(IntEvaluations.isEven());
    }

    public IntEvaluator isOdd() {
        return satisfies(IntEvaluations.isOdd());
    }

    public IntEvaluator isNotNegative() {
        return satisfies(IntEvaluations.isNotNegative());
    }

    public IntEvaluator isNotPositive() {
        return satisfies(IntEvaluations.isNotPositive());
    }

}
