package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceExpressions.InspectableSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceExpressions.RepeatableSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceExpressions.SizedSequenceOperations;

public interface SequenceOperations<T, S> extends
        SizedSequenceOperations<S>,
        InspectableSequenceOperations<T, S>,
        RepeatableSequenceOperations<T, S> {
}
