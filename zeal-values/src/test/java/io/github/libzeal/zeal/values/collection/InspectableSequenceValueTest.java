package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.core.ObjectValue;
import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

interface InspectableSequenceValueTest<S, E extends ObjectValue<S, E> & InspectableSequenceValue<String, E>>
    extends SequenceValueContract<S, E> {

    String NULL_DESIRED_HINT = "Desired elements is (null), which passes by default";

    // ---- includes ----

    @Test
    default void givenIncluded_whenIncludes_thenTrue() {
        newTestCase()
            .subject(subjectOf("a", "b"))
            .expression(value -> value.includes("a"))
            .result(TRUE)
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
            .result(FALSE)
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
            .result(FALSE)
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
            .result(TRUE)
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
            .result(TRUE)
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
            .result(TRUE)
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
            .result(FALSE)
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
            .result(TRUE)
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
            .result(TRUE)
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
            .result(TRUE)
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
            .result(FALSE)
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
            .result(TRUE)
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
            .result(TRUE)
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
            .result(TRUE)
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
            .result(FALSE)
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
            .result(TRUE)
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
            .result(TRUE)
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
            .result(TRUE)
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
            .result(FALSE)
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
            .result(TRUE)
            .name("excludesAny")
            .expected("At least one desired element missing in subject")
            .actual("At least one desired element missing in subject")
            .run();
    }
}
