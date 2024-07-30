package io.github.libzeal.zeal.expression.criteria;

import io.github.libzeal.zeal.expression.evaluation.ConjunctiveEvaluation;
import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Result;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A compound criteria where all sub-criteria must be pass for this compound criteria to pass.
 *
 * @param <T>
 *     The type of the subject.
 */
public class ConjunctiveCriteria<T> implements CompoundCriteria<T> {

    private final SkippableCriteriaList<T> subCriteria;

    /**
     * Creates a new conjunctive criteria.
     *
     * @param name
     *     The name of the criteria.
     * @param subCriteria
     *     The sub-criteria used initialize the compound criteria.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public ConjunctiveCriteria(String name, List<Criteria<T>> subCriteria) {
        requireNonNull(name);
        this.subCriteria = wrap(name, subCriteria);
    }

    private static <T> SkippableCriteriaList<T> wrap(String name, List<Criteria<T>> subCriteria) {
        return new SkippableCriteriaList<>(
            subCriteria,
            eval -> eval.result().equals(Result.FAILED),
            evaluations -> new ConjunctiveEvaluation(name, evaluations)
        );
    }

    /**
     * Creates a new conjunctive criteria with an empty default sub-criteria list.
     *
     * @param name
     *     The name of the criteria.
     *
     * @implNote The default sub-criteria list has an initial capacity of 1.
     */
    public ConjunctiveCriteria(String name) {
        this(name, new ArrayList<>(1));
    }

    @Override
    public void append(Criteria<T> criteria) {
        this.subCriteria.append(criteria);
    }

    @Override
    public void prepend(Criteria<T> criteria) {
        this.subCriteria.prepend(criteria);
    }

    @Override
    public Evaluation evaluate(final T subject, final EvaluationContext context) {
        return subCriteria.evaluate(subject, context);
    }
}
