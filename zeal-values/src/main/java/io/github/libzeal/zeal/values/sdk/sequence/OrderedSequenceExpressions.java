package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.logic.format.ObjectFormatter;
import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.logic.unary.ConditionBuilder;
import io.github.libzeal.zeal.values.sdk.StandardRationales;
import io.github.libzeal.zeal.values.sdk.StandardRationales.Operators;

import static io.github.libzeal.zeal.logic.format.ObjectFormatter.stringify;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withPairs;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withValue;

public class OrderedSequenceExpressions {

    public interface OrderedSequenceOperations<T, S> extends SequenceOperations<T, S> {
        Element<T> lastElement(S haystack, T desired);

        Element<T> atIndex(S haystack, int index, T desired);

        int indexOf(S haystack, T needle);

        int lastIndexOf(S haystack, T needle);

        default Element<T> firstElement(final S haystack, final T desired) {
            return atIndex(haystack, 0, desired);
        }
    }

    public static <T, S> Condition<S> lastIndexOfIs(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {
        return ConditionBuilder.observing((S s) -> ops.lastIndexOf(s, desired))
                .passesIf(lastIndex -> lastIndex == index)
                .name(Operators.EQ.display(lastIndexOfName(desired), index))
                .expected(index)
                .build();
    }

    public static <T, S> Condition<S> lastIndexOfIsNot(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {
        return ConditionBuilder.observing((S s) -> ops.lastIndexOf(s, desired))
                .passesIf(lastIndex -> lastIndex != index)
                .name(Operators.NE.display(lastIndexOfName(desired), index))
                .expected("not[" + index + "]")
                .build();
    }

    private static <T> String lastIndexOfName(final T desired) {
        return "lastIndexOf[" + stringify(desired) + "]";
    }

    public static <T, S> Condition<S> hasAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.atIndex(s, index, desired))
                .passesIf(found -> found.matches(desired))
                .name(withPairs("hasAtIndex", "desired", desiredName, "index", index))
                .expected(desiredName)
                .actual(context ->
                        context.observed().ifPresentOrElseGet(
                                ObjectFormatter::stringify,
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

    public static <T, S> Condition<S> doesNotHaveAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.atIndex(s, index, desired))
                .passesIf(found -> !found.matches(desired))
                .name(withPairs("doesNotHaveAtIndex", "desired", desiredName, "index", index))
                .expected("not[" + desiredName + "]")
                .actual(context ->
                        context.observed().ifPresentOrElseGet(
                                ObjectFormatter::stringify,
                                () -> "Index " + index + " is out of bounds"
                        ))
                .build();
    }

    public static <T, S> Condition<S> startsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.firstElement(s, desired))
                .passesIf(found -> found.matches(desired))
                .name(withValue("startsWith", desiredName))
                .expected(desiredName)
                .actual(context ->
                        context.observed().ifPresentOrElseGet(ObjectFormatter::stringify, () -> "Value is empty")
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

    public static <T, S> Condition<S> doesNotStartWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.firstElement(s, desired))
                .passesIf(found -> !found.matches(desired))
                .name(withValue("doesNotStartWith", desiredName))
                .expected("not[" + desiredName + "]")
                .actual(context ->
                        context.observed().ifPresentOrElseGet(ObjectFormatter::stringify, () -> "Value is empty")
                )
                .hint(context ->
                        context.observed().ifPresentOrElseGet(
                                element -> "The first element is " + stringify(element),
                                () -> "Value is empty"
                        )
                )
                .build();
    }

    public static <T, S> Condition<S> endsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.lastElement(s, desired))
                .passesIf(found -> found.matches(desired))
                .name(withValue("endsWith", desiredName))
                .expected(desiredName)
                .actual(context ->
                        context.observed().ifPresentOrElseGet(ObjectFormatter::stringify, () -> "Value is empty")
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

    public static <T, S> Condition<S> doesNotEndWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return ConditionBuilder.observing((S s) -> ops.lastElement(s, desired))
                .passesIf(found -> !found.matches(desired))
                .name(withValue("doesNotEndWith", desiredName))
                .expected("not[" + desiredName + "]")
                .actual(context ->
                        context.observed().ifPresentOrElseGet(ObjectFormatter::stringify, () -> "Value is empty")
                )
                .hint(context ->
                        context.observed().ifPresentOrElseGet(
                                element -> "The last element is " + stringify(element),
                                () -> "Value is empty"
                        )
                )
                .build();
    }

    public static <T, S> Condition<S> includes(final T desired, final OrderedSequenceOperations<T, S> ops) {
        return ConditionBuilder.observing((S s) -> ops.indexOf(s, desired))
                .passesIf(index -> index > -1)
                .name(StandardRationales.includes(desired))
                .expected(StandardRationales.includes(desired))
                .actual(context -> context.passed() ? StandardRationales.includes(desired) : StandardRationales.excludes(desired))
                .hint(context -> StandardRationales.needleInHaystackHint(context.observed(), desired))
                .build();
    }

    public static <T, S> Condition<S> excludes(final T desired, final OrderedSequenceOperations<T, S> ops) {
        return ConditionBuilder.observing((S s) -> ops.indexOf(s, desired))
                .passesIf(index -> index == -1)
                .name(StandardRationales.excludes(desired))
                .expected(StandardRationales.excludes(desired))
                .actual(context -> context.passed() ? StandardRationales.excludes(desired) : StandardRationales.includes(desired))
                .hint(context -> StandardRationales.needleInHaystackHint(context.observed(), desired))
                .build();
    }
}
