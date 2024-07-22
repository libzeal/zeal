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

public class ExpressionTestCaseBuilder {

    private final List<Arguments> arguments;

    public static ExpressionTestCaseBuilder newBuilder() {
        return new ExpressionTestCaseBuilder();
    }

    public ExpressionTestCaseBuilder() {
        this.arguments = new ArrayList<>();
    }

    public ArgumentBuilder newTest(BiConsumer<ObjectExpression<Object, ?>, ?> modifier) {
        return new ArgumentBuilder(this, modifier);
    }

    private void addArgument(Arguments argument) {
        this.arguments.add(argument);
    }

    public Stream<Arguments> stream() {
        return arguments.stream();
    }

    public static class ArgumentBuilder {

        private final ExpressionTestCaseBuilder testCaseBuilder;
        private final BiConsumer<ObjectExpression<Object, ?>, ?> modifier;
        private String testCaseName;
        private Object value;
        private EvaluationState state;
        private Function<Object, String> name;
        private BiFunction<?, ?, String> expectedValue;
        private BiFunction<?, ?, String> actualValue;

        public ArgumentBuilder(ExpressionTestCaseBuilder testCaseBuilder, BiConsumer<ObjectExpression<Object, ?>, ?> modifier) {
            this.testCaseBuilder = testCaseBuilder;
            this.modifier = modifier;
        }

        public ArgumentBuilder testCaseName(String name) {
            this.testCaseName = name;
            return this;
        }

        public ArgumentBuilder value(Object value) {
            this.value = value;
            return this;
        }

        public ArgumentBuilder expectedState(EvaluationState state) {
            this.state = state;
            return this;
        }

        public ArgumentBuilder expectedName(String name) {
            return expectedName(v -> name);
        }

        public ArgumentBuilder expectedName(Function<Object, String> name) {
            this.name = name;
            return this;
        }

        public ArgumentBuilder expectedExpectedValue(String value) {
            return expectedExpectedValue((e, v) -> value);
        }

        public ArgumentBuilder expectedExpectedValue(BiFunction<?, ?, String> value) {
            this.expectedValue = value;
            return this;
        }

        public ArgumentBuilder expectedActualValue(String value) {
            return expectedActualValue((e, v) -> value);
        }

        public ArgumentBuilder expectedActualValue(BiFunction<?, ?, String> value) {
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

        public ExpressionTestCaseBuilder addTest() {
            this.testCaseBuilder.addArgument(toArguments());
            return testCaseBuilder;
        }
    }
}
