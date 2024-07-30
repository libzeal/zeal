package io.github.libzeal.zeal.expression.criteria;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class SkippableCriteriaList<T> {

    private final List<Criteria<T>> criteria;
    private final Predicate<Evaluation> skipTest;
    private final EvaluatorCreator creator;

    public SkippableCriteriaList(List<Criteria<T>> criteria, Predicate<Evaluation> skipTest,
                                 EvaluatorCreator creator) {
        this.criteria = requireNonNull(criteria);
        this.skipTest = requireNonNull(skipTest);
        this.creator = requireNonNull(creator);
    }

    public void append(Criteria<T> criteria) {
        this.criteria.add(criteria);
    }

    public void prepend(Criteria<T> criteria) {
        this.criteria.add(0, criteria);
    }

    public Evaluation evaluate(final T subject, final EvaluationContext context) {

        final List<Evaluation> evaluatedChildren = new ArrayList<>();
        EvaluationContext currentContext = context;

        for (Criteria<T> criterion : criteria) {

            final Evaluation result = criterion.evaluate(subject, currentContext);

            evaluatedChildren.add(result);

            if (skipTest.test(result)) {
                currentContext = currentContext.withSkip();
            }
        }

        return creator.create(evaluatedChildren);
    }

    @FunctionalInterface
    public interface EvaluatorCreator {
        Evaluation create(List<Evaluation> evaluations);
    }
}
