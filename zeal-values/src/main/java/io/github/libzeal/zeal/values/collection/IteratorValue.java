package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.logic.unary.PredicateExpression.Field;
import io.github.libzeal.zeal.values.sdk.BaseValue;

import java.util.Iterator;

public class IteratorValue<T> extends BaseValue<Iterator<T>, IteratorValue<T>> {

    private static final String HAS_NEXT_NAME = "hasNext";
    private static final String NOT_HAS_NEXT_NAME = "not[hasNext]";

    public IteratorValue(final Iterator<T> subject) {
        super(subject, "Iterator value");
    }

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
