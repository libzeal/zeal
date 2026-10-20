package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.logic.format.Formatter;
import io.github.libzeal.zeal.logic.unary.cache.CachedResult;
import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;
import io.github.libzeal.zeal.values.sdk.CachedValueBuilder;
import io.github.libzeal.zeal.values.sdk.StandardRationales;

import static io.github.libzeal.zeal.logic.format.Formatter.stringify;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withPairs;
import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withValue;

public class OrderedSequenceExpressions {

    public interface OrderedSequenceOperations<T, S> {
        Element<T> lastElement(S haystack, T desired);
        Element<T> atIndex(S haystack, int index, T desired);
        int indexOf(S haystack, T needle);
        Element<T> firstElement(S haystack, T desired);
    }

    public static <T, S> ComputableExpression<S> hasAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final Element<T> found = ops.atIndex(s, index, desired);

                return CachedResult.of(found.matches(desired))
                    .withCache(found);
            })
            .name(withPairs("hasAtIndex", "desired", desiredName, "index", index))
            .expected(desiredName)
            .actual(context ->
                context.cached().ifPresentOrElseGet(
                    Formatter::stringify,
                    () -> "Index " + index + " is out of bounds"
                ))
            .hint(context ->
                context.cached().isPresentOrElse(
                    "Index " + index + " is out of bounds",
                    "Index " + index + " is within bounds, but the desired element was not found there"
                ))
            .build();
    }

    public static <T, S> ComputableExpression<S> doesNotHaveAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final Element<T> found = ops.atIndex(s, index, desired);

                return CachedResult.of(!found.matches(desired))
                    .withCache(found);
            })
            .name(withPairs("doesNotHaveAtIndex", "desired", desiredName, "index", index))
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.cached().ifPresentOrElseGet(
                    Formatter::stringify,
                    () -> "Index " + index + " is out of bounds"
                ))
            .build();
    }

    public static <T, S> ComputableExpression<S> startsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final Element<T> found = ops.firstElement(s, desired);

                return CachedResult.of(found.matches(desired))
                    .withCache(found);
            })
            .name(withValue("startsWith", desiredName))
            .expected(desiredName)
            .actual(context ->
                context.cached().ifPresentOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                context.cached().isPresentOrElse("Value has at least one element, but the first element is not " + desiredName, "Value is empty")
            )
            .build();
    }

    public static <T, S> ComputableExpression<S> doesNotStartWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final Element<T> found = ops.firstElement(s, desired);

                return CachedResult.of(!found.matches(desired))
                    .withCache(found);
            })
            .name(withValue("doesNotStartWith", desiredName))
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.cached().ifPresentOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                "The first element is " + desiredName
            )
            .build();
    }

    public static <T, S> ComputableExpression<S> endsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final Element<T> found = ops.lastElement(s, desired);

                return CachedResult.of(found.matches(desired))
                    .withCache(found);
            })
            .name(withValue("endsWith", desiredName))
            .expected(desiredName)
            .actual(context ->
                context.cached().ifPresentOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                context.cached().isPresentOrElse("Value has at least one element, but the last element is not " + desiredName, "Value is empty")
            )
            .build();
    }

    public static <T, S> ComputableExpression<S> doesNotEndWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final Element<T> found = ops.lastElement(s, desired);

                return CachedResult.of(!found.matches(desired))
                    .withCache(found);
            })
            .name(withValue("doesNotEndWith", desiredName))
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.cached().ifPresentOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                "The last element is " + desiredName
            )
            .build();
    }

    public static <T, S> ComputableExpression<S> includes(final T desired, final OrderedSequenceOperations<T, S> ops) {
        return CachedValueBuilder.of((S s) -> {

                final int index = ops.indexOf(s, desired);

                return CachedResult.of(index > -1)
                    .withCache(index);
            })
            .name(StandardRationales.includes(desired))
            .expected(StandardRationales.includes(desired))
            .actual(context -> context.passed() ? StandardRationales.includes(desired) : StandardRationales.excludes(desired))
            .hint(context -> StandardRationales.needleInHaystackHint(context.cached(), desired))
            .build();
    }

    public static <T, S> ComputableExpression<S> excludes(final T desired, final OrderedSequenceOperations<T, S> ops) {
        return CachedValueBuilder.of((S s) -> {

                final int index = ops.indexOf(s, desired);

                return CachedResult.of(index == -1)
                    .withCache(index);
            })
            .name(StandardRationales.excludes(desired))
            .expected(StandardRationales.excludes(desired))
            .actual(context -> context.passed() ? StandardRationales.excludes(desired) : StandardRationales.includes(desired))
            .hint(context -> StandardRationales.needleInHaystackHint(context.cached(), desired))
            .build();
    }
}
