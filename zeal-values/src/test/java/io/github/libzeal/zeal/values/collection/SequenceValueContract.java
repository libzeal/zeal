package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.core.ObjectValue;
import io.github.libzeal.zeal.values.core.test.ValueTestCaseBuilder;

interface SequenceValueContract<S, E extends ObjectValue<S, E>> {

    ValueTestCaseBuilder<S, E> newTestCase();

    S subjectOf(String... elements);
}
