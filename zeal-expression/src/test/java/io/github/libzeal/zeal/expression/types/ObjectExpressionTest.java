package io.github.libzeal.zeal.expression.types;

import io.github.libzeal.zeal.expression.condition.SimpleCondition;
import io.github.libzeal.zeal.expression.evaluation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evaluation.EvaluationState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static io.github.libzeal.zeal.expression.evaluation.EvaluationState.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class ObjectExpressionTest<T, B extends ObjectExpression<T, B>> {

    private static final String OBJECT_EXPRESSION_NAME = "Object\\[.+\\] evaluation";

    protected abstract B expression(T value);

    @Test
    @SuppressWarnings("unchecked")
    void givenNoEvaluations_whenConstruct_thenCorrectEvaluation() {

        T value = (T) new Object();

        B expression = expression(value);
        EvaluatedExpressionAssertion<?> assertion = new EvaluatedExpressionAssertion<>(expression.evaluate());

        assertEquals(value, expression.subject());
        assertion.assertStateIs(PASSED);
        assertion.assertNameMatches(OBJECT_EXPRESSION_NAME);
        assertion.assertCompoundExpectedValue();
        assertion.assertCompoundActualValueIs(0, 0, 0);
    }

    @Test
    void givenNotNullFollowsAnotherEvaluation_whenEvaluate_thenNotNullPreemptsOtherEvaluation() {

        B expression = expression(null);

        expression.isNull().isNotNull();

        EvaluatedExpression eval = expression.evaluate();
        EvaluatedExpression first = eval.children().get(0);
        EvaluatedExpression second = eval.children().get(1);

        EvaluatedExpressionAssertion<?> firstAssertion = new EvaluatedExpressionAssertion<>(first);
        EvaluatedExpressionAssertion<?> secondAssertion = new EvaluatedExpressionAssertion<>(second);

        assertNotNull(first);
        assertNotNull(second);
        firstAssertion.assertStateIs(FAILED);
        firstAssertion.assertNameIs("isNotNull");
        secondAssertion.assertStateIs(SKIPPED);
    }

    @ParameterizedTest(name = "{3} for {1}")
    @MethodSource("evaluationParameters")
    void whenEvaluate_thenCorrectEvaluation(
        BiConsumer<B, T> modifier,
        T value,
        EvaluationState expectedState,
        BiFunction<B, T, String> expectedName,
        BiFunction<B, T, String> expectedExpectedValue,
        BiFunction<B, T, String> expectedActualValue
    ) {

        B expression = expression(value);

        modifier.accept(expression, value);

        EvaluatedExpression eval = expression.evaluate();
        EvaluatedExpression first = eval.children().get(0);
        EvaluatedExpressionAssertion<?> firstAssertion = new EvaluatedExpressionAssertion<>(first);

        assertNotNull(first);
        firstAssertion.assertStateIs(expectedState);
        firstAssertion.assertNameIs(expectedName.apply(expression,value));
        firstAssertion.assertExpectedIs(expectedExpectedValue.apply(expression,value));
        firstAssertion.assertActualIs(expectedActualValue.apply(expression,value));
    }

    protected static Arguments argument(
        BiConsumer<ObjectExpression<Object, ?>, ?> modifier,
        Object value,
        EvaluationState state,
        BiFunction<?, ?, String> name,
        BiFunction<?, ?, String> expectedValue,
        BiFunction<?, ?, String> actualValue
    ) {
        return Arguments.of(modifier, value, state, name, expectedValue, actualValue);
    }

    private static Stream<Arguments> evaluationParameters() {

        final Object value1 = new Object();
        final SimpleCondition<Object> alwaysTrueCondition = new SimpleCondition<>("Always true", o -> true);
        final SimpleCondition<Object> alwaysFalseCondition = new SimpleCondition<>("Always false", o -> false);

        return Stream.of(
            // isNull
            argument(
                (expression, value) -> expression.isNull(),
                null,
                PASSED,
                (expression, value) -> "isNull",
                (expression, value) -> "(null)",
                (expression, value) -> "(null)"
            ),
            argument(
                (expression, value) -> expression.isNull(),
                "foo",
                FAILED,
                (expression, value) -> "isNull",
                (expression, value) -> "(null)",
                (expression, value) -> "foo"
            ),
            // isNotNull
            argument(
                (expression, value) -> expression.isNotNull(),
                null,
                FAILED,
                (expression, value) -> "isNotNull",
                (expression, value) -> "not[(null)]",
                (expression, value) -> "(null)"
            ),
            argument(
                (expression, value) -> expression.isNotNull(),
                "foo",
                PASSED,
                (expression, value) -> "isNotNull",
                (expression, value) -> "not[(null)]",
                (expression, value) -> "foo"
            ),
            // isType
            argument(
                (expression, value) -> expression.isType(String.class),
                "foo",
                PASSED,
                (expression, value) -> "isType[java.lang.String]",
                (expression, value) -> "class java.lang.String",
                (expression, value) -> "class java.lang.String"
            ),
            argument(
                (expression, value) -> expression.isType(Object.class),
                "foo",
                FAILED,
                (expression, value) -> "isType[java.lang.Object]",
                (expression, value) -> "class java.lang.Object",
                (expression, value) -> "class java.lang.String"
            ),
            argument(
                (expression, value) -> expression.isType(Thread.class),
                "foo",
                FAILED,
                (expression, value) -> "isType[java.lang.Thread]",
                (expression, value) -> "class java.lang.Thread",
                (expression, value) -> "class java.lang.String"
            ),
            argument(
                (expression, value) -> expression.isType(null),
                "foo",
                FAILED,
                (expression, value) -> "isType[(null)]",
                (expression, value) -> "Always fail: cannot compare to (null) type",
                (expression, value) -> "class java.lang.String"
            ),
            // isNotType
            argument(
                (expression, value) -> expression.isNotType(String.class),
                "foo",
                FAILED,
                (expression, value) -> "isNotType[java.lang.String]",
                (expression, value) -> "not[class java.lang.String]",
                (expression, value) -> "class java.lang.String"
            ),
            argument(
                (expression, value) -> expression.isNotType(Object.class),
                "foo",
                PASSED,
                (expression, value) -> "isNotType[java.lang.Object]",
                (expression, value) -> "not[class java.lang.Object]",
                (expression, value) -> "class java.lang.String"
            ),
            argument(
                (expression, value) -> expression.isNotType(Thread.class),
                "foo",
                PASSED,
                (expression, value) -> "isNotType[java.lang.Thread]",
                (expression, value) -> "not[class java.lang.Thread]",
                (expression, value) -> "class java.lang.String"
            ),
            argument(
                (expression, value) -> expression.isNotType(null),
                "foo",
                FAILED,
                (expression, value) -> "isNotType[(null)]",
                (expression, value) -> "Always fail: cannot compare to (null) type",
                (expression, value) -> "class java.lang.String"
            ),
            // isInstanceOf
            argument(
                (expression, value) -> expression.isInstanceOf(String.class),
                "foo",
                PASSED,
                (expression, value) -> "isInstanceOf[java.lang.String]",
                (expression, value) -> "instanceof[class java.lang.String]",
                (expression, value) -> "class java.lang.String"
            ),
            argument(
                (expression, value) -> expression.isInstanceOf(Object.class),
                "foo",
                PASSED,
                (expression, value) -> "isInstanceOf[java.lang.Object]",
                (expression, value) -> "instanceof[class java.lang.Object]",
                (expression, value) -> "class java.lang.String"
            ),
            argument(
                (expression, value) -> expression.isInstanceOf(Thread.class),
                "foo",
                FAILED,
                (expression, value) -> "isInstanceOf[java.lang.Thread]",
                (expression, value) -> "instanceof[class java.lang.Thread]",
                (expression, value) -> "class java.lang.String"
            ),
            argument(
                (expression, value) -> expression.isInstanceOf(null),
                "foo",
                FAILED,
                (expression, value) -> "isInstanceOf[(null)]",
                (expression, value) -> "Always fail: cannot compare to (null) type",
                (expression, value) -> "class java.lang.String"
            ),
            // isNotInstanceOf
            argument(
                (expression, value) -> expression.isNotInstanceOf(String.class),
                "foo",
                FAILED,
                (expression, value) -> "isNotInstanceOf[java.lang.String]",
                (expression, value) -> "not[instanceof[class java.lang.String]]",
                (expression, value) -> "class java.lang.String"
            ),
            argument(
                (expression, value) -> expression.isNotInstanceOf(Object.class),
                "foo",
                FAILED,
                (expression, value) -> "isNotInstanceOf[java.lang.Object]",
                (expression, value) -> "not[instanceof[class java.lang.Object]]",
                (expression, value) -> "class java.lang.String"
            ),
            argument(
                (expression, value) -> expression.isNotInstanceOf(Thread.class),
                "foo",
                PASSED,
                (expression, value) -> "isNotInstanceOf[java.lang.Thread]",
                (expression, value) -> "not[instanceof[class java.lang.Thread]]",
                (expression, value) -> "class java.lang.String"
            ),
            argument(
                (expression, value) -> expression.isNotInstanceOf(null),
                "foo",
                FAILED,
                (expression, value) -> "isNotInstanceOf[(null)]",
                (expression, value) -> "Always fail: cannot compare to (null) type",
                (expression, value) -> "class java.lang.String"
            ),
            // is
            argument(
                (expression, value) -> expression.is("foo"),
                "foo",
                PASSED,
                (expression, value) -> "is[foo]",
                (expression, value) -> "foo",
                (expression, value) -> "foo"
            ),
            argument(
                (expression, value) -> expression.is("foo"),
                "bar",
                FAILED,
                (expression, value) -> "is[foo]",
                (expression, value) -> "foo",
                (expression, value) -> "bar"
            ),
            argument(
                (expression, value) -> expression.is(null),
                null,
                PASSED,
                (expression, value) -> "is[(null)]",
                (expression, value) -> "(null)",
                (expression, value) -> "(null)"
            ),
            // isNot
            argument(
                (expression, value) -> expression.isNot("foo"),
                "foo",
                FAILED,
                (expression, value) -> "isNot[foo]",
                (expression, value) -> "not[foo]",
                (expression, value) -> "foo"
            ),
            argument(
                (expression, value) -> expression.isNot("foo"),
                "bar",
                PASSED,
                (expression, value) -> "isNot[foo]",
                (expression, value) -> "not[foo]",
                (expression, value) -> "bar"
            ),
            argument(
                (expression, value) -> expression.isNot(null),
                null,
                FAILED,
                (expression, value) -> "isNot[(null)]",
                (expression, value) -> "not[(null)]",
                (expression, value) -> "(null)"
            ),
            // isEqualTo
            argument(
                ObjectExpression::isEqualTo,
                new Object(),
                PASSED,
                (expression, value) -> "isEqualTo[" + value + "]",
                (expression, value) -> value.toString(),
                (expression, value) -> value.toString()
            ),
            argument(
                (expression, value) -> expression.isEqualTo(value1),
                new Object(),
                FAILED,
                (expression, value) -> "isEqualTo[" + value1 + "]",
                (expression, value) -> value1.toString(),
                (expression, value) -> value.toString()
            ),
            argument(
                (expression, value) -> expression.isEqualTo(null),
                null,
                PASSED,
                (expression, value) -> "isEqualTo[(null)]",
                (expression, value) -> "(null)",
                (expression, value) -> "(null)"
            ),
            // isNotEqualTo
            argument(
                ObjectExpression::isNotEqualTo,
                new Object(),
                FAILED,
                (expression, value) -> "isNotEqualTo[" + value + "]",
                (expression, value) -> "not[" + value.toString() + "]",
                (expression, value) -> value.toString()
            ),
            argument(
                (expression, value) -> expression.isNotEqualTo(value1),
                new Object(),
                PASSED,
                (expression, value) -> "isNotEqualTo[" + value1 + "]",
                (expression, value) -> "not[" + value1.toString() + "]",
                (expression, value) -> value.toString()
            ),
            argument(
                (expression, value) -> expression.isNotEqualTo(null),
                null,
                FAILED,
                (expression, value) -> "isNotEqualTo[(null)]",
                (expression, value) -> "not[(null)]",
                (expression, value) -> "(null)"
            ),
            // hashCodeIs
            argument(
                (expression, value) -> expression.hashCodeIs(100),
                100,
                PASSED,
                (expression, value) -> "hashCode == " + value,
                (expression, value) -> value.toString(),
                (expression, value) -> value.toString()
            ),
            argument(
                (expression, value) -> expression.hashCodeIs(50),
                100,
                FAILED,
                (expression, value) -> "hashCode == " + 50,
                (expression, value) -> String.valueOf(50),
                (expression, value) -> value.toString()
            ),
            // hashCodeIsNot
            argument(
                (expression, value) -> expression.hashCodeIsNot(100),
                100,
                FAILED,
                (expression, value) -> "hashCode != " + value,
                (expression, value) -> "not[100]",
                (expression, value) -> value.toString()
            ),
            argument(
                (expression, value) -> expression.hashCodeIsNot(50),
                100,
                PASSED,
                (expression, value) -> "hashCode != " + 50,
                (expression, value) -> "not[50]",
                (expression, value) -> value.toString()
            ),
            // toStringIs
            argument(
                (expression, value) -> expression.toStringIs("foo"),
                "foo",
                PASSED,
                (expression, value) -> "toString().equals(foo)",
                (expression, value) -> "foo",
                (expression, value) -> value.toString()
            ),
            argument(
                (expression, value) -> expression.toStringIs("foo"),
                "bar",
                FAILED,
                (expression, value) -> "toString().equals(foo)",
                (expression, value) -> "foo",
                (expression, value) -> value.toString()
            ),
            // toStringIsNot
            argument(
                (expression, value) -> expression.toStringIsNot("foo"),
                "foo",
                FAILED,
                (expression, value) -> "not[toString().equals(foo)]",
                (expression, value) -> "not[foo]",
                (expression, value) -> value.toString()
            ),
            argument(
                (expression, value) -> expression.toStringIsNot("foo"),
                "bar",
                PASSED,
                (expression, value) -> "not[toString().equals(foo)]",
                (expression, value) -> "not[foo]",
                (expression, value) -> value.toString()
            ),
            // satisfies (predicate)
            argument(
                (expression, value) -> expression.satisfies(o -> true),
                new Object(),
                PASSED,
                (expression, value) -> "predicate",
                (expression, value) -> "Predicate satisfied",
                (expression, value) -> "Predicate satisfied"
            ),
            argument(
                (expression, value) -> expression.satisfies(o -> false),
                new Object(),
                FAILED,
                (expression, value) -> "predicate",
                (expression, value) -> "Predicate satisfied",
                (expression, value) -> "Predicate unsatisfied"
            ),
            // doesNotSatisfy (predicate)
            argument(
                (expression, value) -> expression.doesNotSatisfy(o -> true),
                new Object(),
                FAILED,
                (expression, value) -> "not[predicate]",
                (expression, value) -> "Predicate unsatisfied",
                (expression, value) -> "Predicate satisfied"
            ),
            argument(
                (expression, value) -> expression.doesNotSatisfy(o -> false),
                new Object(),
                PASSED,
                (expression, value) -> "not[predicate]",
                (expression, value) -> "Predicate unsatisfied",
                (expression, value) -> "Predicate unsatisfied"
            ),
            // satisfies (condition)
            argument(
                (expression, value) -> expression.satisfies(alwaysTrueCondition),
                new Object(),
                PASSED,
                (expression, value) -> "condition: " + alwaysTrueCondition.name(),
                (expression, value) -> "Condition satisfied",
                (expression, value) -> "Condition satisfied"
            ),
            argument(
                (expression, value) -> expression.satisfies(alwaysFalseCondition),
                new Object(),
                FAILED,
                (expression, value) -> "condition: " + alwaysFalseCondition.name(),
                (expression, value) -> "Condition satisfied",
                (expression, value) -> "Condition unsatisfied"
            ),
            // doesNotSatisfy (condition)
            argument(
                (expression, value) -> expression.doesNotSatisfy(alwaysTrueCondition),
                new Object(),
                FAILED,
                (expression, value) -> "not[condition: " + alwaysTrueCondition.name() + "]",
                (expression, value) -> "Condition unsatisfied",
                (expression, value) -> "Condition satisfied"
            ),
            argument(
                (expression, value) -> expression.doesNotSatisfy(alwaysFalseCondition),
                new Object(),
                PASSED,
                (expression, value) -> "not[condition: " + alwaysFalseCondition.name() + "]",
                (expression, value) -> "Condition unsatisfied",
                (expression, value) -> "Condition unsatisfied"
            )
        );
    }
}
