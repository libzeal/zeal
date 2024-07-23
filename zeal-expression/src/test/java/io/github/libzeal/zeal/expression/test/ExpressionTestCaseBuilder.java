package io.github.libzeal.zeal.expression.test;

import io.github.libzeal.zeal.expression.evaluation.EvaluationState;
import io.github.libzeal.zeal.expression.types.ObjectExpression;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class ExpressionTestCaseBuilder<T, E extends ObjectExpression<T, E>> {

    private final List<Arguments> arguments;

    public static <A, B extends ObjectExpression<A, B>> ExpressionTestCaseBuilder<A, B> newBuilder() {
        return new ExpressionTestCaseBuilder<>();
    }

    public ExpressionTestCaseBuilder() {
        this.arguments = new ArrayList<>();
    }

    public ArgumentBuilder<T, E> newTest(BiConsumer<E, T> modifier) {
        return new ArgumentBuilder<>(this, modifier);
    }

    private void addArgument(Arguments argument) {
        this.arguments.add(argument);
    }

    public Stream<Arguments> stream() {
        return arguments.stream();
    }

    public static class ArgumentBuilder<T, E extends ObjectExpression<T, E>> {

        private final ExpressionTestCaseBuilder<T, E> testCaseBuilder;
        private final BiConsumer<E, T> modifier;
        private String testCaseName;
        private T value;
        private EvaluationState state;
        private Function<T, String> name;
        private BiFunction<?, ?, String> expectedValue;
        private BiFunction<?, ?, String> actualValue;

        public ArgumentBuilder(ExpressionTestCaseBuilder<T, E> testCaseBuilder, BiConsumer<E, T> modifier) {
            this.testCaseBuilder = testCaseBuilder;
            this.modifier = modifier;
        }

        public ArgumentBuilder<T, E> testCaseName(String name) {
            this.testCaseName = name;
            return this;
        }

        public ArgumentBuilder<T, E> value(T value) {
            this.value = value;
            return this;
        }

        public ArgumentBuilder<T, E> expectedState(EvaluationState state) {
            this.state = state;
            return this;
        }

        public ArgumentBuilder<T, E> expectedName(String name) {
            return expectedName(v -> name);
        }

        public ArgumentBuilder<T, E> expectedName(Function<T, String> name) {
            this.name = name;
            return this;
        }

        public ArgumentBuilder<T, E> expectedExpectedValue(String value) {
            return expectedExpectedValue((e, v) -> value);
        }

        public ArgumentBuilder<T, E> expectedExpectedValue(BiFunction<?, ?, String> value) {
            this.expectedValue = value;
            return this;
        }

        public ArgumentBuilder<T, E> expectedActualValue(String value) {
            return expectedActualValue((e, v) -> value);
        }

        public ArgumentBuilder<T, E> expectedActualValue(BiFunction<T, ?, String> value) {
            this.actualValue = value;
            return this;
        }

        private String testCaseName() {

            if (testCaseName == null) {
                return name.apply(value) + ": (value: " + value + ")";
            }
            else {
                return testCaseName;
            }
        }

        private Arguments toArguments() {
            return Arguments.of(testCaseName(), modifier, value, state, name, expectedValue, actualValue);
        }

        public ExpressionTestCaseBuilder<T, E> addTest() {
            this.testCaseBuilder.addArgument(toArguments());
            return testCaseBuilder;
        }
    }
}
