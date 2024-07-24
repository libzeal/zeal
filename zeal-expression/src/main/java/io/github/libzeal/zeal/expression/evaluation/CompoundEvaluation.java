package io.github.libzeal.zeal.expression.evaluation;

import java.util.ArrayList;
import java.util.List;

public class CompoundEvaluation<T> implements Evaluation<T> {

    private final String name;
    private final List<Evaluation<T>> children;

    public CompoundEvaluation(String name, List<Evaluation<T>> children) {
        this.name = name;
        this.children = children;
    }

    public CompoundEvaluation(String name) {
        this(name, new ArrayList<>(1));
    }

    public void append(Evaluation<T> evaluation) {
        this.children.add(evaluation);
    }

    public void prepend(Evaluation<T> evaluation) {
        this.children.add(0, evaluation);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public EvaluatedExpression evaluate(final T subject, final boolean skip) {

        boolean failureFound = skip;
        final List<EvaluatedExpression> evaluatedChildren = new ArrayList<>();

        for (Evaluation<T> child: children) {

            final EvaluatedExpression result = child.evaluate(subject, failureFound);

            evaluatedChildren.add(result);

            if (result.result().equals(Result.FAILED)) {
                failureFound = true;
            }
        }

        return new CompoundEvaluatedExpression(name, evaluatedChildren);
    }
}
