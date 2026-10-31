package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;


class ArrayValueTest extends BaseValueTest<String[], ArrayValue<String>>
    implements OrderedSequenceValueTest<String[], ArrayValue<String>> {

    // Arrays use identity for equals, hashCode, and toString, so the same instances must be returned each time
    private static final String[] VALUE_A = {"foo"};
    private static final String[] VALUE_B = {"bar"};

    @Override
    protected ArrayValue<String> expression(final String[] value) {
        return new ArrayValue<>(value);
    }

    @Override
    protected String[] nonNullValueA() {
        return VALUE_A;
    }

    @Override
    protected String[] nonNullValueB() {
        return VALUE_B;
    }

    @Override
    public String[] subjectOf(final String... elements) {
        return elements;
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

    @Test
    void givenOneDesiredMissingAndDuplicateSubjectElements_whenIncludesAll_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "a", "b"))
            .expression(value -> value.includesAllOf("a", "c"))
            .result(false)
            .name("includesAll")
            .expected("All desired elements in subject")
            .actual("At least one desired element missing in subject")
            .hint("The following desired elements were not found: [c]")
            .run();
    }
}
