package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;
import io.github.libzeal.zeal.logic.util.Formatter;
import io.github.libzeal.zeal.values.sdk.StandardRationales;
import io.github.libzeal.zeal.values.sdk.cache.CachedValueBuilder;
import io.github.libzeal.zeal.values.sdk.cache.SimpleCacheResult;

import static io.github.libzeal.zeal.logic.util.Formatter.stringify;
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

                return SimpleCacheResult.of(found.matches(desired))
                    .withCache(SequenceCaches.element(found));
            })
            .name(withPairs("hasAtIndex", "desired", desiredName, "index", index))
            .expected(desiredName)
            .actual(context ->
                context.cache().value().ifPresentOrElseGet(
                    Formatter::stringify,
                    () -> "Index " + index + " is out of bounds"
                ))
            .hint(context ->
                context.cache().value().isPresentOrElse(
                    "Index " + index + " is out of bounds",
                    "Index " + index + " is within bounds, but the desired element was not found there"
                ))
            .build();
    }

    public static <T, S> ComputableExpression<S> doesNotHaveAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final Element<T> found = ops.atIndex(s, index, desired);

                return SimpleCacheResult.of(!found.matches(desired))
                    .withCache(SequenceCaches.element(found));
            })
            .name(withPairs("doesNotHaveAtIndex", "desired", desiredName, "index", index))
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.cache().value().ifPresentOrElseGet(
                    Formatter::stringify,
                    () -> "Index " + index + " is out of bounds"
                ))
            .build();
    }

    public static <T, S> ComputableExpression<S> startsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final Element<T> found = ops.firstElement(s, desired);

                return SimpleCacheResult.of(found.matches(desired))
                    .withCache(SequenceCaches.element(found));
            })
            .name(withValue("startsWith", desiredName))
            .expected(desiredName)
            .actual(context ->
                context.cache().value().ifPresentOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                context.cache().value().isPresentOrElse("Value has at least one element, but the first element is not " + desiredName, "Value is empty")
            )
            .build();
    }

    public static <T, S> ComputableExpression<S> doesNotStartWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final Element<T> found = ops.firstElement(s, desired);

                return SimpleCacheResult.of(!found.matches(desired))
                    .withCache(SequenceCaches.element(found));
            })
            .name(withValue("doesNotStartWith", desiredName))
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.cache().value().ifPresentOrElseGet(Formatter::stringify, () -> "Value is empty")
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

                return SimpleCacheResult.of(found.matches(desired))
                    .withCache(SequenceCaches.element(found));
            })
            .name(withValue("endsWith", desiredName))
            .expected(desiredName)
            .actual(context ->
                context.cache().value().ifPresentOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                context.cache().value().isPresentOrElse("Value has at least one element, but the last element is not " + desiredName, "Value is empty")
            )
            .build();
    }

    public static <T, S> ComputableExpression<S> doesNotEndWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final Element<T> found = ops.lastElement(s, desired);

                return SimpleCacheResult.of(!found.matches(desired))
                    .withCache(SequenceCaches.element(found));
            })
            .name(withValue("doesNotEndWith", desiredName))
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.cache().value().ifPresentOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                "The last element is " + desiredName
            )
            .build();
    }

    public static <T, S> ComputableExpression<S> includes(final T desired, final OrderedSequenceOperations<T, S> ops) {
        return CachedValueBuilder.of((S s) -> {

                final int index = ops.indexOf(s, desired);

                return SimpleCacheResult.of(index > -1)
                    .withCache(SequenceCaches.index(index));
            })
            .name(StandardRationales.includes(desired))
            .expected(StandardRationales.includes(desired))
            .actual(context -> context.passed() ? StandardRationales.includes(desired) : StandardRationales.excludes(desired))
            .hint(context -> StandardRationales.needleInHaystackHint(context.cache().value(), desired))
            .build();
    }

    public static <T, S> ComputableExpression<S> excludes(final T desired, final OrderedSequenceOperations<T, S> ops) {
        return CachedValueBuilder.of((S s) -> {

                final int index = ops.indexOf(s, desired);

                return SimpleCacheResult.of(index == -1)
                    .withCache(SequenceCaches.index(index));
            })
            .name(StandardRationales.excludes(desired))
            .expected(StandardRationales.excludes(desired))
            .actual(context -> context.passed() ? StandardRationales.excludes(desired) : StandardRationales.includes(desired))
            .hint(context -> StandardRationales.needleInHaystackHint(context.cache().value(), desired))
            .build();
    }
}
