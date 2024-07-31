package io.github.libzeal.zeal.expression.predicate.unary;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Rationale;
import io.github.libzeal.zeal.expression.predicate.EvaluatedPredicate;

import java.util.ArrayList;
import java.util.List;

/**
 * An operation that is composed of any number of predicate. The nature of the evaluation depends on the nature of
 * the specific compound predicate. For example, if the compound predicate is conjunctive, then all predicate must
 * be true for the compound predicate to be true; other implementations may have different behavior.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface CompoundUnaryPredicate<T> extends UnaryPredicate<T> {

    /**
     * Appends the supplied predicate to this compound predicate.
     *
     * @param operation
     *     The operation to append.
     */
    void append(UnaryPredicate<T> operation);

    /**
     * Prepends the supplied predicate to this compound predicate.
     *
     * @param operation
     *     The operation to prepend.
     */
    void prepend(UnaryPredicate<T> operation);
}
