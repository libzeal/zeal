package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

import java.util.List;


class IterableValueTest extends BaseValueTest<Iterable<String>, IterableValue<String>>
    implements SequenceValueTest<Iterable<String>, IterableValue<String>> {

    // Plain iterables use identity for equals, hashCode, and toString, so the same instances must be returned each time
    private static final Iterable<String> VALUE_A = iterableOf("foo");
    private static final Iterable<String> VALUE_B = iterableOf("bar");

    @Override
    protected IterableValue<String> expression(final Iterable<String> value) {
        return new IterableValue<>(value);
    }

    @Override
    protected Iterable<String> nonNullValueA() {
        return VALUE_A;
    }

    @Override
    protected Iterable<String> nonNullValueB() {
        return VALUE_B;
    }

    @Override
    public Iterable<String> subjectOf(final String... elements) {
        return iterableOf(elements);
    }

    private static Iterable<String> iterableOf(final String... elements) {
        final List<String> list = List.of(elements);
        return list::iterator;
    }

    // ---- duplicate elements ----

    @Test
    void givenTwoOccurrences_whenIncludesExactlyTwice_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "a", "b"))
            .expression(value -> value.includesExactly("a", 2))
            .result(true)
            .name("includesExactly[desired=a, times=2]")
            .expected("found 'a' 2 times")
            .actual("found 'a' 2 times")
            .run();
    }
}
