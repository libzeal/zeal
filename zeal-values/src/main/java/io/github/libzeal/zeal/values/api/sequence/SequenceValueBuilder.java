package io.github.libzeal.zeal.values.api.sequence;

import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableField;
import io.github.libzeal.zeal.logic.util.Formatter;
import io.github.libzeal.zeal.values.api.StandardRationales;
import io.github.libzeal.zeal.values.api.SimpleValueBuilder;
import io.github.libzeal.zeal.values.api.StandardRationales.Operators;
import io.github.libzeal.zeal.values.api.ValueBuilder;
import io.github.libzeal.zeal.values.api.cache.CachedValueBuilder;
import io.github.libzeal.zeal.values.api.cache.SequenceCaches;
import io.github.libzeal.zeal.values.api.cache.SimpleCacheResult;
import io.github.libzeal.zeal.values.config.Configuration;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SequenceValueBuilder {

    private static final String LENGTH = "length";

    public interface SequenceOperations<T, S> {

        List<T> findAllIn(S haystack, Collection<T> needles);

        int size(S haystack);
        boolean isEmpty(S haystack);

        boolean includes(S haystack, T needle);
    }

    public static <T, S> ValueBuilder<S> isEmpty(final SequenceOperations<T, S> ops) {
        return SimpleValueBuilder.notNullable(ops::isEmpty)
            .name("isEmpty")
            .expected(Operators.EQ.display(LENGTH, 0))
            .actual(actualLength(ops));
    }

    private static <T, S> ComputableField<S> actualLength(final SequenceOperations<T, S> ops) {
        return context -> {
            final int size = ops.size(context.subject());
            return Operators.EQ.display(LENGTH, size);
        };
    }

    public static <T, S> ValueBuilder<S> isNotEmpty(final SequenceOperations<T, S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> !ops.isEmpty(s))
            .name("isNotEmpty")
            .expected(Operators.GT.display(LENGTH, 0))
            .actual(actualLength(ops));
    }

    public static <T, S> ValueBuilder<S> hasLengthOf(final int length, final SequenceOperations<T, S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.size(s) == length)
            .name("hasLength[" + length + "]")
            .expected(Operators.EQ.display(LENGTH, 0))
            .actual(actualLength(ops));
    }

    public static <T, S> ValueBuilder<S> doesNotHaveLengthOf(final int length, final SequenceOperations<T, S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.size(s) != length)
            .name("doesNotHaveLengthOf[" + length + "]")
            .expected(Operators.NE.display(LENGTH, 0))
            .actual(actualLength(ops));
    }

    public static <T, S> ValueBuilder<S> isShorterThan(final int length, final SequenceOperations<T, S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.size(s) < length)
            .name("isShortThan[" + length + "]")
            .expected(Operators.LT.display(LENGTH, 0))
            .actual(actualLength(ops));
    }

    public static <T, S> ValueBuilder<S> isShorterThanOrEqualTo(final int length, final SequenceOperations<T, S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.size(s) <= length)
            .name("isShortThanOrEqualTo[" + length + "]")
            .expected(Operators.LTE.display(LENGTH, 0))
            .actual(actualLength(ops));
    }

    public static <T, S> ValueBuilder<S> isLongerThan(final int length, final SequenceOperations<T, S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.size(s) > length)
            .name("isLongerThan[" + length + "]")
            .expected(Operators.GT.display(LENGTH, 0))
            .actual(actualLength(ops));
    }

    public static <T, S> ValueBuilder<S> isLongerThanOrEqualTo(final int length, final SequenceOperations<T, S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.size(s) >= length)
            .name("isLongerThanOrEqualTo[" + length + "]")
            .expected(Operators.GTE.display(LENGTH, 0))
            .actual(actualLength(ops));
    }

    public static <T, S> ValueBuilder<S> includes(final T desired, final SequenceOperations<T, S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.includes(s, desired))
            .name(StandardRationales.includes(desired))
            .expected(StandardRationales.includes(desired))
            .actual(context -> context.passed() ? StandardRationales.includes(desired) : StandardRationales.excludes(desired));
    }

    public static <T, S> ValueBuilder<S> includesAll(final Collection<T> desired,
                                                     final SequenceOperations<T, S> ops) {

        return CachedValueBuilder.of((S s) -> {

                if (desired == null) {
                    return SimpleCacheResult.ofPassed()
                        .withCache(SequenceCaches.<T>nonFound());
                }

                final List<T> found = ops.findAllIn(s, desired);

                return SimpleCacheResult.of(desired.size() == found.size())
                    .withCache(SequenceCaches.found(found));
            })
            .name("includesAll")
            .expected("All desired elements in subject")
            .actual(context ->
                context.passed() ?
                    "All desired elements in subject" :
                    "At least one desired element missing in subject"
            )
            .hint(context ->
                {
                    if (desired == null) {
                        return "Desired elements is (null), which passes by default";
                    }
                    else {
                        if (context.passed()) {
                            return "All desired elements found in subject";
                        }
                        else {
                            final List<String> missing = computeMissing(desired, context.cache().value());

                            return "The following desired elements were not found: " + missing;
                        }
                    }
                }
            );
    }

    public static <T, S> ValueBuilder<S> includesAny(final Collection<T> desired, final SequenceOperations<T, S> ops) {

        return CachedValueBuilder.of((S s) -> {

                if (desired == null) {
                    return SimpleCacheResult.ofPassed()
                        .withCache(SequenceCaches.<T>nonFound());
                }

                final List<T> found = ops.findAllIn(s, desired);

                return SimpleCacheResult.of(!found.isEmpty())
                    .withCache(SequenceCaches.found(found));
            })
            .name("includesAny")
            .expected("Any desired elements in subject")
            .actual(context ->
                context.passed() ?
                    "At least one desired element found in subject" :
                    "All desired elements missing in subject"
            )
            .hint(context ->
                {
                    if (desired == null) {
                        return "Desired elements is (null), which passes by default";
                    }
                    else {
                        if (context.passed()) {

                            final List<String> found = context.cache()
                                .value()
                                .stream()
                                .map(Formatter::stringify)
                                .collect(toList());

                            return "At least one desired element found in subject: " + found;
                        }
                        else {
                            return "All of the desired elements are missing in subject";
                        }
                    }
                }
            );
    }

    private static <T> List<String> computeMissing(final Collection<T> desired, final List<T> found) {

        final boolean truncated = desired.size() > Configuration.MAX_LIST_DISPLAY_LENGTH;

        final List<String> missing = desired.stream()
            .filter(d -> !found.contains(d))
            .limit(Configuration.MAX_LIST_DISPLAY_LENGTH)
            .map(Formatter::stringify)
            .collect(toList());

        if (truncated) {
            missing.add("...");
        }

        return missing;
    }

    public static <T, S> ValueBuilder<S> excludes(final T desired, final SequenceOperations<T, S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> !ops.includes(s, desired))
            .name(StandardRationales.excludes(desired))
            .expected(StandardRationales.excludes(desired))
            .actual(context -> context.passed() ? StandardRationales.excludes(desired) : StandardRationales.includes(desired));
    }

    public static <T, S> ValueBuilder<S> excludesAll(final Collection<T> desired, final SequenceOperations<T, S> ops) {
        return CachedValueBuilder.of((S s) -> {

                if (desired == null) {
                    return SimpleCacheResult.ofPassed()
                        .withCache(SequenceCaches.<T>nonFound());
                }

                final List<T> found = ops.findAllIn(s, desired);

                return SimpleCacheResult.of(found.isEmpty())
                    .withCache(SequenceCaches.found(found));
            })
            .name("excludesAll")
            .expected("All desired elements missing in subject")
            .actual(context ->
                context.passed() ?
                    "All desired elements missing in subject" :
                    "At least one desired element found in subject"
            )
            .hint(context ->
                {
                    if (desired == null) {
                        return "Desired elements is (null), which passes by default";
                    }
                    else {
                        if (context.passed()) {
                            return "All of the desired elements are missing in subject";
                        }
                        else {
                            final List<String> found = context.cache()
                                .value()
                                .stream()
                                .map(Formatter::stringify)
                                .collect(toList());

                            return "At least one desired element found in subject: " + found;
                        }
                    }
                }
            );
    }

    public static <T, S> ValueBuilder<S> excludesAny(final Collection<T> desired, final SequenceOperations<T, S> ops) {
        return CachedValueBuilder.of((S s) -> {

                if (desired == null) {
                    return SimpleCacheResult.ofPassed()
                        .withCache(SequenceCaches.<T>nonFound());
                }

                final List<T> found = ops.findAllIn(s, desired);

                return SimpleCacheResult.of(desired.size() < found.size())
                    .withCache(SequenceCaches.found(found));
            })
            .name("includesAll")
            .expected("At least one desired element missing in subject")
            .actual(context ->
                context.passed() ?
                    "At least one desired element missing in subject" :
                    "All desired elements found in subject"
            )
            .hint(context ->
                {
                    if (desired == null) {
                        return "Desired elements is (null), which passes by default";
                    }
                    else {
                        if (context.passed()) {
                            final List<String> found = context.cache()
                                .value()
                                .stream()
                                .map(Formatter::stringify)
                                .collect(toList());

                            return "The following desired elements were found: " + found;

                        }
                        else {
                            return "All desired elements found in subject";
                        }
                    }
                }
            );
    }
}
