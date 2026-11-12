package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.BaseSequenceValue;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;

/**
 * A value used to evaluate {@link Set}s.
 *
 * @param <T>
 *         The type of the elements in the {@link Set}.
 *
 * @author Justin Albano
 */
public final class SetValue<T extends @Nullable Object> extends BaseSequenceValue<Set<T>, T, SetValue<T>> {

    /**
     * Creates a set value from the supplied subject.
     *
     * @param subject
     *         The desired subject.
     */
    public SetValue(final @Nullable Set<T> subject) {
        super(subject, "Set value", new SetOperations<>());
    }

    /**
     * Operations specific for {@link Set}.
     * <p>
     * This set of operations includes a shortcut for the
     * {@link CollectionOperations#occurrences(Collection, Object)} method that
     * assumes a {@link Set} only contains zero or one occurrences of an
     * element. This override also leverage the membership logic of the set
     * (such as a {@link Comparator} for {@link SortedSet}) rather than imposing
     * membership logic.
     *
     * @param <T>
     *         The type of the elements of the set.
     */
    private static final class SetOperations<T extends @Nullable Object>
            extends CollectionOperations<T, Set<T>> {

        @Override
        public int occurrences(final Set<T> haystack, final @Nullable T needle) {
            return includes(haystack, needle) ? 1 : 0;
        }
    }
}
