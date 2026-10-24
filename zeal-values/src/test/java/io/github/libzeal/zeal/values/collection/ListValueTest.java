package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

class ListValueTest extends BaseValueTest<List<String>, ListValue<String>>
    implements SizedSequenceValueTest<List<String>, ListValue<String>>,
    InspectableSequenceValueTest<List<String>, ListValue<String>>,
    RepeatableSequenceValueTest<List<String>, ListValue<String>>,
    OrderedSequenceValueTest<List<String>, ListValue<String>> {

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
            .result(TRUE)
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
            .result(FALSE)
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
            .result(TRUE)
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
            .result(TRUE)
            .name("includesExactly[desired=a, times=2]")
            .expected("found 'a' 2 times")
            .actual("found 'a' 2 times")
            .run();
    }
}
