package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.sequence.InspectableSequenceValue;
import io.github.libzeal.zeal.values.api.sequence.RepeatableSequenceValue;
import io.github.libzeal.zeal.values.api.sequence.SizedSequenceValue;

import java.util.Collection;

public interface CollectionSequenceValue<
        T,
        C extends Collection<T>,
        V extends CollectionSequenceValue<T, C, V>>
    extends SizedSequenceValue<T, V>,
        InspectableSequenceValue<T, V>,
        RepeatableSequenceValue<T, V> {
}
