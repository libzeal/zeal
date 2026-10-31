package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

class SetValueTest extends BaseValueTest<Set<String>, SetValue<String>>
    implements SequenceValueTest<Set<String>, SetValue<String>> {

    @Override
    protected SetValue<String> expression(final Set<String> value) {
        return new SetValue<>(value);
    }

    @Override
    protected Set<String> nonNullValueA() {
        return Set.of("foo");
    }

    @Override
    protected Set<String> nonNullValueB() {
        return Set.of("bar");
    }

    @Override
    public Set<String> subjectOf(final String... elements) {
        // Keeps the order of the elements, so that messages listing elements are predictable
        return new LinkedHashSet<>(List.of(elements));
    }
}
