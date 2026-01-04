package io.github.libzeal.zeal.values.api.sequence;

import io.github.libzeal.zeal.logic.util.Formatter;
import io.github.libzeal.zeal.values.api.StandardRationales;
import io.github.libzeal.zeal.values.api.StandardRationales.Names;
import io.github.libzeal.zeal.values.api.ValueBuilder;
import io.github.libzeal.zeal.values.api.cache.CachedValueBuilder;
import io.github.libzeal.zeal.values.api.cache.SequenceCaches;
import io.github.libzeal.zeal.values.api.cache.SimpleCacheResult;
import io.github.libzeal.zeal.values.api.sequence.SequenceValueBuilder.SequenceOperations;

import java.util.Objects;

import static io.github.libzeal.zeal.logic.util.Formatter.stringify;
import static io.github.libzeal.zeal.values.api.StandardRationales.Names.*;

public class OrderedSequenceValueBuilder {

    public interface OrderedSequenceOperations<T, S> extends SequenceOperations<T, S> {
        T lastElement(S haystack, T desired);
        T atIndex(S haystack, int index, T desired);
        int indexOf(S haystack, T needle);
        T firstElement(S haystack, T desired);
    }

    public static <T, S> ValueBuilder<S> hasAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final T found = ops.atIndex(s, index, desired);

                return SimpleCacheResult.of(Objects.equals(desired, found))
                    .withCache(SequenceCaches.element(found));
            })
            .name(withPairs("hasAtIndex", "desired", desiredName, "index", index))
            .expected(desiredName)
            .actual(context ->
                context.cache().value().getOrElseGet(
                    Formatter::stringify,
                    () -> "Index " + index + " is out of bounds"
                ))
            .hint(context ->
                context.cache().value().getOrElse(
                    "Index " + index + " is beyond Value length of " + ops.size(context.subject()),
                    "Index " + index + " is within bounds, but the desired element was not found there"
                ));
    }

    public static <T, S> ValueBuilder<S> doesNotHaveAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final T found = ops.atIndex(s, index, desired);

                return SimpleCacheResult.of(!Objects.equals(desired, found))
                    .withCache(SequenceCaches.element(found));
            })
            .name(withPairs("doesNotHaveAtIndex", "desired", desiredName, "index", index))
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.cache().value().getOrElseGet(
                    Formatter::stringify,
                    () -> "Index " + index + " is out of bounds"
                ));
    }

    public static <T, S> ValueBuilder<S> startsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final T found = ops.firstElement(s, desired);

                return SimpleCacheResult.of(Objects.equals(desired, found))
                    .withCache(SequenceCaches.element(found));
            })
            .name(withValue("startsWith", desiredName))
            .expected(desiredName)
            .actual(context ->
                context.cache().value().getOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                ops.size(context.subject()) == 0 ? "Value is empty" : "Value has at least one element, but the first element is not " + desiredName
            );
    }

    public static <T, S> ValueBuilder<S> doesNotStartWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final T found = ops.firstElement(s, desired);

                return SimpleCacheResult.of(desired == null || !Objects.equals(desired, found))
                    .withCache(SequenceCaches.element(found));
            })
            .name(withValue("doesNotStartWith", desiredName))
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.cache().value().getOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                "The first element is " + desiredName
            );
    }

    public static <T, S> ValueBuilder<S> endsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final T found = ops.lastElement(s, desired);

                return SimpleCacheResult.of(Objects.equals(desired, found))
                    .withCache(SequenceCaches.element(found));
            })
            .name(withValue("endsWith", desiredName))
            .expected(desiredName)
            .actual(context ->
                context.cache().value().getOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                ops.size(context.subject()) == 0 ? "Value is empty" : "Value has at least one element, but the last element is not " + desiredName
            );
    }

    public static <T, S> ValueBuilder<S> doesNotEndWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final T found = ops.lastElement(s, desired);

                return SimpleCacheResult.of(desired == null || !Objects.equals(desired, found))
                    .withCache(SequenceCaches.element(found));
            })
            .name(withValue("doesNotEndWith", desiredName))
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.cache().value().getOrElseGet(Formatter::stringify, () -> "Value is empty")
            )
            .hint(context ->
                "The last element is " + desiredName
            );
    }

    public static <T, S> ValueBuilder<S> includes(final T desired, final OrderedSequenceOperations<T, S> ops) {
        return CachedValueBuilder.of((S s) -> {

                final int index = ops.indexOf(s, desired);

                return SimpleCacheResult.of(index > -1)
                    .withCache(SequenceCaches.index(index));
            })
            .name(StandardRationales.includes(desired))
            .expected(StandardRationales.includes(desired))
            .actual(context -> context.passed() ? StandardRationales.includes(desired) : StandardRationales.excludes(desired))
            .hint(context -> StandardRationales.needleInHaystackHint(context.cache().value(), desired));
    }

    public static <T, S> ValueBuilder<S> excludes(final T desired, final OrderedSequenceOperations<T, S> ops) {
        return CachedValueBuilder.of((S s) -> {

                final int index = ops.indexOf(s, desired);

                return SimpleCacheResult.of(index == -1)
                    .withCache(SequenceCaches.index(index));
            })
            .name(StandardRationales.excludes(desired))
            .expected(StandardRationales.excludes(desired))
            .actual(context -> context.passed() ? StandardRationales.excludes(desired) : StandardRationales.includes(desired))
            .hint(context -> StandardRationales.needleInHaystackHint(context.cache().value(), desired));
    }
}
