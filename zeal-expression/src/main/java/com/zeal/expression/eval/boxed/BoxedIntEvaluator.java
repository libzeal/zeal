package com.zeal.expression.eval.boxed;

import com.zeal.expression.eval.primitive.IntEvaluations;
import com.zeal.expression.eval.Evaluation;
import com.zeal.expression.eval.base.BaseObjectEvaluator;
import com.zeal.expression.eval.primitive.IntEvaluation;

public class BoxedIntEvaluator extends BaseObjectEvaluator<Integer,
        BoxedIntEvaluator> {

    public BoxedIntEvaluator(Integer value) {
        super(value);
    }

    private static Evaluation<Integer> box(IntEvaluation evaluation) {
        return Evaluation.of(
                subject -> subject != null && evaluation.evaluate(subject).isTrue()
        );
    }

    public BoxedIntEvaluator equals(int value) {
        return satisfies(box(IntEvaluations.equals(value)));
    }

    public BoxedIntEvaluator isLessThan(int value) {
        return satisfies(box(IntEvaluations.isLessThan(value)));
    }

    /**
     * Same as {@link #isLessThan}
     */
    public BoxedIntEvaluator lt(int value) {
        return satisfies(box(IntEvaluations.lt(value)));
    }

    public BoxedIntEvaluator isGreaterThan(int value) {
        return satisfies(box(IntEvaluations.isGreaterThan(value)));
    }

    /**
     * Same as {@link #isGreaterThan}
     */
    public BoxedIntEvaluator gt(int value) {
        return satisfies(box(IntEvaluations.gt(value)));
    }

    public BoxedIntEvaluator isLessThanOrEqualTo(int value) {
        return satisfies(box(IntEvaluations.isLessThanOrEqualTo(value)));
    }

    /**
     * Same as {@link #isLessThanOrEqualTo}
     */
    public BoxedIntEvaluator lte(int value) {
        return satisfies(box(IntEvaluations.lte(value)));
    }

    public BoxedIntEvaluator isGreaterThanOrEqualTo(int value) {
        return satisfies(box(IntEvaluations.isGreaterThanOrEqualTo(value)));
    }

    /**
     * Same as {@link #isGreaterThanOrEqualTo}
     */
    public BoxedIntEvaluator gte(int value) {
        return satisfies(box(IntEvaluations.gte(value)));
    }

    public BoxedIntEvaluator isMaxValue() {
        return satisfies(box(IntEvaluations.isMaxValue()));
    }

    public BoxedIntEvaluator isMinValue() {
        return satisfies(box(IntEvaluations.isMinValue()));
    }

    public BoxedIntEvaluator isZero() {
        return satisfies(box(IntEvaluations.isZero()));
    }

    public BoxedIntEvaluator isPositive() {
        return satisfies(box(IntEvaluations.isPositive()));
    }

    public BoxedIntEvaluator isNegative() {
        return satisfies(box(IntEvaluations.isNegative()));
    }

    public BoxedIntEvaluator isEven() {
        return satisfies(box(IntEvaluations.isEven()));
    }

    public BoxedIntEvaluator isOdd() {
        return satisfies(box(IntEvaluations.isOdd()));
    }

    public BoxedIntEvaluator isNotNegative() {
        return satisfies(box(IntEvaluations.isNotNegative()));
    }

    public BoxedIntEvaluator isNotPositive() {
        return satisfies(box(IntEvaluations.isNotPositive()));
    }

}
