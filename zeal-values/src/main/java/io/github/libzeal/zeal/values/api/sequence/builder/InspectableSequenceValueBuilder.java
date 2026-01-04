package io.github.libzeal.zeal.values.api.sequence.builder;

import io.github.libzeal.zeal.logic.util.Formatter;
import io.github.libzeal.zeal.values.api.SimpleValueBuilder;
import io.github.libzeal.zeal.values.api.StandardRationales;
import io.github.libzeal.zeal.values.api.ValueBuilder;
import io.github.libzeal.zeal.values.api.cache.CachedValueBuilder;
import io.github.libzeal.zeal.values.api.sequence.SequenceCaches;
import io.github.libzeal.zeal.values.api.cache.SimpleCacheResult;
import io.github.libzeal.zeal.values.config.Configuration;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class InspectableSequenceValueBuilder {

    public interface InspectableSequenceOperations<T, S> {

        List<T> findAllIn(S haystack, Collection<T> needles);

        boolean includes(S haystack, T needle);
    }

    public static <T, S> ValueBuilder<S> includes(final T desired, final InspectableSequenceOperations<T, S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.includes(s, desired))
            .name(StandardRationales.includes(desired))
            .expected(StandardRationales.includes(desired))
            .actual(context -> context.passed() ? StandardRationales.includes(desired) : StandardRationales.excludes(desired));
    }

    public static <T, S> ValueBuilder<S> includesAll(final Collection<T> desired,
                                                     final InspectableSequenceOperations<T, S> ops) {

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

    public static <T, S> ValueBuilder<S> includesAny(final Collection<T> desired, final InspectableSequenceOperations<T, S> ops) {

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

    public static <T, S> ValueBuilder<S> excludes(final T desired, final InspectableSequenceOperations<T, S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> !ops.includes(s, desired))
            .name(StandardRationales.excludes(desired))
            .expected(StandardRationales.excludes(desired))
            .actual(context -> context.passed() ? StandardRationales.excludes(desired) : StandardRationales.includes(desired));
    }

    public static <T, S> ValueBuilder<S> excludesAll(final Collection<T> desired, final InspectableSequenceOperations<T, S> ops) {
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

    public static <T, S> ValueBuilder<S> excludesAny(final Collection<T> desired, final InspectableSequenceOperations<T, S> ops) {
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
