package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceValue;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceValue;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceValue;

import java.util.Collection;

public interface CollectionValue<
        T,
        C extends Collection<T>,
        V extends CollectionValue<T, C, V>>
    extends SizedSequenceValue<T, V>,
        InspectableSequenceValue<T, V>,
        RepeatableSequenceValue<T, V> {
}
