package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.BaseObjectValue;
import io.github.libzeal.zeal.values.api.CommonRationale;
import io.github.libzeal.zeal.values.api.cache.CachedComputableField.EvaluationContext;
import io.github.libzeal.zeal.values.api.cache.SequenceCaches;
import io.github.libzeal.zeal.values.api.cache.SequenceCaches.SizeCache;
import io.github.libzeal.zeal.values.api.cache.SimpleCacheResult;

import java.util.Collection;
import java.util.Objects;

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
                        .expected("size > " + 0)
                        .actual((s, passed) -> passed ? "isNotEmpty" : "isEmpty")
        );
    }

    // Synonym for isNotEmpty
    public E isPopulated() {
        return isNotEmpty();
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
                        .hint(context -> BaseIterableValue.needleInHaystackHint(desired, context.cache().index()))
        );
    }

    private static <T> String needleInHaystackHint(final T desired, final int index) {
        return CommonRationale.needleInHaystackHint("Iterable", index, desired);
    }

    // Synonym for includes
    public E contains(final T desired) {
        return includes(desired);
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
                        .hint(context -> BaseIterableValue.needleInHaystackHint(desired, context.cache().index()))
        );
    }

    // Synonym for excludes
    public E doesNotContain(final T desired) {
        return excludes(desired);
    }

    protected long findSize(final I subject) {

        if (subject instanceof Collection) {
            return ((Collection<T>) subject).size();
        }
        else {

            long count = 0;

            for (final T e : subject) {
                count++;
            }

            return count;
        }
    }

    protected int findIndex(final I haystack, final T needle)  {

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
}
