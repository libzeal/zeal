package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;


class ArrayValueTest extends BaseValueTest<String[], ArrayValue<String>>
    implements SizedSequenceValueTest<String[], ArrayValue<String>>,
    InspectableSequenceValueTest<String[], ArrayValue<String>>,
    RepeatableSequenceValueTest<String[], ArrayValue<String>>,
    OrderedSequenceValueTest<String[], ArrayValue<String>> {

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

    // ---- includes (with the index of the desired element as the hint) ----

    @Test
    void givenIncluded_whenIncludes_thenHintHasIndex() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includes("b"))
            .result(true)
            .name("includes[b]")
            .expected("includes[b]")
            .actual("includes[b]")
            .hint("'b' found at index 1")
            .run();
    }

    @Test
    void givenExcluded_whenIncludes_thenHintSaysNotIncluded() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includes("c"))
            .result(false)
            .name("includes[c]")
            .expected("includes[c]")
            .actual("excludes[c]")
            .hint("Value does not include 'c'")
            .run();
    }

    // ---- excludes (with the index of the desired element as the hint) ----

    @Test
    void givenExcluded_whenExcludes_thenHintSaysNotIncluded() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.excludes("c"))
            .result(true)
            .name("excludes[c]")
            .expected("excludes[c]")
            .actual("excludes[c]")
            .hint("Value does not include 'c'")
            .run();
    }

    @Test
    void givenIncluded_whenExcludes_thenHintHasIndex() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.excludes("b"))
            .result(false)
            .name("excludes[b]")
            .expected("excludes[b]")
            .actual("includes[b]")
            .hint("'b' found at index 1")
            .run();
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
