package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.BaseSequenceValue;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

/**
 * A value used to evaluate {@link Collection}s.
 *
 * @param <T>
 *         The type of the elements in the {@link Collection}.
 *
 * @author Justin Albano
 */
public class SimpleCollectionValue<T extends @Nullable Object> extends BaseSequenceValue<Collection<T>, T, SimpleCollectionValue<T>> {

    /**
     * Creates a collection value from the supplied subject.
     *
     * @param subject
     *         The desired subject.
     */
    public SimpleCollectionValue(final @Nullable Collection<T> subject) {
        super(subject, "Collection value", new CollectionOperations<>());
    }
}
