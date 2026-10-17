package io.github.libzeal.zeal.values.core.boxed;

public interface BoxedWholeNumberValue<T extends Number, E extends BoxedWholeNumberValue<T, E>>
        extends BoxedNumberValue<T, E> {

    /**
     * Adds a predicate to the expression that checks if the subject is even.
     *
     * @return This expression (fluent interface).
     */
    E isEven();

    /**
     * Adds a predicate to the expression that checks if the subject is odd.
     *
     * @return This expression (fluent interface).
     */
    E isOdd();
}
