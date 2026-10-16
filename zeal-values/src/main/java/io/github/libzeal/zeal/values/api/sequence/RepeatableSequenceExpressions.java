package io.github.libzeal.zeal.values.api.sequence;

import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;
import io.github.libzeal.zeal.values.api.cache.CachedValueBuilder;
import io.github.libzeal.zeal.values.api.cache.SimpleCacheResult;

import static io.github.libzeal.zeal.logic.util.Formatter.stringify;
import static io.github.libzeal.zeal.values.api.StandardRationales.Names.withPairs;

public class RepeatableSequenceExpressions {

    public static <T, S> ComputableExpression<S> includesExactly(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final long count = ops.occurrences(s, desired);

                return SimpleCacheResult.of(count == times)
                    .withCache(SequenceCaches.count(count));
            })
            .name(withPairs("includesExactly", "desired", desiredAsString, "times", times))
            .expected("found '" + desiredAsString + "' " + times + " " + timesSuffix(times))
            .actual(context ->
                "found '" + desiredAsString + "' " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .hint(context ->
                context.cache().value() == 0 ?
                    "Desired value not found in array" :
                    "Desired value was found, but " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .build();
    }

    private static String timesSuffix(final long times) {
        return times == 1 ? "time" : "times";
    }

    public static <T, S> ComputableExpression<S> includesMoreThan(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final long count = ops.occurrences(s, desired);

                return SimpleCacheResult.of(count > times)
                    .withCache(SequenceCaches.count(count));
            })
            .name(withPairs("includesMoreThan", "desired", desiredAsString, "times", times))
            .expected("found '" + desiredAsString + "' more than " + times + " " + timesSuffix(times))
            .actual(context ->
                "found '" + desiredAsString + "' " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .hint(context ->
                context.cache().value() == 0 ?
                    "Desired value not found in array" :
                    "Desired value was found, but " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .build();
    }

    public static <T, S> ComputableExpression<S> includesMoreThanOrEqualTo(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final long count = ops.occurrences(s, desired);

                return SimpleCacheResult.of(count >= times)
                    .withCache(SequenceCaches.count(count));
            })
            .name(withPairs("includesMoreThanOrEqualTo", "desired", desiredAsString, "times", times))
            .expected("found '" + desiredAsString + "' more than or equal to " + times + " " + timesSuffix(times))
            .actual(context ->
                "found '" + desiredAsString + "' " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .hint(context ->
                context.cache().value() == 0 ?
                    "Desired value not found in array" :
                    "Desired value was found, but " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .build();
    }

    public static <T, S> ComputableExpression<S> includesLessThan(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final long count = ops.occurrences(s, desired);

                return SimpleCacheResult.of(count < times)
                    .withCache(SequenceCaches.count(count));
            })
            .name(withPairs("includesLessThan", "desired", desiredAsString, "times", times))
            .expected("found '" + desiredAsString + "' less than " + times + " " + timesSuffix(times))
            .actual(context ->
                "found '" + desiredAsString + "' " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .hint(context ->
                context.cache().value() == 0 ?
                    "Desired value not found in array" :
                    "Desired value was found, but " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .build();
    }

    public static <T, S> ComputableExpression<S> includesLessThanOrEqualTo(final T desired, final long times, final RepeatableSequenceOperations<T, S> ops) {

        final String desiredAsString = stringify(desired);

        return CachedValueBuilder.of((S s) -> {

                final long count = ops.occurrences(s, desired);

                return SimpleCacheResult.of(count <= times)
                    .withCache(SequenceCaches.count(count));
            })
            .name(withPairs("includesLessThanOrEqualTo", "desired", desiredAsString, "times", times))
            .expected("found '" + desiredAsString + "' less than or equal to " + times + " " + timesSuffix(times))
            .actual(context ->
                "found '" + desiredAsString + "' " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .hint(context ->
                context.cache().value() == 0 ?
                    "Desired value not found in array" :
                    "Desired value was found, but " + context.cache().value() + " " + timesSuffix(context.cache().value())
            )
            .build();
    }

    public interface RepeatableSequenceOperations<T, S> {
        int occurrences(S haystack, T needle);
    }
}
