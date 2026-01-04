package io.github.libzeal.zeal.values.api.sequence;

import io.github.libzeal.zeal.values.api.ValueBuilder;
import io.github.libzeal.zeal.values.api.cache.CachedValueBuilder;
import io.github.libzeal.zeal.values.api.cache.SequenceCaches;
import io.github.libzeal.zeal.values.api.cache.SimpleCacheResult;
import io.github.libzeal.zeal.values.api.sequence.SequenceValueBuilder.SequenceOperations;

import static io.github.libzeal.zeal.logic.util.Formatter.stringify;

public class RepeatableSequenceValueBuilder {

    public static <T, S> ValueBuilder<S> includesExactly(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final long count = ops.occurrences(s, desired);

                return SimpleCacheResult.of(count == times)
                    .withCache(SequenceCaches.count(count));
            })
            .name("includesExactly[desired=" + desiredAsString + ", times=" + times + "]")
            .expected("found '" + desiredAsString + "' " + times + " " + timesSuffix(times))
            .actual(context ->
                "found '" + desiredAsString + "' " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .hint(context ->
                context.cache().value() == 0 ?
                    "Desired value not found in array" :
                    "Desired value was found, but " + context.cache().value() + " " + timesSuffix(context.cache().value())
            );
    }

    private static String timesSuffix(final long times) {
        return times == 1 ? "time" : "times";
    }

    public static <T, S> ValueBuilder<S> includesMoreThan(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final long count = ops.occurrences(s, desired);

                return SimpleCacheResult.of(count > times)
                    .withCache(SequenceCaches.count(count));
            })
            .name("includesMoreThan[desired=" + desiredAsString + ", times=" + times + "]")
            .expected("found '" + desiredAsString + "' more than " + times + " " + timesSuffix(times))
            .actual(context ->
                "found '" + desiredAsString + "' " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .hint(context ->
                context.cache().value() == 0 ?
                    "Desired value not found in array" :
                    "Desired value was found, but " + context.cache().value() + " " + timesSuffix(context.cache().value())
            );
    }

    public static <T, S> ValueBuilder<S> includesMoreThanOrEqualTo(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final long count = ops.occurrences(s, desired);

                return SimpleCacheResult.of(count >= times)
                    .withCache(SequenceCaches.count(count));
            })
            .name("includesMoreThanOrEqualTo[desired=" + desiredAsString + ", times=" + times + "]")
            .expected("found '" + desiredAsString + "' more than or equal to " + times + " " + timesSuffix(times))
            .actual(context ->
                "found '" + desiredAsString + "' " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .hint(context ->
                context.cache().value() == 0 ?
                    "Desired value not found in array" :
                    "Desired value was found, but " + context.cache().value() + " " + timesSuffix(context.cache().value())
            );
    }

    public static <T, S> ValueBuilder<S> includesLessThan(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final long count = ops.occurrences(s, desired);

                return SimpleCacheResult.of(count < times)
                    .withCache(SequenceCaches.count(count));
            })
            .name("includesLessThan[desired=" + desiredAsString + ", times=" + times + "]")
            .expected("found '" + desiredAsString + "' less than " + times + " " + timesSuffix(times))
            .actual(context ->
                "found '" + desiredAsString + "' " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .hint(context ->
                context.cache().value() == 0 ?
                    "Desired value not found in array" :
                    "Desired value was found, but " + context.cache().value() + " " + timesSuffix(context.cache().value())
            );
    }

    public static <T, S> ValueBuilder<S> includesLessThanOrEqualTo(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final long count = ops.occurrences(s, desired);

                return SimpleCacheResult.of(count <= times)
                    .withCache(SequenceCaches.count(count));
            })
            .name("includesLessThanOrEqualTo[desired=" + desiredAsString + ", times=" + times + "]")
            .expected("found '" + desiredAsString + "' less than or equal to " + times + " " + timesSuffix(times))
            .actual(context ->
                "found '" + desiredAsString + "' " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .hint(context ->
                context.cache().value() == 0 ?
                    "Desired value not found in array" :
                    "Desired value was found, but " + context.cache().value() + " " + timesSuffix(context.cache().value())
            );
    }

    public interface RepeatableSequenceOperations<T, S> extends SequenceOperations<T, S> {
        int occurrences(S haystack, T needle);
    }
}
