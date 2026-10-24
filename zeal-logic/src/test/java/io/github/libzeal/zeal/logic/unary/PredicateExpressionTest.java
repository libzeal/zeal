package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.unary.PredicateExpression.Field;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class PredicateExpressionTest {

    private static final String NAME = "someName";
    private static final Field<Object, Object> EXPECTED = context -> "foo";
    private static final Field<Object, Object> ACTUAL = context -> "bar";
    private static final Field<Object, Object> HINT = context -> "baz";

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new PredicateExpression<>(null, new Object(), false, Function.identity(), o -> true, EXPECTED,
                ACTUAL, HINT)
        );
    }

    @Test
    void givenNullObserve_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new PredicateExpression<>(NAME, new Object(), false, null, o -> true, EXPECTED, ACTUAL, HINT)
        );
    }

    @Test
    void givenNullCondition_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new PredicateExpression<>(NAME, new Object(), false, Function.identity(), null, EXPECTED, ACTUAL,
                HINT)
        );
    }

    @Test
    void givenNullExpected_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new PredicateExpression<>(NAME, new Object(), false, Function.identity(), o -> true, null, ACTUAL,
                HINT)
        );
    }

    @Test
    void givenNullActual_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new PredicateExpression<>(NAME, new Object(), false, Function.identity(), o -> true, EXPECTED,
                null, HINT)
        );
    }

    @Test
    void givenNullHint_whenEvaluate_thenNoHint() {

        final PredicateExpression<Object, Object> expression = new PredicateExpression<>(NAME, new Object(), false,
            Function.identity(), o -> true, EXPECTED, ACTUAL, null);

        assertFalse(expression.evaluate().rationale().hint().isPresent());
    }

    @Test
    void givenExpression_whenName_thenNameIsCorrect() {

        final PredicateExpression<Object, Object> expression = new PredicateExpression<>(NAME, new Object(), false,
            Function.identity(), o -> true, EXPECTED, ACTUAL, HINT);

        assertEquals(NAME, expression.name());
    }

    @Test
    void givenTrueCondition_whenEvaluate_thenEvaluationIsCorrect() {

        final PredicateExpression<Object, Object> expression = new PredicateExpression<>(NAME, new Object(), false,
            Function.identity(), o -> true, EXPECTED, ACTUAL, HINT);

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(NAME, evaluation.name());
        assertRationaleIsCorrect(evaluation.rationale());
    }

    @Test
    void givenFalseCondition_whenEvaluate_thenEvaluationIsCorrect() {

        final PredicateExpression<Object, Object> expression = new PredicateExpression<>(NAME, new Object(), false,
            Function.identity(), o -> false, EXPECTED, ACTUAL, HINT);

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertRationaleIsCorrect(evaluation.rationale());
    }

    private static void assertRationaleIsCorrect(final Rationale rationale) {
        assertEquals("foo", rationale.expected());
        assertEquals("bar", rationale.actual());
        assertEquals("baz", rationale.hint().orElse(null));
    }

    @Test
    void givenObservation_whenEvaluate_thenConditionAndFieldsSeeObservedValue() {

        final PredicateExpression<String, Integer> expression = new PredicateExpression<>(NAME, "abc", false,
            String::length, length -> length == 3,
            context -> "length = 3",
            context -> "length = " + context.observed() + ", subject = " + context.subject(),
            context -> context.ifPassedOrElse("passed", "failed"));

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals("length = 3, subject = abc", evaluation.rationale().actual());
        assertEquals("passed", evaluation.rationale().hint().orElse(null));
    }

    @Test
    void givenObservation_whenEvaluate_thenObservedOnce() {

        final AtomicInteger observations = new AtomicInteger();
        final PredicateExpression<String, Integer> expression = new PredicateExpression<>(NAME, "abc", false,
            s -> {
                observations.incrementAndGet();
                return s.length();
            },
            length -> length == 3,
            context -> String.valueOf(context.observed()),
            context -> String.valueOf(context.observed()),
            context -> String.valueOf(context.observed()));

        expression.evaluate();

        assertEquals(1, observations.get());
    }

    @Test
    void givenNullSubjectAndNotNullable_whenEvaluate_thenFailsWithoutObserving() {

        final AtomicInteger observations = new AtomicInteger();
        final PredicateExpression<Object, Object> expression = new PredicateExpression<>(NAME, null, false,
            s -> {
                observations.incrementAndGet();
                return s;
            },
            o -> true,
            EXPECTED,
            context -> String.valueOf(context.observed()),
            HINT);

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals("null", evaluation.rationale().actual());
        assertEquals(0, observations.get());
    }

    @Test
    void givenNullSubjectAndNullable_whenEvaluate_thenNullIsObservedAndTested() {

        final PredicateExpression<Object, Object> expression = new PredicateExpression<>(NAME, null, true,
            Function.identity(), o -> o == null, EXPECTED, ACTUAL, HINT);

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertRationaleIsCorrect(evaluation.rationale());
    }
}
