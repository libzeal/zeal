package io.github.libzeal.zeal.values.core.boxed;

import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@link Integer}s.
 *
 * @author Justin Albano
 */
public final class BoxedIntegerValue
        extends BaseBoxedWholeNumberValue<Integer, BoxedIntegerValue> {

    /**
     * Creates a new Integer value.
     *
     * @param subject
     *         The desired subject.
     */
    public BoxedIntegerValue(final @Nullable Integer subject) {
        super(
                subject,
                "Integer value",
                new WholeNumberOperations<>(
                        0,
                        Integer.MIN_VALUE,
                        Integer.MAX_VALUE
                )
        );
    }
}
