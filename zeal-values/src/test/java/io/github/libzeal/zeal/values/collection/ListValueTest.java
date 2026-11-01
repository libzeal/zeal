package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ListValueTest extends BaseValueTest<List<String>, ListValue<String>>
    implements OrderedSequenceValueTest<List<String>, ListValue<String>> {

    @Override
    protected ListValue<String> expression(final List<String> value) {
        return new ListValue<>(value);
    }

    @Override
    protected List<String> nonNullValueA() {
        return List.of("foo");
    }

    @Override
    protected List<String> nonNullValueB() {
        return List.of("bar");
    }

    @Override
    public List<String> subjectOf(final String... elements) {
        return List.of(elements);
    }

    // ---- duplicate elements ----

    @Test
    void givenAllDesiredPresentAndDuplicateSubjectElements_whenIncludesAll_thenTrue() {
        newTestCase()
            .subject(List.of("a", "a", "b"))
            .expression(value -> value.includesAll(List.of("a", "b")))
            .result(true)
            .name("includesAll")
            .expected("All desired elements in subject")
            .actual("All desired elements in subject")
            .hint("All desired elements found in subject")
            .run();
    }

    @Test
    void givenOneDesiredMissingAndDuplicateSubjectElements_whenIncludesAll_thenFalse() {
        newTestCase()
            .subject(List.of("a", "a", "b"))
            .expression(value -> value.includesAll(List.of("a", "c")))
            .result(false)
            .name("includesAll")
            .expected("All desired elements in subject")
            .actual("At least one desired element missing in subject")
            .hint("The following desired elements were not found: [c]")
            .run();
    }

    @Test
    void givenOneDesiredMissingAndDuplicateSubjectElements_whenExcludesAny_thenTrue() {
        newTestCase()
            .subject(List.of("a", "a", "b"))
            .expression(value -> value.excludesAny(List.of("a", "c")))
            .result(true)
            .name("excludesAny")
            .expected("At least one desired element missing in subject")
            .actual("At least one desired element missing in subject")
            .hint("The following desired elements were found: [a]")
            .run();
    }

    @Test
    void givenTwoOccurrences_whenIncludesExactlyTwice_thenTrue() {
        newTestCase()
            .subject(List.of("a", "a", "b"))
            .expression(value -> value.includesExactly("a", 2))
            .result(true)
            .name("includesExactly[desired=a, times=2]")
            .expected("found 'a' 2 times")
            .actual("found 'a' 2 times")
            .run();
    }

    // ---- null needles in lists that do not permit null elements ----

    @Test
    void givenListNotPermittingNull_whenIncludesNull_thenFalse() {
        newTestCase()
            .subject(List.of("a", "b"))
            .expression(value -> value.includes(null))
            .result(false)
            .name("includes[(null)]")
            .expected("includes[(null)]")
            .actual("excludes[(null)]")
            .hint("Value does not include '(null)'")
            .run();
    }

    @Test
    void givenListNotPermittingNull_whenExcludesNull_thenTrue() {
        newTestCase()
            .subject(List.of("a", "b"))
            .expression(value -> value.excludes(null))
            .result(true)
            .name("excludes[(null)]")
            .expected("excludes[(null)]")
            .actual("excludes[(null)]")
            .hint("Value does not include '(null)'")
            .run();
    }

    @Test
    void givenListNotPermittingNull_whenLastIndexOfNullIsMinusOne_thenTrue() {
        newTestCase()
            .subject(List.of("a", "b"))
            .expression(value -> value.lastIndexOfIs(null, -1))
            .result(true)
            .name("lastIndexOf[(null)] = -1")
            .expected("-1")
            .actual("-1")
            .run();
    }

    @Test
    void givenListNotPermittingNull_whenIncludesAllOfWithNull_thenFalse() {
        newTestCase()
            .subject(List.of("a", "b"))
            .expression(value -> value.includesAllOf("a", null))
            .result(false)
            .name("includesAll")
            .expected("All desired elements in subject")
            .actual("At least one desired element missing in subject")
            .hint("The following desired elements were not found: [(null)]")
            .run();
    }

    @Test
    void givenListThrowingForNonNullNeedle_whenIncludes_thenExceptionPropagated() {

        final ListValue<String> value = new ListValue<>(listThrowingOnSearch("a", "b")).includes("a");

        assertThrows(NullPointerException.class, value::evaluate);
    }

    @Test
    void givenListThrowingForNonNullNeedle_whenLastIndexOfIs_thenExceptionPropagated() {

        final ListValue<String> value = new ListValue<>(listThrowingOnSearch("a", "b")).lastIndexOfIs("a", 0);

        assertThrows(NullPointerException.class, value::evaluate);
    }

    // A list that fails when searched, which ensures that only a null needle is treated as not found
    private static List<String> listThrowingOnSearch(final String... elements) {

        final List<String> list = List.of(elements);

        return new AbstractList<>() {

            @Override
            public String get(final int index) {
                return list.get(index);
            }

            @Override
            public int size() {
                return list.size();
            }

            @Override
            public int indexOf(final Object o) {
                throw new NullPointerException("Searched");
            }

            @Override
            public int lastIndexOf(final Object o) {
                throw new NullPointerException("Searched");
            }
        };
    }

    // ---- collections of a subtype of the element type ----

    @Test
    void givenCollectionsOfSubtype_whenIncludesAndExcludes_thenAccepted() {

        final List<Integer> included = List.of(1, 2);
        final List<Integer> excluded = List.of(4, 5);
        final List<Integer> partiallyIncluded = List.of(1, 4);

        final ListValue<Number> value = new ListValue<Number>(List.of(1, 2, 3))
            .includesAll(included)
            .includesAny(partiallyIncluded)
            .excludesAll(excluded)
            .excludesAny(partiallyIncluded);

        assertTrue(value.evaluate().isTrue());
    }
}
