package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceExpressions.InspectableSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceExpressions.RepeatableSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceExpressions.SizedSequenceOperations;

import java.util.Collection;

interface CollectionOperations<T, C extends Collection<T>> extends
        SizedSequenceOperations<C>,
        InspectableSequenceOperations<T, C>,
        RepeatableSequenceOperations<T, C> {
}
