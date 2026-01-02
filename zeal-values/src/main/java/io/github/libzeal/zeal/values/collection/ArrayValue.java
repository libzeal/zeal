package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.BaseObjectValue;
import io.github.libzeal.zeal.values.api.CommonRationale;

import java.util.Objects;

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

    public ArrayValue<T> includes(final T value) {
        return append(
                expression(s -> includes(value, s))
                        .name(CommonRationale.includes(value))
                        .expected(CommonRationale.includes(value))
                        .actual((s, passed) -> passed ? CommonRationale.includes(value) : CommonRationale.excludes(value))
                        .hint((s, passed) -> needleInHaystackHint(s, value))
        );
    }

    private static <E> boolean includes(final E value, final E[] s) {

        for (final E element: s) {
            if (Objects.equals(element, value)) {
                return true;
            }
        }

        return false;
    }

    static <E> String needleInHaystackHint(final E[] haystack, final E needle) {

        int index = 0;

        for (final E element: haystack) {

            if (Objects.equals(element, needle)) {
                break;
            }

            index++;
        }

        return CommonRationale.needleInHaystackHint("Array", index, needle);
    }

    public ArrayValue<T> excludes(final T value) {
        return append(
                expression(s -> excludes(value, s))
                        .name(CommonRationale.excludes(value))
                        .expected(CommonRationale.excludes(value))
                        .actual((s, passed) -> passed ? CommonRationale.excludes(value) : CommonRationale.includes(value))
                        .hint((s, passed) -> needleInHaystackHint(s, value))
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

//    public ArrayValue<T> hasAtIndex(final T value, final int index) {
//
//    }
//
//    public ArrayValue<T> doesNotHaveAtIndex(final T value, final int index) {
//
//    }
//
//    public ArrayValue<T> startsWith(final T value) {
//
//    }
//
//    public ArrayValue<T> endsWith(final T value) {
//
//    }
}
