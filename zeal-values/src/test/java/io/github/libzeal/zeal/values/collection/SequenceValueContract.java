package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.core.ObjectValue;
import io.github.libzeal.zeal.values.core.test.ValueTestCaseBuilder;

/**
 * What every sequence value contract needs from the test class that implements it. A test class that extends
 * {@link io.github.libzeal.zeal.values.sdk.BaseValueTest} already provides {@link #newTestCase()}.
 *
 * @param <S>
 *     The type of the subject.
 * @param <E>
 *     The type of the value under test.
 */
interface SequenceValueContract<S, E extends ObjectValue<S, E>> {

    ValueTestCaseBuilder<S, E> newTestCase();

    /**
     * Creates a subject containing the supplied elements, in order. The elements used by the contracts are distinct,
     * so the contracts also apply to sets.
     */
    S subjectOf(String... elements);
}
