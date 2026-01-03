package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.BaseObjectValue;
import io.github.libzeal.zeal.values.api.CommonRationale;
import io.github.libzeal.zeal.values.api.cache.SequenceCaches;
import io.github.libzeal.zeal.values.api.cache.SimpleCacheResult;

import java.util.Objects;

import static io.github.libzeal.zeal.logic.util.Formatter.stringify;

public class ArrayValue<T> extends BaseObjectValue<T[], ArrayValue<T>> {

    public ArrayValue(final T[] subject) {
        super(subject, "Array value");
    }

    public ArrayValue<T> hasLength(final int length) {
        return append(
                expression(s -> s.length == length)
                        .name("hasLength[" + length + "]")
                        .expected("length = " + length)
                        .actual((s, passed) -> String.valueOf(s.length))
        );
    }

    public ArrayValue<T> isShorterThan(final int length) {
        return append(
                expression(s -> s.length < length)
                        .name("isShortThan[" + length + "]")
                        .expected("length < " + length)
                        .actual((s, passed) -> String.valueOf(s.length))
        );
    }

    public ArrayValue<T> isShorterThanOrEqualTo(final int length) {
        return append(
                expression(s -> s.length <= length)
                        .name("isShortThanOrEqualTo[" + length + "]")
                        .expected("length <= " + length)
                        .actual((s, passed) -> String.valueOf(s.length))
        );
    }

    public ArrayValue<T> isLongerThan(final int length) {
        return append(
                expression(s -> s.length > length)
                        .name("isLongerThan[" + length + "]")
                        .expected("length > " + length)
                        .actual((s, passed) -> String.valueOf(s.length))
        );
    }

    public ArrayValue<T> isLongerThanOrEqualTo(final int length) {
        return append(
                expression(s -> s.length >= length)
                        .name("isLongerThanOrEqualTo[" + length + "]")
                        .expected("length >= " + length)
                        .actual((s, passed) -> String.valueOf(s.length))
        );
    }

    public ArrayValue<T> includes(final T desired) {
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

    protected int findIndex(final T[] haystack, final T needle)  {

        int index = 0;

        for (final T element : haystack) {
            if (Objects.equals(element, needle)) {
                return index;
            }

            index++;
        }

        return -1;
    }

    private static <T> String needleInHaystackHint(final T desired, final int index) {
        return CommonRationale.needleInHaystackHint("Iterable", index, desired);
    }

    public ArrayValue<T> excludes(final T desired) {
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

    private static <E> boolean excludes(final E value, final E[] s) {

        for (final E element: s) {
            if (Objects.equals(element, value)) {
                return false;
            }
        }

        return true;
    }

    public ArrayValue<T> hasAtIndex(final T desired, final int index) {

        final String desiredName = stringify(desired);

        return append(
                cachableExpression(s -> {

                    final T found = findAtIndex(s, index);

                    return SimpleCacheResult.of(Objects.equals(desired, found))
                            .withCache(SequenceCaches.element(found));
                })
                        .name("hasAtIndex[desired=" + desiredName + ", index=" + index + "]")
                        .expected(desiredName)
                        .actual(context ->
                                context.cache().element().getOrElse("(null)", desiredName))
                        .hint(context ->
                                context.cache().element().getOrElse(
                                        "Index " + index + " is beyond array length of " + context.subject().length,
                                        "Index " + index + " is within bounds, but the desired element was not found there"
                                ))
        );
    }

    private static <T> T findAtIndex(final T[] array, final int index) {

        if (index > 0 && index < array.length) {
            return array[index];
        }
        else {
            return null;
        }
    }

    public ArrayValue<T> doesNotHaveAtIndex(final T desired, final int index) {

        final String desiredName = stringify(desired);

        return append(
                cachableExpression(s -> {

                    final T found = findAtIndex(s, index);

                    return SimpleCacheResult.of(!Objects.equals(desired, found))
                            .withCache(SequenceCaches.element(found));
                })
                        .name("doesNotHaveAtIndex[desired=" + desiredName + ", index=" + index + "]")
                        .expected("not[" + desiredName + "]")
                        .actual(context ->
                                context.cache().element().getOrElse("(null)", desiredName
                                ))
        );
    }

    public ArrayValue<T> startsWith(final T desired) {

        final String desiredName = stringify(desired);

        return append(
                cachableExpression(s -> {

                    final T found = findAtIndex(s, 0);

                    return SimpleCacheResult.of(Objects.equals(desired, found))
                            .withCache(SequenceCaches.element(found));
                })
                        .name("startsWith[" + desiredName + "]")
                        .expected(desiredName)
                        .actual(context ->
                                context.subject().length == 0 ? "(null)" : desiredName
                        )
                        .hint(context ->
                                context.subject().length == 0 ? "Array is empty" : "Array has at least one element, but the first element is not " + desiredName
                        )
        );
    }

    public ArrayValue<T> endsWith(final T desired) {

        final String desiredName = stringify(desired);

        return append(
                cachableExpression(s -> {

                    final T found = findAtIndex(s, s.length - 1);

                    return SimpleCacheResult.of(Objects.equals(desired, found))
                            .withCache(SequenceCaches.element(found));
                })
                        .name("endsWith[" + desiredName + "]")
                        .expected(desiredName)
                        .actual(context ->
                                context.subject().length == 0 ? "(null)" : desiredName
                        )
                        .hint(context ->
                                context.subject().length == 0 ? "Array is empty" : "Array has at least one element, but the last element is not " + desiredName
                        )
        );
    }
}
