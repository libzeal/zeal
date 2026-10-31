package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.BaseSequenceValue;

import java.util.Set;

// TODO Optimize the includes* counting methods to short circuit, since Sets have unique values
public class SetValue<T> extends BaseSequenceValue<Set<T>, T, SetValue<T>> {

    public SetValue(final Set<T> subject) {
        super(subject, "Set value", new CollectionOperations<>());
    }
}
