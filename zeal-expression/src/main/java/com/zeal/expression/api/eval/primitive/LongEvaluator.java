package com.zeal.expression.api.eval.primitive;

import com.zeal.expression.eval.primitive.FloatEvaluation;
import com.zeal.expression.eval.primitive.LongEvaluation;
import com.zeal.expression.eval.primitive.LongEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.LongEvaluationChain;

public class LongEvaluator implements LongEvaluationBooleanExpression {

    private final long subject;
    private final LongEvaluationChain chain;

    public LongEvaluator(long subject) {
        this.subject = subject;
        this.chain = new LongEvaluationChain();
    }

    protected LongEvaluator chain(LongEvaluation evaluation) {
        chain.append(evaluation);
        return this;
    }

    public long subject() {
        return subject;
    }

    @Override
    public boolean isTrue() {
        return chain.evaluate(subject);
    }

    public LongEvaluator satisfies(LongEvaluation evaluation) {
        return chain(evaluation);
    }

    public LongEvaluator equals(long value) {
        return satisfies(LongEvaluations.equals(value));
    }

    public LongEvaluator isLessThan(long value) {
        return satisfies(LongEvaluations.isLessThan(value));
    }

    /**
     * Same as {@link #isLessThan}
     */
    public LongEvaluator lt(long value) {
        return satisfies(LongEvaluations.lt(value));
    }

    public LongEvaluator isGreaterThan(long value) {
        return satisfies(LongEvaluations.isGreaterThan(value));
    }

    /**
     * Same as {@link #isGreaterThan}
     */
    public LongEvaluator gt(long value) {
        return satisfies(LongEvaluations.gt(value));
    }

    public LongEvaluator isLessThanOrEqualTo(long value) {
        return satisfies(LongEvaluations.isLessThanOrEqualTo(value));
    }

    /**
     * Same as {@link #isLessThanOrEqualTo}
     */
    public LongEvaluator lte(long value) {
        return satisfies(LongEvaluations.lte(value));
    }

    public LongEvaluator isGreaterThanOrEqualTo(long value) {
        return satisfies(LongEvaluations.isGreaterThanOrEqualTo(value));
    }

    /**
     * Same as {@link #isGreaterThanOrEqualTo}
     */
    public LongEvaluator gte(long value) {
        return satisfies(LongEvaluations.gte(value));
    }

    public LongEvaluator isMaxValue() {
        return satisfies(LongEvaluations.isMaxValue());
    }

    public LongEvaluator isMinValue() {
        return satisfies(LongEvaluations.isMinValue());
    }

    public LongEvaluator isZero() {
        return satisfies(LongEvaluations.isZero());
    }

    public LongEvaluator isPositive() {
        return satisfies(LongEvaluations.isPositive());
    }

    public LongEvaluator isNegative() {
        return satisfies(LongEvaluations.isNegative());
    }

    public LongEvaluator isEven() {
        return satisfies(LongEvaluations.isEven());
    }

    public LongEvaluator isOdd() {
        return satisfies(LongEvaluations.isOdd());
    }

    public LongEvaluator isNotNegative() {
        return satisfies(LongEvaluations.isNotNegative());
    }

    public LongEvaluator isNotPositive() {
        return satisfies(LongEvaluations.isNotPositive());
    }
}
