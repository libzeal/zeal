package io.github.libzeal.zeal.expression.evalulation;

import java.util.function.Predicate;

public class TerminalEvaluation<T> implements Evaluation<T> {

    private final String name;
    private final DeferredValue expected;
    private final ActualValueComputer<T> actual;
    private final Predicate<T> predicate;

    public static <A> TerminalEvaluation<A> of(String name, DeferredValue expected,
                                               ActualValueComputer<A> actual, Predicate<A> predicate) {
        return new TerminalEvaluation<>(name, expected, actual, predicate);
    }

    public TerminalEvaluation(String name, DeferredValue expected, ActualValueComputer<T> actual, Predicate<T> predicate) {
        this.name = name;
        this.expected = expected;
        this.actual = actual;
        this.predicate = predicate;
    }

    @Override
    public EvaluatedExpression evaluate(T subject, boolean skip) {

        EvaluationState state = computeEvaluationState(subject, skip);

        return new TerminalEvaluatedExpression(
                state,
                name,
                expected,
                () -> actual.compute(subject)
        );
    }

    private EvaluationState computeEvaluationState(T subject, boolean skip) {

        if (skip) {
            return EvaluationState.SKIPPED;
        }

        if (test(subject)) {
            return EvaluationState.PASSED;
        }
        else {
            return EvaluationState.FAILED;
        }
    }

    private boolean test(T subject) {
        return subject != null && predicate.test(subject);
    }
}
