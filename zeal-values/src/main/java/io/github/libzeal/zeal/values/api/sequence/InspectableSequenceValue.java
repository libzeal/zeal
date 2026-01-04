package io.github.libzeal.zeal.values.api.sequence;

import io.github.libzeal.zeal.values.api.BaseObjectValue;

import java.util.Collection;

public interface InspectableSequenceValue<T, S, V extends BaseObjectValue<S, V>> {

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
