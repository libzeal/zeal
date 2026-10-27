package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.core.ObjectValue;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceValue;
import org.junit.jupiter.api.Test;


interface RepeatableSequenceValueTest<S, E extends ObjectValue<S, E> & RepeatableSequenceValue<String, E>>
    extends SequenceValueContract<S, E> {

    // ---- includesExactly ----

    @Test
    default void givenOneOccurrence_whenIncludesExactly_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesExactly("a", 1))
            .result(true)
            .name("includesExactly[desired=a, times=1]")
            .expected("found 'a' 1 time")
            .actual("found 'a' 1 time")
            .hint("Desired value was found, but 1 time")
            .run();
    }

    @Test
    default void givenNoOccurrences_whenIncludesExactly_thenFalse() {
        newTestCase()
            .subject(subjectOf("b"))
            .expression(value -> value.includesExactly("a", 1))
            .result(false)
            .name("includesExactly[desired=a, times=1]")
            .expected("found 'a' 1 time")
            .actual("found 'a' 0 times")
            .hint("Desired value not found in subject")
            .run();
    }

    @Test
    default void givenOneOccurrence_whenIncludesOnce_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesOnce("a"))
            .result(true)
            .name("includesExactly[desired=a, times=1]")
            .expected("found 'a' 1 time")
            .actual("found 'a' 1 time")
            .run();
    }

    // ---- includesMoreThan ----

    @Test
    default void givenOneOccurrence_whenIncludesMoreThanZero_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesMoreThan("a", 0))
            .result(true)
            .name("includesMoreThan[desired=a, times=0]")
            .expected("found 'a' more than 0 times")
            .actual("found 'a' 1 time")
            .run();
    }

    @Test
    default void givenNoOccurrences_whenIncludesMoreThanZero_thenFalse() {
        newTestCase()
            .subject(subjectOf("b"))
            .expression(value -> value.includesMoreThan("a", 0))
            .result(false)
            .name("includesMoreThan[desired=a, times=0]")
            .expected("found 'a' more than 0 times")
            .actual("found 'a' 0 times")
            .hint("Desired value not found in subject")
            .run();
    }

    @Test
    default void givenOneOccurrence_whenIncludesMoreThanOne_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesMoreThan("a", 1))
            .result(false)
            .name("includesMoreThan[desired=a, times=1]")
            .expected("found 'a' more than 1 time")
            .actual("found 'a' 1 time")
            .hint("Desired value was found, but 1 time")
            .run();
    }

    // ---- includesMoreThanOrEqualTo ----

    @Test
    default void givenOneOccurrence_whenIncludesMoreThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesMoreThanOrEqualTo("a", 1))
            .result(true)
            .name("includesMoreThanOrEqualTo[desired=a, times=1]")
            .expected("found 'a' more than or equal to 1 time")
            .actual("found 'a' 1 time")
            .run();
    }

    @Test
    default void givenNoOccurrences_whenIncludesMoreThanOrEqualTo_thenFalse() {
        newTestCase()
            .subject(subjectOf("b"))
            .expression(value -> value.includesMoreThanOrEqualTo("a", 1))
            .result(false)
            .name("includesMoreThanOrEqualTo[desired=a, times=1]")
            .expected("found 'a' more than or equal to 1 time")
            .actual("found 'a' 0 times")
            .run();
    }

    // ---- includesLessThan ----

    @Test
    default void givenNoOccurrences_whenIncludesLessThan_thenTrue() {
        newTestCase()
            .subject(subjectOf("b"))
            .expression(value -> value.includesLessThan("a", 1))
            .result(true)
            .name("includesLessThan[desired=a, times=1]")
            .expected("found 'a' less than 1 time")
            .actual("found 'a' 0 times")
            .run();
    }

    @Test
    default void givenOneOccurrence_whenIncludesLessThan_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesLessThan("a", 1))
            .result(false)
            .name("includesLessThan[desired=a, times=1]")
            .expected("found 'a' less than 1 time")
            .actual("found 'a' 1 time")
            .run();
    }

    // ---- includesLessThanOrEqualTo ----

    @Test
    default void givenOneOccurrence_whenIncludesLessThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesLessThanOrEqualTo("a", 1))
            .result(true)
            .name("includesLessThanOrEqualTo[desired=a, times=1]")
            .expected("found 'a' less than or equal to 1 time")
            .actual("found 'a' 1 time")
            .run();
    }

    @Test
    default void givenOneOccurrence_whenIncludesLessThanOrEqualToZero_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesLessThanOrEqualTo("a", 0))
            .result(false)
            .name("includesLessThanOrEqualTo[desired=a, times=0]")
            .expected("found 'a' less than or equal to 0 times")
            .actual("found 'a' 1 time")
            .run();
    }
}
