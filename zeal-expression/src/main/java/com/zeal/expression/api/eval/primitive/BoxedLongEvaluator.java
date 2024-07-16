package com.zeal.expression.api.eval.primitive;

import com.zeal.expression.eval.Evaluation;
import com.zeal.expression.eval.base.BaseObjectEvaluator;
import com.zeal.expression.eval.primitive.IntEvaluation;
import com.zeal.expression.eval.primitive.LongEvaluation;
import com.zeal.expression.eval.primitive.LongEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.LongEvaluationChain;

public class BoxedLongEvaluator extends BaseObjectEvaluator<Long,
        BoxedLongEvaluator> {

    public BoxedLongEvaluator(Long value) {
        super(value);
    }

    private static Evaluation<Long> box(LongEvaluation evaluation) {
        return subject -> subject != null && evaluation.evaluate(subject);
    }

    public BoxedLongEvaluator equals(long value) {
        return satisfies(box(LongEvaluations.equals(value)));
    }

    public BoxedLongEvaluator isLessThan(long value) {
        return satisfies(box(LongEvaluations.isLessThan(value)));
    }

    /**
     * Same as {@link #isLessThan}
     */
    public BoxedLongEvaluator lt(long value) {
        return satisfies(box(LongEvaluations.lt(value)));
    }

    public BoxedLongEvaluator isGreaterThan(long value) {
        return satisfies(box(LongEvaluations.isGreaterThan(value)));
    }

    /**
     * Same as {@link #isGreaterThan}
     */
    public BoxedLongEvaluator gt(long value) {
        return satisfies(box(LongEvaluations.gt(value)));
    }

    public BoxedLongEvaluator isLessThanOrEqualTo(long value) {
        return satisfies(box(LongEvaluations.isLessThanOrEqualTo(value)));
    }

    /**
     * Same as {@link #isLessThanOrEqualTo}
     */
    public BoxedLongEvaluator lte(long value) {
        return satisfies(box(LongEvaluations.lte(value)));
    }

    public BoxedLongEvaluator isGreaterThanOrEqualTo(long value) {
        return satisfies(box(LongEvaluations.isGreaterThanOrEqualTo(value)));
    }

    /**
     * Same as {@link #isGreaterThanOrEqualTo}
     */
    public BoxedLongEvaluator gte(long value) {
        return satisfies(box(LongEvaluations.gte(value)));
    }

    public BoxedLongEvaluator isMaxValue() {
        return satisfies(box(LongEvaluations.isMaxValue()));
    }

    public BoxedLongEvaluator isMinValue() {
        return satisfies(box(LongEvaluations.isMinValue()));
    }

    public BoxedLongEvaluator isZero() {
        return satisfies(box(LongEvaluations.isZero()));
    }

    public BoxedLongEvaluator isPositive() {
        return satisfies(box(LongEvaluations.isPositive()));
    }

    public BoxedLongEvaluator isNegative() {
        return satisfies(box(LongEvaluations.isNegative()));
    }

    public BoxedLongEvaluator isEven() {
        return satisfies(box(LongEvaluations.isEven()));
    }

    public BoxedLongEvaluator isOdd() {
        return satisfies(box(LongEvaluations.isOdd()));
    }

    public BoxedLongEvaluator isNotNegative() {
        return satisfies(box(LongEvaluations.isNotNegative()));
    }

    public BoxedLongEvaluator isNotPositive() {
        return satisfies(box(LongEvaluations.isNotPositive()));
    }
}
