package io.github.libzeal.zeal.values.core.boxed;

import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@link Long}s.
 *
 * @author Justin Albano
 */
public final class BoxedLongValue extends BaseBoxedWholeNumberValue<Long, BoxedLongValue> {

    /**
     * Creates a new Long value.
     *
     * @param subject
     *         The desired subject.
     */
    public BoxedLongValue(final @Nullable Long subject) {
        super(
                subject,
                "Long value",
                new WholeNumberOperations<>(
                        0L,
                        Long.MIN_VALUE,
                        Long.MAX_VALUE
                )
        );
    }
}
