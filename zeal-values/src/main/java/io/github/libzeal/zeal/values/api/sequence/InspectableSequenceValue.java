package io.github.libzeal.zeal.values.api.sequence;

import java.util.Collection;

public interface InspectableSequenceValue<T, V extends InspectableSequenceValue<T, V>> {

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
