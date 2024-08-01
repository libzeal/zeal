package io.github.libzeal.zeal.expression.predicate.unary;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.SimpleRationale;
import io.github.libzeal.zeal.expression.predicate.EvaluatedPredicate;

/**
 * A predicate evaluates a single subject.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
@FunctionalInterface
public interface UnaryPredicate<T> {

    /**
     * Obtains the name of the predicate.
     *
     * @return The name of the predicate.
     */
    default String name() {
        return "unnamed";
    }

    /**
     * Evaluates the supplied subject against this predicate.
     *
     * @param subject
     *     The subject to evaluate.
     *
     * @return The evaluation of the subject against this predicate.
     */
    Evaluation evaluate(T subject);

    /**
     * Evaluates the predicate as skipped (does not perform the evaluation).
     *
     * @return A skipped evaluation.
     */
    default Evaluation skip() {
        return EvaluatedPredicate.skipped(name(), SimpleRationale.skipped());
    }
}
