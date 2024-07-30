package io.github.libzeal.zeal.expression.criteria;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;

/**
 * A condition that accepts a subject and results in an evaluation.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface Criteria<T> {

    /**
     * Evaluates the supplied subject against this criteria.
     *
     * @param subject
     *     The subject to evaluate.
     * @param context
     *     The context of the evaluation.
     *
     * @return The evaluation of the subject against this criteria.
     */
    Evaluation evaluate(T subject, EvaluationContext context);
}
