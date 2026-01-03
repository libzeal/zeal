package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.logic.util.Formatter;
import io.github.libzeal.zeal.values.api.BaseObjectValue;
import io.github.libzeal.zeal.values.api.CommonRationale;
import io.github.libzeal.zeal.values.api.cache.CachedComputableField.EvaluationContext;
import io.github.libzeal.zeal.values.api.cache.SequenceCaches;
import io.github.libzeal.zeal.values.api.cache.SequenceCaches.SizeCache;
import io.github.libzeal.zeal.values.api.cache.SimpleCacheResult;
import io.github.libzeal.zeal.values.config.Configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public abstract class BaseIterableValue<T, I extends Iterable<T>, E extends BaseIterableValue<T, I, E>>
    extends BaseObjectValue<I, E> {

    protected BaseIterableValue(final I subject, final String name) {
        super(subject, name);
    }

    public E hasSize(final long expected) {
        return append(
            cachableExpression(s -> {

                final long size = findSize(s);

                return SimpleCacheResult.of(size == expected)
                    .withCache(SequenceCaches.size(size));
            })
                .name("size[" + expected + "]")
                .expected("size = " + expected)
                .actual(this::actualSize)
        );
    }

    private String actualSize(final EvaluationContext<I, SizeCache> context) {
        return String.valueOf(context.cache().size());
    }

    public E doesNotHaveSize(final long expected) {
        return append(
            cachableExpression(s -> {

                final long size = findSize(s);

                return SimpleCacheResult.of(size != expected)
                    .withCache(SequenceCaches.size(size));
            })
                .name("not[size[" + expected + "]]")
                .expected("size != " + expected)
                .actual(this::actualSize)
        );
    }

    public E isSmallerThan(final long expected) {
        return append(
            cachableExpression(s -> {

                final long size = findSize(s);

                return SimpleCacheResult.of(size < expected)
                    .withCache(SequenceCaches.size(size));
            })
                .name("isSmallerThan[" + expected + "]")
                .expected("size < " + expected)
                .actual(this::actualSize)
        );
    }

    public E isSmallerThanOrEqualTo(final long expected) {
        return append(
            cachableExpression(s -> {

                final long size = findSize(s);

                return SimpleCacheResult.of(size <= expected)
                    .withCache(SequenceCaches.size(size));
            })
                .name("isSmallerThanOrEqualTo[" + expected + "]")
                .expected("size <= " + expected)
                .actual(this::actualSize)
        );
    }

    public E isLargerThan(final long expected) {
        return append(
            cachableExpression(s -> {

                final long size = findSize(s);

                return SimpleCacheResult.of(size > expected)
                    .withCache(SequenceCaches.size(size));
            })
                .name("isLargerThan[" + expected + "]")
                .expected("size > " + expected)
                .actual(this::actualSize)
        );
    }

    public E isLargerThanOrEqualTo(final long expected) {
        return append(
            cachableExpression(s -> {

                final long size = findSize(s);

                return SimpleCacheResult.of(size >= expected)
                    .withCache(SequenceCaches.size(size));
            })
                .name("isLargerThanOrEqualTo[" + expected + "]")
                .expected("size >= " + expected)
                .actual(this::actualSize)
        );
    }

    public E isEmpty() {
        return append(
            expression(this::findIsEmpty)
                .name("isEmpty")
                .expected("isEmpty")
                .actual((s, passed) -> passed ? "isEmpty" : "isNotEmpty")
        );
    }

    public E isNotEmpty() {
        return append(
            expression(s -> !findIsEmpty(s))
                .name("isNotEmpty")
                .expected("size > 0")
                .actual((s, passed) -> passed ? "isNotEmpty" : "isEmpty")
        );
    }

    public E includes(final T desired) {
        return append(
            cachableExpression(s -> {

                final int index = findIndex(s, desired);

                return SimpleCacheResult.of(index > -1)
                    .withCache(SequenceCaches.index(index));
            })
                .name(CommonRationale.includes(desired))
                .expected(CommonRationale.includes(desired))
                .actual(context -> context.passed() ? CommonRationale.includes(desired) : CommonRationale.excludes(desired))
                .hint(context -> needleInHaystackHint(desired, context.cache().index()))
        );
    }

    private static <T> String needleInHaystackHint(final T desired, final int index) {
        return CommonRationale.needleInHaystackHint("Iterable", index, desired);
    }

    public E excludes(final T desired) {
        return append(
            cachableExpression(s -> {

                final int index = findIndex(s, desired);

                return SimpleCacheResult.of(index == -1)
                    .withCache(SequenceCaches.index(index));
            })
                .name(CommonRationale.excludes(desired))
                .expected(CommonRationale.excludes(desired))
                .actual(context -> context.passed() ? CommonRationale.excludes(desired) : CommonRationale.includes(desired))
                .hint(context -> needleInHaystackHint(desired, context.cache().index()))
        );
    }

    public E includesAll(final Collection<T> desired) {
        return append(
            cachableExpression(s -> {

                if (desired == null) {
                    return SimpleCacheResult.ofPassed()
                        .withCache(SequenceCaches.<T>nonFound());
                }

                final List<T> found = findContainsAll(s, desired);

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
                .hint(context -> {
                        if (desired == null) {
                            return "Desired elements is (null), which passes by default";
                        }
                        else {
                            if (context.passed()) {
                                return "All desired elements found in subject";
                            }
                            else {
                                final List<String> missing = computeMissing(desired, context.cache().elements());

                                return "The following desired elements were not found: " + missing;
                            }
                        }
                    }
                )
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

    @SafeVarargs
    public final E includesAllOf(final T... desired) {
        return includesAll(Arrays.asList(desired));
    }

    public E includesAny(final Collection<T> desired) {
        return append(
            expression(s -> desired == null || findContainsAny(s, desired))
                .name("includesAny")
                .expected("All desired elements in subject")
                .actual((s, passed) ->
                    passed ?
                        "Any desired element in subject" :
                        "All desired elements missing in subject"
                )
                .hint((s, passed) -> {
                        if (desired == null) {
                            return "Desired elements is (null), which passes by default";
                        }
                        else {
                            if (passed) {
                                return "Any desired element found in subject";
                            }
                            else {
                                return "All desired elements missing in subject";
                            }
                        }
                    }
                )
        );
    }

    @SafeVarargs
    public final E includesAnyOf(final T... desired) {
        return includesAny(Arrays.asList(desired));
    }

//    public E excludesAll(final Collection<T> desired) {
//
//    }
//
//    public E excludesAllOf(final T... desired) {
//
//    }
//
//    public E excludesAny(final Collection<T> desired) {
//
//    }
//
//    public E excludesAnyOf(final T... desired) {
//
//    }

    @SuppressWarnings("unchecked")
    protected long findSize(final I subject) {

        long count = 0;

        for (final T e : subject) {
            count++;
        }

        return count;
    }

    protected int findIndex(final I haystack, final T needle) {

        int index = 0;

        for (final T element : haystack) {

            if (Objects.equals(element, needle)) {
                return index;
            }

            index++;
        }

        return -1;
    }

    protected boolean findIsEmpty(final I subject) {
        return !subject.iterator().hasNext();
    }

    protected final List<T> findContainsAll(final I haystack, final Collection<T> needles) {
        return needles.stream()
            .filter(findContains(haystack))
            .collect(toList());
    }

    protected Predicate<T> findContains(final I haystack) {
        return needle -> findIndex(haystack, needle) != -1;
    }

    protected final boolean findContainsAny(final I haystack, final Collection<T> needles) {
        return needles.stream()
            .anyMatch(findContains(haystack));
    }
}
