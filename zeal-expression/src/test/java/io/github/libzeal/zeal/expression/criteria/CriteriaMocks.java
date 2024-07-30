package io.github.libzeal.zeal.expression.criteria;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Result;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class CriteriaMocks {

    @SuppressWarnings("unchecked")
    public static Criteria<Object> criteria(final Result expectedResult) {

        Evaluation result = mock(Evaluation.class);
        doReturn(expectedResult).when(result).result();

        Criteria<Object> subCriteria = mock(Criteria.class);
        doReturn(result).when(subCriteria).evaluate(any(Object.class), any(EvaluationContext.class));

        return subCriteria;
    }
}
