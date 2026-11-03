package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.logic.unary.ObservableUnaryExpression.Field;
import io.github.libzeal.zeal.values.sdk.BaseValue;
import org.jspecify.annotations.Nullable;

import java.util.Iterator;

/**
 * A value used to evaluate {@link Iterator}s.
 *
 * @param <T>
 *         The type of the elements in the {@link Iterator}.
 *
 * @author Justin Albano
 */
public class IteratorValue<T extends @Nullable Object> extends BaseValue<Iterator<T>, IteratorValue<T>> {

    private static final String HAS_NEXT_NAME = "hasNext";
    private static final String NOT_HAS_NEXT_NAME = "not[hasNext]";

    /**
     * Creates a new iterator value.
     *
     * @param subject
     *         The desired subject.
     */
    public IteratorValue(final @Nullable Iterator<T> subject) {
        super(subject, "Iterator value");
    }

    /**
     * Checks if the iterator has a next element (equivalent to
     * {@link Iterator#hasNext()}.
     *
     * @return This value (fluent interface).
     */
    public IteratorValue<T> hasNext() {
        return append(
                observing(Iterator::hasNext)
                        .passesIf(hasNext -> hasNext)
                        .name(HAS_NEXT_NAME)
                        .expected(HAS_NEXT_NAME)
                        .actual(hasNextActual())
                        .build()
        );
    }

    private static <T> Field<Iterator<T>, Boolean> hasNextActual() {
        return context -> Boolean.TRUE.equals(context.observed()) ? HAS_NEXT_NAME : NOT_HAS_NEXT_NAME;
    }

    /**
     * Checks if the iterator does not have a next element (equivalent to not
     * {@link Iterator#hasNext()}).
     *
     * @return This value (fluent interface).
     */
    public IteratorValue<T> doesNotHaveNext() {
        return append(
                observing(Iterator::hasNext)
                        .passesIf(hasNext -> !hasNext)
                        .name(NOT_HAS_NEXT_NAME)
                        .expected(NOT_HAS_NEXT_NAME)
                        .actual(hasNextActual())
                        .build()
        );
    }
}
