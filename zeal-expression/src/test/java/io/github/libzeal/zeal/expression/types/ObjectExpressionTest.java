package io.github.libzeal.zeal.expression.types;

import io.github.libzeal.zeal.expression.evalulation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evalulation.EvaluationState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static io.github.libzeal.zeal.expression.evalulation.EvaluationState.*;
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
        Consumer<B> modifier,
        T value,
        EvaluationState expectedState,
        String expectedName,
        String expectedExpectedValue,
        String expectedActualValue
    ) {

        B expression = expression(value);

        modifier.accept(expression);

        EvaluatedExpression eval = expression.evaluate();
        EvaluatedExpression first = eval.children().get(0);
        EvaluatedExpressionAssertion<?> firstAssertion = new EvaluatedExpressionAssertion<>(first);

        assertNotNull(first);
        firstAssertion.assertStateIs(expectedState);
        firstAssertion.assertNameIs(expectedName);
        firstAssertion.assertExpectedIs(expectedExpectedValue);
        firstAssertion.assertActualIs(expectedActualValue);
    }

    private static Stream<Arguments> evaluationParameters() {
        return Stream.of(
            // isNull
            argument(
                ObjectExpression::isNull,
                null,
                PASSED,
                "isNull",
                "(null)",
                "(null)"
            ),
            argument(
                ObjectExpression::isNull,
                "foo",
                FAILED,
                "isNull",
                "(null)",
                "foo"
            ),
            // isNotNull
            argument(
                ObjectExpression::isNotNull,
                null,
                FAILED,
                "isNotNull",
                "not[(null)]",
                "(null)"
            ),
            argument(
                ObjectExpression::isNotNull,
                "foo",
                PASSED,
                "isNotNull",
                "not[(null)]",
                "foo"
            ),
            // isType
            argument(
                o -> o.isType(String.class),
                "foo",
                PASSED,
                "isType[java.lang.String]",
                "class java.lang.String",
                "class java.lang.String"
            ),
            argument(
                o -> o.isType(Object.class),
                "foo",
                FAILED,
                "isType[java.lang.Object]",
                "class java.lang.Object",
                "class java.lang.String"
            ),
            argument(
                o -> o.isType(Thread.class),
                "foo",
                FAILED,
                "isType[java.lang.Thread]",
                "class java.lang.Thread",
                "class java.lang.String"
            ),
            // isNotType
            argument(
                o -> o.isNotType(String.class),
                "foo",
                FAILED,
                "isNotType[java.lang.String]",
                "not class java.lang.String",
                "class java.lang.String"
            ),
            argument(
                o -> o.isNotType(Object.class),
                "foo",
                PASSED,
                "isNotType[java.lang.Object]",
                "not class java.lang.Object",
                "class java.lang.String"
            ),
            argument(
                o -> o.isNotType(Thread.class),
                "foo",
                PASSED,
                "isNotType[java.lang.Thread]",
                "not class java.lang.Thread",
                "class java.lang.String"
            ),
            // isInstanceOf
            argument(
                o -> o.isInstanceOf(String.class),
                "foo",
                PASSED,
                "isInstanceOf[java.lang.String]",
                "instanceof class java.lang.String",
                "class java.lang.String"
            ),
            argument(
                o -> o.isInstanceOf(Object.class),
                "foo",
                PASSED,
                "isInstanceOf[java.lang.Object]",
                "instanceof class java.lang.Object",
                "class java.lang.String"
            ),
            argument(
                o -> o.isInstanceOf(Thread.class),
                "foo",
                FAILED,
                "isInstanceOf[java.lang.Thread]",
                "instanceof class java.lang.Thread",
                "class java.lang.String"
            ),
            // isNotInstanceOf
            argument(
                o -> o.isNotInstanceOf(String.class),
                "foo",
                FAILED,
                "isNotInstanceOf[java.lang.String]",
                "not instanceof class java.lang.String",
                "class java.lang.String"
            ),
            argument(
                o -> o.isNotInstanceOf(Object.class),
                "foo",
                FAILED,
                "isNotInstanceOf[java.lang.Object]",
                "not instanceof class java.lang.Object",
                "class java.lang.String"
            ),
            argument(
                o -> o.isNotInstanceOf(Thread.class),
                "foo",
                PASSED,
                "isNotInstanceOf[java.lang.Thread]",
                "not instanceof class java.lang.Thread",
                "class java.lang.String"
            ),
            // is
            argument(
                o -> o.is("foo"),
                "foo",
                PASSED,
                "is[foo]",
                "foo",
                "foo"
            ),
            argument(
                o -> o.is("foo"),
                "bar",
                FAILED,
                "is[foo]",
                "foo",
                "bar"
            ),
            argument(
                o -> o.is(null),
                null,
                PASSED,
                "is[(null)]",
                "(null)",
                "(null)"
            ),
            // isNot
            argument(
                o -> o.isNot("foo"),
                "foo",
                FAILED,
                "isNot[foo]",
                "not[foo]",
                "foo"
            ),
            argument(
                o -> o.isNot("foo"),
                "bar",
                PASSED,
                "isNot[foo]",
                "not[foo]",
                "bar"
            ),
            argument(
                o -> o.isNot(null),
                null,
                FAILED,
                "isNot[(null)]",
                "not[(null)]",
                "(null)"
            )
        );
    }

    protected static Arguments argument(
        Consumer<ObjectExpression<?, ?>> modifier,
        Object value,
        EvaluationState state,
        String name,
        String expectedValue,
        String actualValue
    ) {
        return Arguments.of(modifier, value, state, name, expectedValue, actualValue);
    }
}
