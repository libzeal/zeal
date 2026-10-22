package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.logic.format.Formatter;
import io.github.libzeal.zeal.logic.unary.ComputableExpression;
import io.github.libzeal.zeal.logic.unary.ExpressionBuilder;
import io.github.libzeal.zeal.values.sdk.StandardRationales;
import io.github.libzeal.zeal.values.sdk.StandardRationales.Operators;

import static io.github.libzeal.zeal.logic.format.Formatter.stringify;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withPairs;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withValue;

public class OrderedSequenceExpressions {

    public interface OrderedSequenceOperations<T, S> {
        Element<T> lastElement(S haystack, T desired);
        Element<T> atIndex(S haystack, int index, T desired);
        int indexOf(S haystack, T needle);
        int lastIndexOf(S haystack, T needle);
        Element<T> firstElement(S haystack, T desired);
    }

    public static <T, S> ComputableExpression<S> lastIndexOfIs(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {
        return ExpressionBuilder.observing((S s) -> ops.lastIndexOf(s, desired))
            .passesIf(lastIndex -> lastIndex == index)
            .name(Operators.EQ.display(lastIndexOfName(desired), index))
            .expected(index)
            .build();
    }

    public static <T, S> ComputableExpression<S> lastIndexOfIsNot(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {
        return ExpressionBuilder.observing((S s) -> ops.lastIndexOf(s, desired))
            .passesIf(lastIndex -> lastIndex != index)
            .name(Operators.NE.display(lastIndexOfName(desired), index))
            .expected("not[" + index + "]")
            .build();
    }

    private static <T> String lastIndexOfName(final T desired) {
        return "lastIndexOf[" + stringify(desired) + "]";
    }

    public static <T, S> ComputableExpression<S> hasAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ExpressionBuilder.observing((S s) -> ops.atIndex(s, index, desired))
            .passesIf(found -> found.matches(desired))
            .name(withPairs("hasAtIndex", "desired", desiredName, "index", index))
            .expected(desiredName)
            .actual(context ->
                context.observed().ifPresentOrElseGet(
                    Formatter::stringify,
                    () -> "Index " + index + " is out of bounds"
                ))
            .hint(context -> {
                if (context.passed()) {
                    return "Desired element found at index " + index;
                }
                else {
                    return context.observed().isPresentOrElse(
                        "Index " + index + " is within bounds, but the desired element was not found there",
                        "Index " + index + " is out of bounds"
                    );
                }
            })
            .build();
    }

    public static <T, S> ComputableExpression<S> doesNotHaveAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ExpressionBuilder.observing((S s) -> ops.atIndex(s, index, desired))
            .passesIf(found -> !found.matches(desired))
            .name(withPairs("doesNotHaveAtIndex", "desired", desiredName, "index", index))
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.observed().ifPresentOrElseGet(
                    Formatter::stringify,
                    () -> "Index " + index + " is out of bounds"
                ))
            .build();
    }

    public static <T, S> ComputableExpression<S> startsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ExpressionBuilder.observing((S s) -> ops.firstElement(s, desired))
            .passesIf(found -> found.matches(desired))
            .name(withValue("startsWith", desiredName))
            .expected(desiredName)
            .actual(context ->
                context.observed().ifPresentOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context -> {
                if (context.passed()) {
                    return "The first element is " + desiredName;
                }
                else {
                    return context.observed().isPresentOrElse(
                        "Value has at least one element, but the first element is not " + desiredName,
                        "Value is empty"
                    );
                }
            })
            .build();
    }

    public static <T, S> ComputableExpression<S> doesNotStartWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ExpressionBuilder.observing((S s) -> ops.firstElement(s, desired))
            .passesIf(found -> !found.matches(desired))
            .name(withValue("doesNotStartWith", desiredName))
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.observed().ifPresentOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                context.observed().ifPresentOrElseGet(
                    element -> "The first element is " + stringify(element),
                    () -> "Value is empty"
                )
            )
            .build();
    }

    public static <T, S> ComputableExpression<S> endsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ExpressionBuilder.observing((S s) -> ops.lastElement(s, desired))
            .passesIf(found -> found.matches(desired))
            .name(withValue("endsWith", desiredName))
            .expected(desiredName)
            .actual(context ->
                context.observed().ifPresentOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context -> {
                if (context.passed()) {
                    return "The last element is " + desiredName;
                }
                else {
                    return context.observed().isPresentOrElse(
                        "Value has at least one element, but the last element is not " + desiredName,
                        "Value is empty"
                    );
                }
            })
            .build();
    }

    public static <T, S> ComputableExpression<S> doesNotEndWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ExpressionBuilder.observing((S s) -> ops.lastElement(s, desired))
            .passesIf(found -> !found.matches(desired))
            .name(withValue("doesNotEndWith", desiredName))
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.observed().ifPresentOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                context.observed().ifPresentOrElseGet(
                    element -> "The last element is " + stringify(element),
                    () -> "Value is empty"
                )
            )
            .build();
    }

    public static <T, S> ComputableExpression<S> includes(final T desired, final OrderedSequenceOperations<T, S> ops) {
        return ExpressionBuilder.observing((S s) -> ops.indexOf(s, desired))
            .passesIf(index -> index > -1)
            .name(StandardRationales.includes(desired))
            .expected(StandardRationales.includes(desired))
            .actual(context -> context.passed() ? StandardRationales.includes(desired) : StandardRationales.excludes(desired))
            .hint(context -> StandardRationales.needleInHaystackHint(context.observed(), desired))
            .build();
    }

    public static <T, S> ComputableExpression<S> excludes(final T desired, final OrderedSequenceOperations<T, S> ops) {
        return ExpressionBuilder.observing((S s) -> ops.indexOf(s, desired))
            .passesIf(index -> index == -1)
            .name(StandardRationales.excludes(desired))
            .expected(StandardRationales.excludes(desired))
            .actual(context -> context.passed() ? StandardRationales.excludes(desired) : StandardRationales.includes(desired))
            .hint(context -> StandardRationales.needleInHaystackHint(context.observed(), desired))
            .build();
    }
}
