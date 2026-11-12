package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.core.ObjectValue;
import io.github.libzeal.zeal.values.core.test.ValueTestCaseBuilder;
import io.github.libzeal.zeal.values.sdk.sequence.SequenceValue;
import org.junit.jupiter.api.Test;

import java.util.List;

interface SequenceValueTest<S, E extends ObjectValue<S, E> & SequenceValue<String, E>> {

    String NULL_DESIRED_HINT = "Desired elements is (null), which passes by default";

    ValueTestCaseBuilder<S, E> newTestCase();

    S subjectOf(String... elements);

    // ---- isEmpty ----

    @Test
    default void givenEmpty_whenIsEmpty_thenTrue() {
        newTestCase()
            .subject(subjectOf())
            .expression(value -> value.isEmpty())
            .result(true)
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
            .result(false)
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
            .result(false)
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
            .result(true)
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
            .result(false)
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
            .result(true)
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
            .result(false)
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
            .result(true)
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
            .result(false)
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
            .result(true)
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
            .result(true)
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
            .result(false)
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
            .result(false)
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
            .result(true)
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
            .result(true)
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
            .result(false)
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
            .result(false)
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
            .result(false)
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
            .result(true)
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
            .result(false)
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
            .result(true)
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
            .result(true)
            .name("isLongerThanOrEqualTo[1]")
            .expected("length >= 1")
            .actual("length = 2")
            .run();
    }

    // ---- includes ----

    @Test
    default void givenIncluded_whenIncludes_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includes("a"))
            .result(true)
            .name("includes[a]")
            .expected("includes[a]")
            .actual("includes[a]")
            .run();
    }

    @Test
    default void givenExcluded_whenIncludes_thenFalse() {
        newTestCase()
            .subject(subjectOf("b"))
            .expression(value -> value.includes("a"))
            .result(false)
            .name("includes[a]")
            .expected("includes[a]")
            .actual("excludes[a]")
            .run();
    }

    // ---- excludes ----

    @Test
    default void givenIncluded_whenExcludes_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.excludes("a"))
            .result(false)
            .name("excludes[a]")
            .expected("excludes[a]")
            .actual("includes[a]")
            .run();
    }

    @Test
    default void givenExcluded_whenExcludes_thenTrue() {
        newTestCase()
            .subject(subjectOf("b"))
            .expression(value -> value.excludes("a"))
            .result(true)
            .name("excludes[a]")
            .expected("excludes[a]")
            .actual("excludes[a]")
            .run();
    }

    // ---- includesAll ----

    @Test
    default void givenNullDesired_whenIncludesAll_thenTrue() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.includesAll(null))
            .result(true)
            .name("includesAll")
            .expected("All desired elements in subject")
            .actual("All desired elements in subject")
            .hint(NULL_DESIRED_HINT)
            .run();
    }

    @Test
    default void givenAllDesiredPresent_whenIncludesAll_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesAll(List.of("a", "b")))
            .result(true)
            .name("includesAll")
            .expected("All desired elements in subject")
            .actual("All desired elements in subject")
            .hint("All desired elements found in subject")
            .run();
    }

    @Test
    default void givenOneDesiredMissing_whenIncludesAll_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesAll(List.of("a", "c")))
            .result(false)
            .name("includesAll")
            .expected("All desired elements in subject")
            .actual("At least one desired element missing in subject")
            .hint("The following desired elements were not found: [c]")
            .run();
    }

    @Test
    default void givenAllDesiredPresent_whenIncludesAllOf_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesAllOf("a", "b"))
            .result(true)
            .name("includesAll")
            .expected("All desired elements in subject")
            .actual("All desired elements in subject")
            .run();
    }

    // ---- includesAny ----

    @Test
    default void givenNullDesired_whenIncludesAny_thenTrue() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.includesAny(null))
            .result(true)
            .name("includesAny")
            .expected("Any desired elements in subject")
            .actual("At least one desired element found in subject")
            .hint(NULL_DESIRED_HINT)
            .run();
    }

    @Test
    default void givenOneDesiredPresent_whenIncludesAny_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesAny(List.of("b", "c")))
            .result(true)
            .name("includesAny")
            .expected("Any desired elements in subject")
            .actual("At least one desired element found in subject")
            .hint("At least one desired element found in subject: [b]")
            .run();
    }

    @Test
    default void givenNoDesiredPresent_whenIncludesAny_thenFalse() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.includesAny(List.of("c")))
            .result(false)
            .name("includesAny")
            .expected("Any desired elements in subject")
            .actual("All desired elements missing in subject")
            .hint("All of the desired elements are missing in subject")
            .run();
    }

    @Test
    default void givenOneDesiredPresent_whenIncludesAnyOf_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includesAnyOf("b", "c"))
            .result(true)
            .name("includesAny")
            .expected("Any desired elements in subject")
            .actual("At least one desired element found in subject")
            .run();
    }

    // ---- excludesAll ----

    @Test
    default void givenNullDesired_whenExcludesAll_thenTrue() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.excludesAll(null))
            .result(true)
            .name("excludesAll")
            .expected("All desired elements missing in subject")
            .actual("All desired elements missing in subject")
            .hint(NULL_DESIRED_HINT)
            .run();
    }

    @Test
    default void givenNoDesiredPresent_whenExcludesAll_thenTrue() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.excludesAll(List.of("c")))
            .result(true)
            .name("excludesAll")
            .expected("All desired elements missing in subject")
            .actual("All desired elements missing in subject")
            .hint("All of the desired elements are missing in subject")
            .run();
    }

    @Test
    default void givenOneDesiredPresent_whenExcludesAll_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.excludesAll(List.of("b", "c")))
            .result(false)
            .name("excludesAll")
            .expected("All desired elements missing in subject")
            .actual("At least one desired element found in subject")
            .hint("At least one desired element found in subject: [b]")
            .run();
    }

    @Test
    default void givenNoDesiredPresent_whenExcludesAllOf_thenTrue() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.excludesAllOf("c"))
            .result(true)
            .name("excludesAll")
            .expected("All desired elements missing in subject")
            .actual("All desired elements missing in subject")
            .run();
    }

    // ---- excludesAny ----

    @Test
    default void givenNullDesired_whenExcludesAny_thenTrue() {
        newTestCase()
            .subject(subjectOf("a"))
            .expression(value -> value.excludesAny(null))
            .result(true)
            .name("excludesAny")
            .expected("At least one desired element missing in subject")
            .actual("At least one desired element missing in subject")
            .hint(NULL_DESIRED_HINT)
            .run();
    }

    @Test
    default void givenOneDesiredMissing_whenExcludesAny_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.excludesAny(List.of("a", "c")))
            .result(true)
            .name("excludesAny")
            .expected("At least one desired element missing in subject")
            .actual("At least one desired element missing in subject")
            .hint("The following desired elements were found: [a]")
            .run();
    }

    @Test
    default void givenAllDesiredPresent_whenExcludesAny_thenFalse() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.excludesAny(List.of("a", "b")))
            .result(false)
            .name("excludesAny")
            .expected("At least one desired element missing in subject")
            .actual("All desired elements found in subject")
            .hint("All desired elements found in subject")
            .run();
    }

    @Test
    default void givenOneDesiredMissing_whenExcludesAnyOf_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.excludesAnyOf("a", "c"))
            .result(true)
            .name("excludesAny")
            .expected("At least one desired element missing in subject")
            .actual("At least one desired element missing in subject")
            .run();
    }

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
