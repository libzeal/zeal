package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.core.ObjectValue;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceValue;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

/**
 * Test cases for values that implement {@link SizedSequenceValue}: checks of
 * the size of a sequence.
 * <p>
 * A value's test class implements this interface (along with the contracts for the other sequence interfaces the value
 * implements) to run these test cases against the value.
 *
 * @param <S>
 *     The type of the subject.
 * @param <E>
 *     The type of the value under test.
 */
interface SizedSequenceValueTest<S, E extends ObjectValue<S, E> & SizedSequenceValue<?, E>>
    extends SequenceValueContract<S, E> {

    // ---- isEmpty ----

    @Test
    default void givenEmpty_whenIsEmpty_thenTrue() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.isEmpty())
            .result(TRUE)
            .name("isEmpty")
            .expected("length = 0")
            .actual("length = 0")
            .run();
    }

    @Test
    default void givenNonEmpty_whenIsEmpty_thenFalse() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.isEmpty())
            .result(FALSE)
            .name("isEmpty")
            .expected("length = 0")
            .actual("length = 1")
            .run();
    }

    // ---- isNotEmpty ----

    @Test
    default void givenEmpty_whenIsNotEmpty_thenFalse() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.isNotEmpty())
            .result(FALSE)
            .name("isNotEmpty")
            .expected("length > 0")
            .actual("length = 0")
            .run();
    }

    @Test
    default void givenNonEmpty_whenIsNotEmpty_thenTrue() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.isNotEmpty())
            .result(TRUE)
            .name("isNotEmpty")
            .expected("length > 0")
            .actual("length = 1")
            .run();
    }

    // ---- hasLengthOf ----

    @Test
    default void givenShorter_whenHasLengthOf_thenFalse() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.hasLengthOf(1))
            .result(FALSE)
            .name("hasLengthOf[1]")
            .expected("length = 1")
            .actual("length = 0")
            .run();
    }

    @Test
    default void givenSameLength_whenHasLengthOf_thenTrue() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.hasLengthOf(1))
            .result(TRUE)
            .name("hasLengthOf[1]")
            .expected("length = 1")
            .actual("length = 1")
            .run();
    }

    @Test
    default void givenLonger_whenHasLengthOf_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.hasLengthOf(1))
            .result(FALSE)
            .name("hasLengthOf[1]")
            .expected("length = 1")
            .actual("length = 2")
            .run();
    }

    // ---- doesNotHaveLengthOf ----

    @Test
    default void givenShorter_whenDoesNotHaveLengthOf_thenTrue() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.doesNotHaveLengthOf(1))
            .result(TRUE)
            .name("doesNotHaveLengthOf[1]")
            .expected("length != 1")
            .actual("length = 0")
            .run();
    }

    @Test
    default void givenSameLength_whenDoesNotHaveLengthOf_thenFalse() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.doesNotHaveLengthOf(1))
            .result(FALSE)
            .name("doesNotHaveLengthOf[1]")
            .expected("length != 1")
            .actual("length = 1")
            .run();
    }

    @Test
    default void givenLonger_whenDoesNotHaveLengthOf_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.doesNotHaveLengthOf(1))
            .result(TRUE)
            .name("doesNotHaveLengthOf[1]")
            .expected("length != 1")
            .actual("length = 2")
            .run();
    }

    // ---- isShorterThan ----

    @Test
    default void givenShorter_whenIsShorterThan_thenTrue() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.isShorterThan(1))
            .result(TRUE)
            .name("isShorterThan[1]")
            .expected("length < 1")
            .actual("length = 0")
            .run();
    }

    @Test
    default void givenSameLength_whenIsShorterThan_thenFalse() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.isShorterThan(1))
            .result(FALSE)
            .name("isShorterThan[1]")
            .expected("length < 1")
            .actual("length = 1")
            .run();
    }

    @Test
    default void givenLonger_whenIsShorterThan_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.isShorterThan(1))
            .result(FALSE)
            .name("isShorterThan[1]")
            .expected("length < 1")
            .actual("length = 2")
            .run();
    }

    // ---- isShorterThanOrEqualTo ----

    @Test
    default void givenShorter_whenIsShorterThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.isShorterThanOrEqualTo(1))
            .result(TRUE)
            .name("isShorterThanOrEqualTo[1]")
            .expected("length <= 1")
            .actual("length = 0")
            .run();
    }

    @Test
    default void givenSameLength_whenIsShorterThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.isShorterThanOrEqualTo(1))
            .result(TRUE)
            .name("isShorterThanOrEqualTo[1]")
            .expected("length <= 1")
            .actual("length = 1")
            .run();
    }

    @Test
    default void givenLonger_whenIsShorterThanOrEqualTo_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.isShorterThanOrEqualTo(1))
            .result(FALSE)
            .name("isShorterThanOrEqualTo[1]")
            .expected("length <= 1")
            .actual("length = 2")
            .run();
    }

    // ---- isLongerThan ----

    @Test
    default void givenShorter_whenIsLongerThan_thenFalse() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.isLongerThan(1))
            .result(FALSE)
            .name("isLongerThan[1]")
            .expected("length > 1")
            .actual("length = 0")
            .run();
    }

    @Test
    default void givenSameLength_whenIsLongerThan_thenFalse() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.isLongerThan(1))
            .result(FALSE)
            .name("isLongerThan[1]")
            .expected("length > 1")
            .actual("length = 1")
            .run();
    }

    @Test
    default void givenLonger_whenIsLongerThan_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.isLongerThan(1))
            .result(TRUE)
            .name("isLongerThan[1]")
            .expected("length > 1")
            .actual("length = 2")
            .run();
    }

    // ---- isLongerThanOrEqualTo ----

    @Test
    default void givenShorter_whenIsLongerThanOrEqualTo_thenFalse() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.isLongerThanOrEqualTo(1))
            .result(FALSE)
            .name("isLongerThanOrEqualTo[1]")
            .expected("length >= 1")
            .actual("length = 0")
            .run();
    }

    @Test
    default void givenSameLength_whenIsLongerThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.isLongerThanOrEqualTo(1))
            .result(TRUE)
            .name("isLongerThanOrEqualTo[1]")
            .expected("length >= 1")
            .actual("length = 1")
            .run();
    }

    @Test
    default void givenLonger_whenIsLongerThanOrEqualTo_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.isLongerThanOrEqualTo(1))
            .result(TRUE)
            .name("isLongerThanOrEqualTo[1]")
            .expected("length >= 1")
            .actual("length = 2")
            .run();
    }
}
