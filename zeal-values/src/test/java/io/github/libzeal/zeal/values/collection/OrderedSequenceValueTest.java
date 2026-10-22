package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.core.ObjectValue;
import io.github.libzeal.zeal.values.sdk.sequence.OrderedSequenceValue;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

/**
 * Test cases for values that implement {@link OrderedSequenceValue}: checks of
 * the positions of elements in an ordered sequence.
 * <p>
 * A value's test class implements this interface (along with the contracts for the other sequence interfaces the value
 * implements) to run these test cases against the value.
 *
 * @param <S>
 *     The type of the subject.
 * @param <E>
 *     The type of the value under test.
 */
interface OrderedSequenceValueTest<S, E extends ObjectValue<S, E> & OrderedSequenceValue<String, E>>
    extends SequenceValueContract<S, E> {

    // ---- hasAtIndex ----

    @Test
    default void givenDesiredAtFirstIndex_whenHasAtIndex_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.hasAtIndex("a", 0))
            .result(TRUE)
            .name("hasAtIndex[desired=a, index=0]")
            .expected("a")
            .actual("a")
            .hint("Desired element found at index 0")
            .run();
    }

    @Test
    default void givenDesiredAtLastIndex_whenHasAtIndex_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.hasAtIndex("b", 1))
            .result(TRUE)
            .name("hasAtIndex[desired=b, index=1]")
            .expected("b")
            .actual("b")
            .run();
    }

    @Test
    default void givenOtherElementAtIndex_whenHasAtIndex_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.hasAtIndex("b", 0))
            .result(FALSE)
            .name("hasAtIndex[desired=b, index=0]")
            .expected("b")
            .actual("a")
            .hint("Index 0 is within bounds, but the desired element was not found there")
            .run();
    }

    @Test
    default void givenIndexOutOfBounds_whenHasAtIndex_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.hasAtIndex("a", 2))
            .result(FALSE)
            .name("hasAtIndex[desired=a, index=2]")
            .expected("a")
            .actual("Index 2 is out of bounds")
            .hint("Index 2 is out of bounds")
            .run();
    }

    @Test
    default void givenNegativeIndex_whenHasAtIndex_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.hasAtIndex("a", -1))
            .result(FALSE)
            .name("hasAtIndex[desired=a, index=-1]")
            .expected("a")
            .actual("Index -1 is out of bounds")
            .hint("Index -1 is out of bounds")
            .run();
    }

    // ---- doesNotHaveAtIndex ----

    @Test
    default void givenOtherElementAtIndex_whenDoesNotHaveAtIndex_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.doesNotHaveAtIndex("b", 0))
            .result(TRUE)
            .name("doesNotHaveAtIndex[desired=b, index=0]")
            .expected("not[b]")
            .actual("a")
            .run();
    }

    @Test
    default void givenDesiredAtIndex_whenDoesNotHaveAtIndex_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.doesNotHaveAtIndex("a", 0))
            .result(FALSE)
            .name("doesNotHaveAtIndex[desired=a, index=0]")
            .expected("not[a]")
            .actual("a")
            .run();
    }

    @Test
    default void givenIndexOutOfBounds_whenDoesNotHaveAtIndex_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.doesNotHaveAtIndex("a", 2))
            .result(TRUE)
            .name("doesNotHaveAtIndex[desired=a, index=2]")
            .expected("not[a]")
            .actual("Index 2 is out of bounds")
            .run();
    }

    // ---- startsWith ----

    @Test
    default void givenDesiredFirst_whenStartsWith_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.startsWith("a"))
            .result(TRUE)
            .name("startsWith[a]")
            .expected("a")
            .actual("a")
            .hint("The first element is a")
            .run();
    }

    @Test
    default void givenOtherElementFirst_whenStartsWith_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.startsWith("b"))
            .result(FALSE)
            .name("startsWith[b]")
            .expected("b")
            .actual("a")
            .hint("Value has at least one element, but the first element is not b")
            .run();
    }

    @Test
    default void givenEmpty_whenStartsWith_thenFalse() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.startsWith("a"))
            .result(FALSE)
            .name("startsWith[a]")
            .expected("a")
            .actual("Value is empty")
            .hint("Value is empty")
            .run();
    }

    // ---- doesNotStartWith ----

    @Test
    default void givenOtherElementFirst_whenDoesNotStartWith_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.doesNotStartWith("b"))
            .result(TRUE)
            .name("doesNotStartWith[b]")
            .expected("not[b]")
            .actual("a")
            .hint("The first element is a")
            .run();
    }

    @Test
    default void givenDesiredFirst_whenDoesNotStartWith_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.doesNotStartWith("a"))
            .result(FALSE)
            .name("doesNotStartWith[a]")
            .expected("not[a]")
            .actual("a")
            .hint("The first element is a")
            .run();
    }

    @Test
    default void givenEmpty_whenDoesNotStartWith_thenTrue() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.doesNotStartWith("a"))
            .result(TRUE)
            .name("doesNotStartWith[a]")
            .expected("not[a]")
            .actual("Value is empty")
            .hint("Value is empty")
            .run();
    }

    // ---- endsWith ----

    @Test
    default void givenDesiredLast_whenEndsWith_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.endsWith("b"))
            .result(TRUE)
            .name("endsWith[b]")
            .expected("b")
            .actual("b")
            .hint("The last element is b")
            .run();
    }

    @Test
    default void givenOtherElementLast_whenEndsWith_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.endsWith("a"))
            .result(FALSE)
            .name("endsWith[a]")
            .expected("a")
            .actual("b")
            .hint("Value has at least one element, but the last element is not a")
            .run();
    }

    @Test
    default void givenEmpty_whenEndsWith_thenFalse() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.endsWith("a"))
            .result(FALSE)
            .name("endsWith[a]")
            .expected("a")
            .actual("Value is empty")
            .hint("Value is empty")
            .run();
    }

    // ---- doesNotEndWith ----

    @Test
    default void givenOtherElementLast_whenDoesNotEndWith_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.doesNotEndWith("a"))
            .result(TRUE)
            .name("doesNotEndWith[a]")
            .expected("not[a]")
            .actual("b")
            .hint("The last element is b")
            .run();
    }

    @Test
    default void givenDesiredLast_whenDoesNotEndWith_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.doesNotEndWith("b"))
            .result(FALSE)
            .name("doesNotEndWith[b]")
            .expected("not[b]")
            .actual("b")
            .hint("The last element is b")
            .run();
    }

    @Test
    default void givenEmpty_whenDoesNotEndWith_thenTrue() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.doesNotEndWith("a"))
            .result(TRUE)
            .name("doesNotEndWith[a]")
            .expected("not[a]")
            .actual("Value is empty")
            .hint("Value is empty")
            .run();
    }

    // ---- lastIndexOfIs ----

    @Test
    default void givenSingleOccurrenceAtIndex_whenLastIndexOfIs_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.lastIndexOfIs("b", 1))
            .result(TRUE)
            .name("lastIndexOf[b] = 1")
            .expected("1")
            .actual("1")
            .run();
    }

    @Test
    default void givenLastOfDuplicatesAtIndex_whenLastIndexOfIs_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b", "a"))
            .expression(value -> value.lastIndexOfIs("a", 2))
            .result(TRUE)
            .name("lastIndexOf[a] = 2")
            .expected("2")
            .actual("2")
            .run();
    }

    @Test
    default void givenOnlyEarlierOccurrenceAtIndex_whenLastIndexOfIs_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b", "a"))
            .expression(value -> value.lastIndexOfIs("a", 0))
            .result(FALSE)
            .name("lastIndexOf[a] = 0")
            .expected("0")
            .actual("2")
            .run();
    }

    @Test
    default void givenMissingElement_whenLastIndexOfIs_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.lastIndexOfIs("c", 0))
            .result(FALSE)
            .name("lastIndexOf[c] = 0")
            .expected("0")
            .actual("-1")
            .run();
    }

    @Test
    default void givenMissingElement_whenLastIndexOfIsMinusOne_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.lastIndexOfIs("c", -1))
            .result(TRUE)
            .name("lastIndexOf[c] = -1")
            .expected("-1")
            .actual("-1")
            .run();
    }

    // ---- lastIndexOfIsNot ----

    @Test
    default void givenOnlyEarlierOccurrenceAtIndex_whenLastIndexOfIsNot_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b", "a"))
            .expression(value -> value.lastIndexOfIsNot("a", 0))
            .result(TRUE)
            .name("lastIndexOf[a] != 0")
            .expected("not[0]")
            .actual("2")
            .run();
    }

    @Test
    default void givenLastOfDuplicatesAtIndex_whenLastIndexOfIsNot_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b", "a"))
            .expression(value -> value.lastIndexOfIsNot("a", 2))
            .result(FALSE)
            .name("lastIndexOf[a] != 2")
            .expected("not[2]")
            .actual("2")
            .run();
    }

    @Test
    default void givenMissingElement_whenLastIndexOfIsNot_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.lastIndexOfIsNot("c", 0))
            .result(TRUE)
            .name("lastIndexOf[c] != 0")
            .expected("not[0]")
            .actual("-1")
            .run();
    }

    @Test
    default void givenMissingElement_whenLastIndexOfIsNotMinusOne_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.lastIndexOfIsNot("c", -1))
            .result(FALSE)
            .name("lastIndexOf[c] != -1")
            .expected("not[-1]")
            .actual("-1")
            .run();
    }
}
