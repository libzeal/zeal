package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    // ---- occurrences (using the set's own membership) ----

    @Test
    void givenSortedSetWithCaseInsensitiveOrder_whenIncludesOnceWithDifferentCase_thenTrue() {
        newTestCase()
            .subject(caseInsensitiveSetOf("a", "b"))
            .expression(value -> value.includesOnce("A"))
            .result(true)
            .name("includesExactly[desired=A, times=1]")
            .expected("found 'A' 1 time")
            .actual("found 'A' 1 time")
            .run();
    }

    @Test
    void givenSortedSetWithCaseInsensitiveOrder_whenIncludesWithDifferentCase_thenTrue() {
        newTestCase()
            .subject(caseInsensitiveSetOf("a", "b"))
            .expression(value -> value.includes("A"))
            .result(true)
            .name("includes[A]")
            .expected("includes[A]")
            .actual("includes[A]")
            .run();
    }

    @Test
    void givenSetNotPermittingNull_whenIncludesNullExactlyZeroTimes_thenTrue() {
        newTestCase()
            .subject(Set.of("a", "b"))
            .expression(value -> value.includesExactly(null, 0))
            .result(true)
            .name("includesExactly[desired=(null), times=0]")
            .expected("found '(null)' 0 times")
            .actual("found '(null)' 0 times")
            .run();
    }

    @Test
    void givenSetWithNull_whenIncludesNullOnce_thenTrue() {
        newTestCase()
            .subject(new HashSet<>(Arrays.asList("a", null)))
            .expression(value -> value.includesOnce(null))
            .result(true)
            .name("includesExactly[desired=(null), times=1]")
            .expected("found '(null)' 1 time")
            .actual("found '(null)' 1 time")
            .run();
    }

    private static Set<String> caseInsensitiveSetOf(final String... elements) {

        final Set<String> set = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        set.addAll(List.of(elements));

        return set;
    }

    // ---- null needles in sets that do not permit null elements ----

    @Test
    void givenSetNotPermittingNull_whenIncludesNull_thenFalse() {
        newTestCase()
            .subject(Set.of("a", "b"))
            .expression(value -> value.includes(null))
            .result(false)
            .name("includes[(null)]")
            .expected("includes[(null)]")
            .actual("excludes[(null)]")
            .run();
    }

    @Test
    void givenSetNotPermittingNull_whenExcludesNull_thenTrue() {
        newTestCase()
            .subject(Set.of("a", "b"))
            .expression(value -> value.excludes(null))
            .result(true)
            .name("excludes[(null)]")
            .expected("excludes[(null)]")
            .actual("excludes[(null)]")
            .run();
    }

    @Test
    void givenSortedSetNotPermittingNull_whenIncludesNull_thenFalse() {
        newTestCase()
            .subject(new TreeSet<>(List.of("a", "b")))
            .expression(value -> value.includes(null))
            .result(false)
            .name("includes[(null)]")
            .expected("includes[(null)]")
            .actual("excludes[(null)]")
            .run();
    }

    @Test
    void givenSetThrowingForNonNullNeedle_whenIncludes_thenExceptionPropagated() {

        final SetValue<String> value = new SetValue<>(setThrowingOnSearch("a", "b")).includes("a");

        assertThrows(NullPointerException.class, value::evaluate);
    }

    // A set that fails when searched, which ensures that only a null needle is treated as not found
    private static Set<String> setThrowingOnSearch(final String... elements) {

        final Set<String> set = new LinkedHashSet<>(List.of(elements));

        return new AbstractSet<>() {

            @Override
            public Iterator<String> iterator() {
                return set.iterator();
            }

            @Override
            public int size() {
                return set.size();
            }

            @Override
            public boolean contains(final Object o) {
                throw new NullPointerException("Searched");
            }
        };
    }
}
