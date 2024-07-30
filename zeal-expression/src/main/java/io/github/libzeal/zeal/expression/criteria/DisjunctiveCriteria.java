package io.github.libzeal.zeal.expression.criteria;

import io.github.libzeal.zeal.expression.evaluation.DisjunctiveEvaluation;
import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Result;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class DisjunctiveCriteria<T> implements CompoundCriteria<T> {

    private final SkippableCriteriaList<T> subCriteria;

    public DisjunctiveCriteria(final String name, final List<Criteria<T>> subCriteria) {
        requireNonNull(name);
        this.subCriteria = wrap(name, subCriteria);
    }

    private static <T> SkippableCriteriaList<T> wrap(String name, List<Criteria<T>> subCriteria) {
        return new SkippableCriteriaList<>(
            subCriteria,
            eval -> eval.result().equals(Result.PASSED),
            evaluations -> new DisjunctiveEvaluation(name, evaluations)
        );
    }

    public DisjunctiveCriteria(final String name) {
        this(name, new ArrayList<>(1));
    }

    @Override
    public void append(final Criteria<T> criteria) {
        this.subCriteria.append(criteria);
    }

    @Override
    public void prepend(final Criteria<T> criteria) {
        this.subCriteria.prepend(criteria);
    }

    @Override
    public Evaluation evaluate(final T subject, final EvaluationContext context) {
        return subCriteria.evaluate(subject, context);
    }
}
