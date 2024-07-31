package io.github.libzeal.zeal.expression.operation;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Result;
import io.github.libzeal.zeal.expression.operation.unary.UnaryOperation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TestOperations {

    @SuppressWarnings("unchecked")
    public static UnaryOperation<Object> unaryOperation(final Result expectedResult) {

        Evaluation result = mock(Evaluation.class);
        doReturn(expectedResult).when(result).result();

        UnaryOperation<Object> subOperation = mock(UnaryOperation.class);
        doReturn(result).when(subOperation).evaluate(any(Object.class));

        return subOperation;
    }
}
