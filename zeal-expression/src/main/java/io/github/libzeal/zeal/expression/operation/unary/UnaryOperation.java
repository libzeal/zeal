package io.github.libzeal.zeal.expression.operation.unary;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Rationale;
import io.github.libzeal.zeal.expression.operation.EvaluatedOperation;

/**
 * An operation that accepts a single subject and results in an evaluation.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
@FunctionalInterface
public interface UnaryOperation<T> {

    /**
     * Evaluates the supplied subject against this operation.
     *
     * @param subject
     *     The subject to evaluate.
     *
     * @return The evaluation of the subject against this operation.
     */
    Evaluation evaluate(T subject);

    /**
     * Obtains the name of the operation.
     *
     * @return The name of the operation.
     */
    default String name() {
        return "unnamed";
    }

    /**
     * Evaluates the operation as a skipped operation.
     *
     * @param subject
     *     The subject of the evaluation.
     *
     * @return A skipped evaluation.
     */
    default Evaluation evaluateAsSkipped(T subject) {
        return EvaluatedOperation.skipped(name(), Rationale::skipped);
    }
}
