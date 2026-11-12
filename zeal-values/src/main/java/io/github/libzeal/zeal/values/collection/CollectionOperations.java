package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.SequenceOperations;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

class CollectionOperations<T extends @Nullable Object, C extends Collection<T>>
        implements SequenceOperations<T, C> {

    @Override
    public int size(final C haystack) {
        return haystack.size();
    }

    @Override
    public boolean includes(final C haystack, final @Nullable T needle) {

        try {
            return haystack.contains(needle);
        }
        catch (final NullPointerException e) {
            // Some collections do not permit null elements and may throw when a
            // null needle is used. This section catches this exception and if
            // the needle is null (the cause of the NPE), the needle is
            // considered "not found."

            if (needle == null) {
                return false;
            }

            throw e;
        }
    }

    @Override
    public int occurrences(final C haystack, final @Nullable T needle) {
        return Collections.frequency(haystack, needle);
    }
}
