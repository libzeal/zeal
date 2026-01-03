package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableField;
import io.github.libzeal.zeal.values.api.BaseObjectValue;

import java.util.Iterator;

public class IteratorValue<T> extends BaseObjectValue<Iterator<T>, IteratorValue<T>> {

    public IteratorValue(final Iterator<T> subject) {
        super(subject, "Iterator value");
    }

    public IteratorValue<T> hasNext() {
        return append(
                expression(Iterator::hasNext)
                        .name(hasNextName())
                        .expected(hasNextName())
                        .actual(hasNextActual())
        );
    }

    private static String hasNextName() {
        return "hasNext";
    }

    private static <T> ComputableField<Iterator<T>> hasNextActual() {
        return context -> context.ifPassedOrElse(hasNextName(), doesNotHaveNextName());
    }

    private static String doesNotHaveNextName() {
        return "not[hasNext]";
    }

    public IteratorValue<T> doesNotHaveNext() {
        return append(
                expression(s -> !s.hasNext())
                        .name(doesNotHaveNextName())
                        .expected(doesNotHaveNextName())
                        .actual(hasNextActual())
        );
    }
}
