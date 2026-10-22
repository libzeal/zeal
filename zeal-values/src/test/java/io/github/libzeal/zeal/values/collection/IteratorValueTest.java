package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

class IteratorValueTest extends BaseValueTest<Iterator<String>, IteratorValue<String>> {

    // Iterators use identity for equals, hashCode, and toString, so the same instances must be returned each time
    private static final Iterator<String> VALUE_A = List.of("foo").iterator();
    private static final Iterator<String> VALUE_B = List.of("bar").iterator();

    @Override
    protected IteratorValue<String> expression(final Iterator<String> value) {
        return new IteratorValue<>(value);
    }

    @Override
    protected Iterator<String> nonNullValueA() {
        return VALUE_A;
    }

    @Override
    protected Iterator<String> nonNullValueB() {
        return VALUE_B;
    }

    // ---- hasNext ----

    @Test
    void givenRemainingElement_whenHasNext_thenTrue() {
        newTestCase()
            .subject(List.of("a").iterator())
            .expression(IteratorValue::hasNext)
            .result(TRUE)
            .name("hasNext")
            .expected("hasNext")
            .actual("hasNext")
            .run();
    }

    @Test
    void givenNoRemainingElements_whenHasNext_thenFalse() {
        newTestCase()
            .subject(Collections.emptyIterator())
            .expression(IteratorValue::hasNext)
            .result(FALSE)
            .name("hasNext")
            .expected("hasNext")
            .actual("not[hasNext]")
            .run();
    }

    // ---- doesNotHaveNext ----

    @Test
    void givenNoRemainingElements_whenDoesNotHaveNext_thenTrue() {
        newTestCase()
            .subject(Collections.emptyIterator())
            .expression(IteratorValue::doesNotHaveNext)
            .result(TRUE)
            .name("not[hasNext]")
            .expected("not[hasNext]")
            .actual("not[hasNext]")
            .run();
    }

    @Test
    void givenRemainingElement_whenDoesNotHaveNext_thenFalse() {
        newTestCase()
            .subject(List.of("a").iterator())
            .expression(IteratorValue::doesNotHaveNext)
            .result(FALSE)
            .name("not[hasNext]")
            .expected("not[hasNext]")
            .actual("hasNext")
            .run();
    }
}
