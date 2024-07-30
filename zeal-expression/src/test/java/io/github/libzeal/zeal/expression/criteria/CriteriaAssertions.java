package io.github.libzeal.zeal.expression.criteria;

import org.mockito.ArgumentMatcher;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CriteriaAssertions {

    public static void assertIsSkipped(final Criteria<Object> passingSubCriteria, final Object subject) {
        verify(passingSubCriteria, times(1)).evaluate(eq(subject), argThat(isSkipped()));
    }

    private static ArgumentMatcher<EvaluationContext> isSkipped() {
        return new ArgumentMatcher<EvaluationContext>() {

            @Override
            public boolean matches(final EvaluationContext context) {
                return context.skip();
            }
        };
    }

    public static void assertIsNotSkipped(final Criteria<Object> passingSubCriteria, final Object subject) {
        verify(passingSubCriteria, times(1)).evaluate(eq(subject), argThat(isNotSkipped()));
    }

    private static ArgumentMatcher<EvaluationContext> isNotSkipped() {
        return new ArgumentMatcher<EvaluationContext>() {

            @Override
            public boolean matches(final EvaluationContext context) {
                return !context.skip();
            }
        };
    }
}
