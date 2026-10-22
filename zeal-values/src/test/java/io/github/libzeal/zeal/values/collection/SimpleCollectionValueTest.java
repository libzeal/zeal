package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;

import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

class SimpleCollectionValueTest extends BaseValueTest<Collection<String>, SimpleCollectionValue<String>>
    implements SizedSequenceValueTest<Collection<String>, SimpleCollectionValue<String>>,
    InspectableSequenceValueTest<Collection<String>, SimpleCollectionValue<String>>,
    RepeatableSequenceValueTest<Collection<String>, SimpleCollectionValue<String>> {

    // ArrayDeque uses identity for equals and hashCode, so the same instances must be returned each time
    private static final Collection<String> VALUE_A = new ArrayDeque<>(List.of("foo"));
    private static final Collection<String> VALUE_B = new ArrayDeque<>(List.of("bar"));

    @Override
    protected SimpleCollectionValue<String> expression(final Collection<String> value) {
        return new SimpleCollectionValue<>(value);
    }

    @Override
    protected Collection<String> nonNullValueA() {
        return VALUE_A;
    }

    @Override
    protected Collection<String> nonNullValueB() {
        return VALUE_B;
    }

    @Override
    public Collection<String> subjectOf(final String... elements) {
        // A collection that is neither a list nor a set
        return new ArrayDeque<>(List.of(elements));
    }

    // ---- duplicate elements ----

    @Test
    void givenTwoOccurrences_whenIncludesExactlyTwice_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "a", "b"))
            .expression(value -> value.includesExactly("a", 2))
            .result(TRUE)
            .name("includesExactly[desired=a, times=2]")
            .expected("found 'a' 2 times")
            .actual("found 'a' 2 times")
            .run();
    }
}
