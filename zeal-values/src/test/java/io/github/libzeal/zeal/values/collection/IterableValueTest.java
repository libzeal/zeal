package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.core.test.ValueTestCaseBuilder;
import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


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

    // ---- collection subjects (size, includes, and occurrences delegate to the collection) ----

    @Nested
    class GivenCollectionSubject implements SequenceValueTest<Iterable<String>, IterableValue<String>> {

        @Override
        public ValueTestCaseBuilder<Iterable<String>, IterableValue<String>> newTestCase() {
            return ValueTestCaseBuilder.forValue(IterableValue::new);
        }

        @Override
        public Iterable<String> subjectOf(final String... elements) {
            return Arrays.asList(elements);
        }

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
        void givenNonIterableCollection_whenHasLengthOf_thenSizeUsedWithoutIterating() {
            newTestCase()
                .subject(nonIterableCollectionOf("a", "b"))
                .expression(value -> value.hasLengthOf(2))
                .result(true)
                .name("hasLengthOf[2]")
                .expected("length = 2")
                .actual("length = 2")
                .run();
        }

        @Test
        void givenNonIterableCollection_whenIncludes_thenContainsUsedWithoutIterating() {
            newTestCase()
                .subject(nonIterableCollectionOf("a", "b"))
                .expression(value -> value.includes("b"))
                .result(true)
                .name("includes[b]")
                .expected("includes[b]")
                .actual("includes[b]")
                .run();
        }

        @Test
        void givenCollectionNotPermittingNull_whenIncludesNull_thenFalse() {
            newTestCase()
                .subject(List.of("a", "b"))
                .expression(value -> value.includes(null))
                .result(false)
                .name("includes[(null)]")
                .expected("includes[(null)]")
                .actual("excludes[(null)]")
                .run();
        }

        @Test
        void givenCollectionThrowingForNonNullNeedle_whenIncludes_thenExceptionPropagated() {

            final IterableValue<String> value = new IterableValue<>(collectionThrowingOnSearch("a", "b")).includes("a");

            assertThrows(NullPointerException.class, value::evaluate);
        }

        // A collection that fails when searched, which ensures that only a null needle is treated as not found
        private static Collection<String> collectionThrowingOnSearch(final String... elements) {

            final List<String> list = Arrays.asList(elements);

            return new AbstractCollection<>() {

                @Override
                public Iterator<String> iterator() {
                    return list.iterator();
                }

                @Override
                public int size() {
                    return list.size();
                }

                @Override
                public boolean contains(final Object o) {
                    throw new NullPointerException("Searched");
                }
            };
        }

        // A collection that fails if iterated, which ensures that the collection's own size and contains are used
        private static Collection<String> nonIterableCollectionOf(final String... elements) {

            final List<String> list = Arrays.asList(elements);

            return new AbstractCollection<>() {

                @Override
                public Iterator<String> iterator() {
                    throw new UnsupportedOperationException("Collection should not be iterated");
                }

                @Override
                public int size() {
                    return list.size();
                }

                @Override
                public boolean contains(final Object o) {
                    return list.contains(o);
                }

                @Override
                public String toString() {
                    return list.toString();
                }
            };
        }
    }
}
