package io.github.libzeal.zeal.values.core.boxed;

/**
 * A value representing a boxed whole {@link Number}.
 *
 * @param <T>
 *         The {@link Number} type.
 * @param <V>
 *         The value type (the implementation type of this interface).
 *
 * @author Justin Albano
 */
public interface BoxedWholeNumberValue<T extends Number, V extends BoxedWholeNumberValue<T, V>>
        extends BoxedNumberValue<T, V> {

    /**
     * Checks if the subject is even.
     *
     * @return This value (fluent interface).
     */
    V isEven();

    /**
     * Checks if the subject is odd.
     *
     * @return This value (fluent interface).
     */
    V isOdd();
}
