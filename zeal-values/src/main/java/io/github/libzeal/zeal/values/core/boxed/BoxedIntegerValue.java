package io.github.libzeal.zeal.values.core.boxed;

/**
 * A value used to evaluate {@link Integer}s.
 *
 * @author Justin Albano
 */
public class BoxedIntegerValue
        extends BaseBoxedWholeNumberValue<Integer, BoxedIntegerValue> {

    /**
     * Creates a new Integer value.
     *
     * @param subject
     *         The desired subject.
     */
    public BoxedIntegerValue(final Integer subject) {
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
