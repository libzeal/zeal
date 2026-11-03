package io.github.libzeal.zeal.values.core.boxed;

import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@link Short}s.
 *
 * @author Justin Albano
 */
public class BoxedShortValue extends BaseBoxedWholeNumberValue<Short, BoxedShortValue> {

    /**
     * Creates a new Short value.
     *
     * @param subject
     *         The desired subject.
     */
    public BoxedShortValue(final @Nullable Short subject) {
        super(
                subject,
                "Short value",
                new WholeNumberOperations<>(
                        (short) 0,
                        Short.MIN_VALUE,
                        Short.MAX_VALUE
                )
        );
    }
}
