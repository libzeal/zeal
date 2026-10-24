package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionBuilderTest {

    @Test
    void givenNullCondition_whenOf_thenExceptionThrown() {
        assertThrows(NullPointerException.class, () -> ExpressionBuilder.of(null));
    }

    @Test
    void givenNullObservation_whenObserving_thenExceptionThrown() {
        assertThrows(NullPointerException.class, () -> ExpressionBuilder.observing(null));
    }

    @Test
    void givenNullCondition_whenPassesIf_thenExceptionThrown() {
        assertThrows(NullPointerException.class, () -> ExpressionBuilder.observing(Object::hashCode).passesIf(null));
    }

    @Test
    void givenNullName_whenName_thenExceptionThrown() {
        assertThrows(NullPointerException.class, () -> ExpressionBuilder.of(o -> true).name(null));
    }

    @Test
    void givenNoFieldsSet_whenEvaluate_thenDefaultsUsed() {

        final Evaluation evaluation = ExpressionBuilder.of((String s) -> true)
            .build()
            .compute("foo")
            .evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals("<unnamed>", evaluation.name());
        assertEquals("<not set>", evaluation.rationale().expected());
        assertEquals("foo", evaluation.rationale().actual());
        assertFalse(evaluation.rationale().hint().isPresent());
    }

    @Test
    void givenFieldsAsStrings_whenEvaluate_thenFieldsUsed() {

        final Evaluation evaluation = ExpressionBuilder.of((String s) -> false)
            .name("someName")
            .expected("foo")
            .actual("bar")
            .hint("baz")
            .build()
            .compute("subject")
            .evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals("someName", evaluation.name());
        assertEquals("foo", evaluation.rationale().expected());
        assertEquals("bar", evaluation.rationale().actual());
        assertEquals("baz", evaluation.rationale().hint().orElse(null));
    }

    @Test
    void givenExpectedAsLong_whenEvaluate_thenExpectedIsStringified() {

        final Evaluation evaluation = ExpressionBuilder.of((String s) -> true)
            .expected(42L)
            .build()
            .compute("foo")
            .evaluate();

        assertEquals("42", evaluation.rationale().expected());
    }

    @Test
    void givenObserving_whenEvaluate_thenConditionTestsObservedValueAndActualDefaultsToIt() {

        final Evaluation evaluation = ExpressionBuilder.observing(String::length)
            .passesIf(length -> length == 3)
            .build()
            .compute("abc")
            .evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals("3", evaluation.rationale().actual());
    }

    @Test
    void givenNullSubjectAndNotNullable_whenEvaluate_thenFalseAndActualIsNull() {

        final Evaluation evaluation = ExpressionBuilder.observing(String::length)
            .passesIf(length -> true)
            .build()
            .compute(null)
            .evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals("(null)", evaluation.rationale().actual());
    }

    @Test
    void givenNullSubjectAndNullable_whenEvaluate_thenConditionTestsNull() {

        final Evaluation evaluation = ExpressionBuilder.of((String s) -> s == null)
            .nullable()
            .build()
            .compute(null)
            .evaluate();

        assertEquals(Result.TRUE, evaluation.result());
    }

    @Test
    void givenBuilderChangedAfterBuild_whenEvaluate_thenBuiltExpressionUnchanged() {

        final ExpressionBuilder<String, String> builder = ExpressionBuilder.of((String s) -> true)
            .name("before");
        final ComputableExpression<String> expression = builder.build();

        builder.name("after");

        assertEquals("before", expression.compute("foo").evaluate().name());
    }
}
