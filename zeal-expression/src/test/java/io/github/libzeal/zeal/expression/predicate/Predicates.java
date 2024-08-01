package io.github.libzeal.zeal.expression.predicate;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Result;
import io.github.libzeal.zeal.expression.evaluation.SimpleRationale;
import io.github.libzeal.zeal.expression.predicate.unary.UnaryPredicate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Predicates {

    private Predicates() {}

    public static UnaryPredicate<Object> unaryPredicate(final Result expectedResult) {
        return unaryPredicate("test predicate", expectedResult);
    }

    @SuppressWarnings("unchecked")
    public static UnaryPredicate<Object> unaryPredicate(final String name, final Result expectedResult) {

        Evaluation evaluation = mock(Evaluation.class);
        doReturn(expectedResult).when(evaluation).result();
        doReturn(name).when(evaluation).name();

        UnaryPredicate<Object> predicate = mock(UnaryPredicate.class);
        doReturn(evaluation).when(predicate).evaluate(any(Object.class));
        doReturn(name).when(predicate).name();
        doReturn(EvaluatedPredicate.skipped(name, SimpleRationale.skipped())).when(predicate).skip();

        return predicate;
    }
}
