package io.github.libzeal.zeal.values.api.sequence;

import io.github.libzeal.zeal.logic.util.Formatter;
import io.github.libzeal.zeal.values.api.StandardRationales;
import io.github.libzeal.zeal.values.api.ValueBuilder;
import io.github.libzeal.zeal.values.api.cache.CachedValueBuilder;
import io.github.libzeal.zeal.values.api.cache.SequenceCaches;
import io.github.libzeal.zeal.values.api.cache.SimpleCacheResult;
import io.github.libzeal.zeal.values.api.sequence.SequenceValueBuilder.SequenceOperations;

import java.util.Objects;

import static io.github.libzeal.zeal.logic.util.Formatter.stringify;

public class OrderedSequenceValueBuilder {

    public interface OrderedSequenceOperations<T, S> extends SequenceOperations<T, S> {
        T lastElement(final S haystack);
        T atIndex(S haystack, int index);
        int indexOf(S haystack, T needle);
    }

    public static <T, S> ValueBuilder<S> hasAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final T found = ops.atIndex(s, index);

                return SimpleCacheResult.of(Objects.equals(desired, found))
                    .withCache(SequenceCaches.element(found));
            })
            .name("hasAtIndex[desired=" + desiredName + ", index=" + index + "]")
            .expected(desiredName)
            .actual(context ->
                context.cache().value().getOrElseGet(Formatter::stringify, () -> "(null)"))
            .hint(context ->
                context.cache().value().getOrElse(
                    "Index " + index + " is beyond array length of " + ops.size(context.subject()),
                    "Index " + index + " is within bounds, but the desired element was not found there"
                ));
    }

    public static <T, S> ValueBuilder<S> doesNotHaveAtIndex(final T desired, final int index, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final T found = ops.atIndex(s, index);

                return SimpleCacheResult.of(!Objects.equals(desired, found))
                    .withCache(SequenceCaches.element(found));
            })
            .name("doesNotHaveAtIndex[desired=" + desiredName + ", index=" + index + "]")
            .expected("not[" + desiredName + "]")
            .actual(context ->
                context.cache().value().getOrElse("(null)", desiredName
                ));
    }

    public static <T, S> ValueBuilder<S> startsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final T found = ops.atIndex(s, 0);

                return SimpleCacheResult.of(Objects.equals(desired, found))
                    .withCache(SequenceCaches.element(found));
            })
            .name("startsWith[" + desiredName + "]")
            .expected(desiredName)
            .actual(context ->
                context.cache().value().getOrElseGet(Formatter::stringify, () -> "(null)")
            )
            .hint(context ->
                ops.size(context.subject()) == 0 ? "Array is empty" : "Array has at least one element, but the first element is not " + desiredName
            );
    }

    public static <T, S> ValueBuilder<S> doesNotStartWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final T found = ops.atIndex(s, 0);

                return SimpleCacheResult.of(desired == null || !Objects.equals(desired, found))
                    .withCache(SequenceCaches.element(found));
            })
            .name("doesNotStartWith[" + desiredName + "]")
            .expected(desiredName)
            .actual(context ->
                context.cache().value().getOrElseGet(Formatter::stringify, () -> "(null)")
            )
            .hint(context ->
                "The first element is " + desiredName
            );
    }

    public static <T, S> ValueBuilder<S> endsWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final T found = ops.lastElement(s);

                return SimpleCacheResult.of(Objects.equals(desired, found))
                    .withCache(SequenceCaches.element(found));
            })
            .name("endsWith[" + desiredName + "]")
            .expected(desiredName)
            .actual(context ->
                context.cache().value().getOrElseGet(Formatter::stringify, () -> "(null)")
            )
            .hint(context ->
                ops.size(context.subject()) == 0 ? "Array is empty" : "Array has at least one element, but the last element is not " + desiredName
            );
    }

    public static <T, S> ValueBuilder<S> doesNotEndWith(final T desired, final OrderedSequenceOperations<T, S> ops) {

        final String desiredName = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final T found = ops.lastElement(s);

                return SimpleCacheResult.of(desired == null || !Objects.equals(desired, found))
                    .withCache(SequenceCaches.element(found));
            })
            .name("doesNotEndWith[" + desiredName + "]")
            .expected(desiredName)
            .actual(context ->
                context.cache().value().getOrElseGet(Formatter::stringify, () -> "(null)")
            )
            .hint(context ->
                "The last element is " + desiredName
            );
    }

    // TODO: Use indices for these two methods
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
