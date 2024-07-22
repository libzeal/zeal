package io.github.libzeal.zeal.expression.types;

import io.github.libzeal.zeal.expression.condition.SimpleCondition;
import io.github.libzeal.zeal.expression.evaluation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evaluation.EvaluationState;
import io.github.libzeal.zeal.expression.test.ExpressionTestCaseBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.github.libzeal.zeal.expression.evaluation.EvaluationState.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A base class designed to be extended by test fixtures used to test custom expressions.
 * <p />
 * For example:
 * <pre><code>
 * public class StringExpressionCheck extends ObjectExpressionTest<String, StringExpression> {
 *
 *    {@literal @}Override
 *     protected void extendTestCases(ExpressionTestCaseBuilder builder) {
 *         builder.newTest((expression, value) -> expression.isEmpty())
 *             .value("foo")
 *             .expectedState(FAILED)
 *             .expectedName("isEmpty")
 *             .expectedExpectedValue("true")
 *             .expectedActualValue("false")
 *             .addTest();
 *     }
 * }
 * </code></pre>
 * @param <T>
 *     The type of the subject.
 * @param <B>
 *     The type of the expression under test. This type is an example of the
 *     <a href="https://stackoverflow.com/q/4173254/2403253">Curiously Recurring Template Pattern (CRTP)</a>.
 *     This type allows for the fluent interface methods of this class to return the subtype object, rather than
 *     {@link ObjectExpression}. For example:
 *     <pre><code>
 *         public class StringExpressionCheck extends ObjectExpressionTest<String, StringExpression> { //...
 *     </code></pre>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ObjectExpressionTest<T, B extends ObjectExpression<T, B>> {

    private static final String OBJECT_EXPRESSION_NAME = "Object\\[.+\\] evaluation";
    private static final SimpleCondition<Object> ALWAYS_TRUE = new SimpleCondition<>("Always true", s -> true);
    private static final SimpleCondition<Object> ALWAYS_FALSE = new SimpleCondition<>("Always false", s -> false);

    protected abstract B expression(T value);

    @Test
    @SuppressWarnings("unchecked")
    final void givenNoEvaluations_whenConstruct_thenCorrectEvaluation() {

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
    final void givenNotNullFollowsAnotherEvaluation_whenEvaluate_thenNotNullPreemptsOtherEvaluation() {

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

    @ParameterizedTest(name = "{0}")
    @MethodSource("testCaseStream")
    final void whenEvaluate_thenCorrectEvaluation(
        String testCaseName,
        BiConsumer<B, T> modifier,
        T value,
        EvaluationState expectedState,
        Function<T, String> expectedName,
        BiFunction<B, T, String> expectedExpectedValue,
        BiFunction<B, T, String> expectedActualValue
    ) {

        B expression = expression(value);

        modifier.accept(expression, value);

        EvaluatedExpression eval = expression.evaluate();
        EvaluatedExpression first = eval.children().get(0);
        EvaluatedExpressionAssertion<?> firstEvaluation = new EvaluatedExpressionAssertion<>(first);

        assertNotNull(first);
        firstEvaluation.assertStateIs(expectedState);
        firstEvaluation.assertNameIs(expectedName.apply(value));
        firstEvaluation.assertExpectedIs(expectedExpectedValue.apply(expression,value));
        firstEvaluation.assertActualIs(expectedActualValue.apply(expression,value));
    }

    private Stream<Arguments> testCaseStream() {

        ExpressionTestCaseBuilder builder = ExpressionTestCaseBuilder.newBuilder();

        isNullTestCases(builder);
        isNotNullTestCases(builder);
        isTypeTestCases(builder);
        isNotTypeTestCases(builder);
        isInstanceOfTestCases(builder);
        isNotInstanceOfTestCases(builder);
        isTestCases(builder);
        isNotTestCases(builder);
        isEqualToTestCases(builder);
        isNotEqualToTestCases(builder);
        hashCodeIsTestCases(builder);
        hashCodeIsNotTestCases(builder);
        toStringIsTestCases(builder);
        toStringIsNotTestCases(builder);
        satisfiesPredicateTestCases(builder);
        doesNotSatisfyPredicateTestCases(builder);
        satisfiesConditionTestCases(builder);
        doesNotSatisfyConditionTestCases(builder);

        extendTestCases(builder);

        return builder.stream();
    }

    private void isNullTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.isNull())
                .value(null)
                .expectedState(PASSED)
                .expectedName("isNull")
                .expectedExpectedValue("(null)")
                .expectedActualValue("(null)")
                .addTest()
            .newTest((expression, value) -> expression.isNull())
                .value("foo")
                .expectedState(FAILED)
                .expectedName("isNull")
                .expectedExpectedValue("(null)")
                .expectedActualValue("foo")
                .addTest();
    }

    private void isNotNullTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.isNotNull())
                .value(null)
                .expectedState(FAILED)
                .expectedName("isNotNull")
                .expectedExpectedValue("not[(null)]")
                .expectedActualValue("(null)")
                .addTest()
            .newTest((expression, value) -> expression.isNotNull())
                .value("foo")
                .expectedState(PASSED)
                .expectedName("isNotNull")
                .expectedExpectedValue("not[(null)]")
                .expectedActualValue("foo")
                .addTest();
    }

    private void isTypeTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.isType(String.class))
                .value("foo")
                .expectedState(PASSED)
                .expectedName("isType[java.lang.String]")
                .expectedExpectedValue("class java.lang.String")
                .expectedActualValue("class java.lang.String")
                .addTest()
            .newTest((expression, value) -> expression.isType(Object.class))
                .value("foo")
                .expectedState(FAILED)
                .expectedName("isType[java.lang.Object]")
                .expectedExpectedValue("class java.lang.Object")
                .expectedActualValue("class java.lang.String")
                .addTest()
            .newTest((expression, value) -> expression.isType(Thread.class))
                .value("foo")
                .expectedState(FAILED)
                .expectedName("isType[java.lang.Thread]")
                .expectedExpectedValue("class java.lang.Thread")
                .expectedActualValue("class java.lang.String")
                .addTest()
            .newTest((expression, value) -> expression.isType(null))
                .value("foo")
                .expectedState(FAILED)
                .expectedName("isType[(null)]")
                .expectedExpectedValue("Always fail: cannot compare to (null) type")
                .expectedActualValue("class java.lang.String")
                .addTest();
    }

    private void isNotTypeTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.isNotType(String.class))
                .value("foo")
                .expectedState(FAILED)
                .expectedName("isNotType[java.lang.String]")
                .expectedExpectedValue("not[class java.lang.String]")
                .expectedActualValue("class java.lang.String")
                .addTest()
            .newTest((expression, value) -> expression.isNotType(Object.class))
                .value("foo")
                .expectedState(PASSED)
                .expectedName("isNotType[java.lang.Object]")
                .expectedExpectedValue("not[class java.lang.Object]")
                .expectedActualValue("class java.lang.String")
                .addTest()
            .newTest((expression, value) -> expression.isNotType(Thread.class))
                .value("foo")
                .expectedState(PASSED)
                .expectedName("isNotType[java.lang.Thread]")
                .expectedExpectedValue("not[class java.lang.Thread]")
                .expectedActualValue("class java.lang.String")
                .addTest()
            .newTest((expression, value) -> expression.isNotType(null))
                .value("foo")
                .expectedState(FAILED)
                .expectedName("isNotType[(null)]")
                .expectedExpectedValue("Always fail: cannot compare to (null) type")
                .expectedActualValue("class java.lang.String")
                .addTest();
    }

    private void isInstanceOfTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.isInstanceOf(String.class))
                .value("foo")
                .expectedState(PASSED)
                .expectedName("isInstanceOf[java.lang.String]")
                .expectedExpectedValue("instanceof[class java.lang.String]")
                .expectedActualValue("class java.lang.String")
                .addTest()
            .newTest((expression, value) -> expression.isInstanceOf(Object.class))
                .value("foo")
                .expectedState(PASSED)
                .expectedName("isInstanceOf[java.lang.Object]")
                .expectedExpectedValue("instanceof[class java.lang.Object]")
                .expectedActualValue("class java.lang.String")
                .addTest()
            .newTest((expression, value) -> expression.isInstanceOf(Thread.class))
                .value("foo")
                .expectedState(FAILED)
                .expectedName("isInstanceOf[java.lang.Thread]")
                .expectedExpectedValue("instanceof[class java.lang.Thread]")
                .expectedActualValue("class java.lang.String")
                .addTest()
            .newTest((expression, value) -> expression.isInstanceOf(null))
                .value("foo")
                .expectedState(FAILED)
                .expectedName("isInstanceOf[(null)]")
                .expectedExpectedValue("Always fail: cannot compare to (null) type")
                .expectedActualValue("class java.lang.String")
                .addTest();
    }

    private void isNotInstanceOfTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.isNotInstanceOf(String.class))
                .value("foo")
                .expectedState(FAILED)
                .expectedName("isNotInstanceOf[java.lang.String]")
                .expectedExpectedValue("not[instanceof[class java.lang.String]]")
                .expectedActualValue("class java.lang.String")
                .addTest()
            .newTest((expression, value) -> expression.isNotInstanceOf(Object.class))
                .value("foo")
                .expectedState(FAILED)
                .expectedName("isNotInstanceOf[java.lang.Object]")
                .expectedExpectedValue("not[instanceof[class java.lang.Object]]")
                .expectedActualValue("class java.lang.String")
                .addTest()
            .newTest((expression, value) -> expression.isNotInstanceOf(Thread.class))
                .value("foo")
                .expectedState(PASSED)
                .expectedName("isNotInstanceOf[java.lang.Thread]")
                .expectedExpectedValue("not[instanceof[class java.lang.Thread]]")
                .expectedActualValue("class java.lang.String")
                .addTest()
            .newTest((expression, value) -> expression.isNotInstanceOf(null))
                .value("foo")
                .expectedState(FAILED)
                .expectedName("isNotInstanceOf[(null)]")
                .expectedExpectedValue("Always fail: cannot compare to (null) type")
                .expectedActualValue("class java.lang.String")
                .addTest();
    }

    private void isTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.is("foo"))
                .value("foo")
                .expectedState(PASSED)
                .expectedName("is[foo]")
                .expectedExpectedValue("foo")
                .expectedActualValue("foo")
                .addTest()
            .newTest((expression, value) -> expression.is("foo"))
                .value("bar")
                .expectedState(FAILED)
                .expectedName("is[foo]")
                .expectedExpectedValue("foo")
                .expectedActualValue("bar")
                .addTest()
            .newTest((expression, value) -> expression.is(null))
                .value(null)
                .expectedState(PASSED)
                .expectedName("is[(null)]")
                .expectedExpectedValue("(null)")
                .expectedActualValue("(null)")
                .addTest();
    }

    private void isNotTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.isNot("foo"))
                .value("foo")
                .expectedState(FAILED)
                .expectedName("isNot[foo]")
                .expectedExpectedValue("not[foo]")
                .expectedActualValue("foo")
                .addTest()
            .newTest((expression, value) -> expression.isNot("foo"))
                .value("bar")
                .expectedState(PASSED)
                .expectedName("isNot[foo]")
                .expectedExpectedValue("not[foo]")
                .expectedActualValue("bar")
                .addTest()
            .newTest((expression, value) -> expression.isNot(null))
                .value(null)
                .expectedState(FAILED)
                .expectedName("isNot[(null)]")
                .expectedExpectedValue("not[(null)]")
                .expectedActualValue("(null)")
                .addTest();
    }

    private void isEqualToTestCases(ExpressionTestCaseBuilder builder) {

        Object value1 = new Object();

        builder.newTest(ObjectExpression::isEqualTo)
                .value(new Object())
                .expectedState(PASSED)
                .expectedName(value -> "isEqualTo[" + value + "]")
                .expectedExpectedValue((expression, value) -> value.toString())
                .expectedActualValue((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(value1))
                .value(new Object())
                .expectedState(FAILED)
                .expectedName(value -> "isEqualTo[" + value1 + "]")
                .expectedExpectedValue((expression, value) -> value1.toString())
                .expectedActualValue((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(null))
                .value(null)
                .expectedState(PASSED)
                .expectedName("isEqualTo[(null)]")
                .expectedExpectedValue("(null)")
                .expectedActualValue("(null)")
                .addTest();
    }

    private void isNotEqualToTestCases(ExpressionTestCaseBuilder builder) {

        Object value1 = new Object();

        builder.newTest(ObjectExpression::isNotEqualTo)
                .value(new Object())
                .expectedState(FAILED)
                .expectedName(value -> "isNotEqualTo[" + value + "]")
                .expectedExpectedValue((expression, value) -> "not[" + value.toString() + "]")
                .expectedActualValue((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.isNotEqualTo(value1))
                .value(new Object())
                .expectedState(PASSED)
                .expectedName(value -> "isNotEqualTo[" + value1 + "]")
                .expectedExpectedValue((expression, value) -> "not[" + value1.toString() + "]")
                .expectedActualValue((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.isNotEqualTo(null))
                .value(null)
                .expectedState(FAILED)
                .expectedName("isNotEqualTo[(null)]")
                .expectedExpectedValue("not[(null)]")
                .expectedActualValue("(null)")
                .addTest();
    }

    private void hashCodeIsTestCases(ExpressionTestCaseBuilder builder) {
            builder.newTest((expression, value) -> expression.hashCodeIs(100))
                .value(100)
                .expectedState(PASSED)
                .expectedName(value -> "hashCode == " + value)
                .expectedExpectedValue((expression, value) -> value.toString())
                .expectedActualValue((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.hashCodeIs(50))
                .value(100)
                .expectedState(FAILED)
                .expectedName(value -> "hashCode == " + 50)
                .expectedExpectedValue((expression, value) -> String.valueOf(50))
                .expectedActualValue((expression, value) -> value.toString())
                .addTest();
    }

    private void hashCodeIsNotTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.hashCodeIsNot(100))
                .value(100)
                .expectedState(FAILED)
                .expectedName(value -> "hashCode != " + value)
                .expectedExpectedValue((expression, value) -> "not[100]")
                .expectedActualValue((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.hashCodeIsNot(50))
                .value(100)
                .expectedState(PASSED)
                .expectedName(value -> "hashCode != " + 50)
                .expectedExpectedValue((expression, value) -> "not[50]")
                .expectedActualValue((expression, value) -> value.toString())
                .addTest();
    }

    private void toStringIsTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.toStringIs("foo"))
                .value("foo")
                .expectedState(PASSED)
                .expectedName(value -> "toString().equals(foo)")
                .expectedExpectedValue((expression, value) -> "foo")
                .expectedActualValue((expression, value) -> value.toString())
            .addTest()
                .newTest((expression, value) -> expression.toStringIs("foo"))
                .value("bar")
                .expectedState(FAILED)
                .expectedName(value -> "toString().equals(foo)")
                .expectedExpectedValue((expression, value) -> "foo")
                .expectedActualValue((expression, value) -> value.toString())
                .addTest();
    }

    private void toStringIsNotTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.toStringIsNot("foo"))
                .value("foo")
                .expectedState(FAILED)
                .expectedName(value -> "not[toString().equals(foo)]")
                .expectedExpectedValue((expression, value) -> "not[foo]")
                .expectedActualValue((expression, value) -> value.toString())
                .addTest()
            .newTest((expression, value) -> expression.toStringIsNot("foo"))
                .value("bar")
                .expectedState(PASSED)
                .expectedName(value -> "not[toString().equals(foo)]")
                .expectedExpectedValue((expression, value) -> "not[foo]")
                .expectedActualValue((expression, value) -> value.toString())
                .addTest();
    }

    private void satisfiesPredicateTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.satisfies(o -> true))
                .value(new Object())
                .expectedState(PASSED)
                .expectedName("predicate")
                .expectedExpectedValue("Predicate satisfied")
                .expectedActualValue("Predicate satisfied")
                .addTest()
            .newTest((expression, value) -> expression.satisfies(o -> false))
                .value(new Object())
                .expectedState(FAILED)
                .expectedName("predicate")
                .expectedExpectedValue("Predicate satisfied")
                .expectedActualValue("Predicate unsatisfied")
                .addTest();
    }

    private void doesNotSatisfyPredicateTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.doesNotSatisfy(o -> true))
                .value(new Object())
                .expectedState(FAILED)
                .expectedName("not[predicate]")
                .expectedExpectedValue("Predicate unsatisfied")
                .expectedActualValue("Predicate satisfied")
                .addTest()
            .newTest((expression, value) -> expression.doesNotSatisfy(o -> false))
                .value(new Object())
                .expectedState(PASSED)
                .expectedName("not[predicate]")
                .expectedExpectedValue("Predicate unsatisfied")
                .expectedActualValue("Predicate unsatisfied")
                .addTest();
    }

    private void satisfiesConditionTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.satisfies(ALWAYS_TRUE))
                .value(new Object())
                .expectedState(PASSED)
                .expectedName("condition: " + ALWAYS_TRUE.name())
                .expectedExpectedValue("Condition satisfied")
                .expectedActualValue("Condition satisfied")
                .addTest()
            .newTest((expression, value) -> expression.satisfies(ALWAYS_FALSE))
                .value(new Object())
                .expectedState(FAILED)
                .expectedName("condition: " + ALWAYS_FALSE.name())
                .expectedExpectedValue("Condition satisfied")
                .expectedActualValue("Condition unsatisfied")
                .addTest();
    }

    private void doesNotSatisfyConditionTestCases(ExpressionTestCaseBuilder builder) {
        builder.newTest((expression, value) -> expression.doesNotSatisfy(ALWAYS_TRUE))
                .value(new Object())
                .expectedState(FAILED)
                .expectedName("not[condition: " + ALWAYS_TRUE.name() + "]")
                .expectedExpectedValue("Condition unsatisfied")
                .expectedActualValue("Condition satisfied")
                .addTest()
            .newTest((expression, value) -> expression.doesNotSatisfy(ALWAYS_FALSE))
                .value(new Object())
                .expectedState(PASSED)
                .expectedName("not[condition: " + ALWAYS_FALSE.name() + "]")
                .expectedExpectedValue("Condition unsatisfied")
                .expectedActualValue("Condition unsatisfied")
                .addTest();
    }

    /**
     * An extension point that allows for custom test cases to be added and executed using the supplied builder.
     *
     * @param builder
     *     THe builder used to create new test cases.
     */
    protected void extendTestCases(ExpressionTestCaseBuilder builder) {}
}
