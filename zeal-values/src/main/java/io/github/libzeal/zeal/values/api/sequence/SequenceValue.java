package io.github.libzeal.zeal.values.api.sequence;

import io.github.libzeal.zeal.values.api.BaseObjectValue;

import java.util.Collection;

/**
 * A value representing a sequence of elements.
 *
 * @param <T>
 *     The type of the elements in the sequence.
 * @param <S>
 *     The type of the sequence.
 * @param <V>
 *     The type of the value. This type is used to maintain the correct return
 *     type for fluent interfaces, allowing for method chaining.
 *
 * @author Justin Albano
 */
public interface SequenceValue<T, S, V extends BaseObjectValue<S, V>> {

    V isEmpty();
    V isNotEmpty();
    V hasLengthOf(int length);
    V doesNotHaveLengthOf(int length);
    V isLongerThan(int length);
    V isLongerThanOrEqualTo(int length);
    V isShorterThan(int length);
    V isShorterThanOrEqualTo(int length);
    V includes(T desired);
    V includesAll(Collection<T> desired);
    V includesAllOf(T... desired);
    V includesAny(Collection<T> desired);
    V includesAnyOf(T... desired);
    V excludes(T desired);
    V excludesAll(Collection<T> desired);
    V excludesAllOf(T... desired);
    V excludesAny(Collection<T> desired);
    V excludesAnyOf(T... desired);

}
