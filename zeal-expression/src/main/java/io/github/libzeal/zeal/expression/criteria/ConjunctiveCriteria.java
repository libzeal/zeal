package io.github.libzeal.zeal.expression.criteria;

import io.github.libzeal.zeal.expression.evaluation.ConjunctiveEvaluation;
import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Result;

import java.util.ArrayList;
import java.util.List;

public class ConjunctiveCriteria<T> implements CompoundCriteria<T> {

    private final String name;
    private final List<Criteria<T>> criteria;

    public ConjunctiveCriteria(String name, List<Criteria<T>> criteria) {
        this.name = name;
        this.criteria = criteria;
    }

    public ConjunctiveCriteria(String name) {
        this(name, new ArrayList<>(1));
    }

    @Override
    public void append(Criteria<T> criteria) {
        this.criteria.add(criteria);
    }

    @Override
    public void prepend(Criteria<T> criteria) {
        this.criteria.add(0, criteria);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Evaluation evaluate(final T subject, final EvaluationContext context) {

        final List<Evaluation> evaluatedChildren = new ArrayList<>();
        EvaluationContext currentContext = context;

        for (Criteria<T> criterion: criteria) {

            final Evaluation result = criterion.evaluate(subject, currentContext);

            evaluatedChildren.add(result);

            if (result.result().equals(Result.FAILED)) {
                currentContext = currentContext.withSkip();
            }
        }

        return new ConjunctiveEvaluation(name, evaluatedChildren);
    }
}
