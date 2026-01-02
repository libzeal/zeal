package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.api.BaseObjectValue;

/**
 * An expression used to evaluate {@link Boolean} instances.
 * <p>
 * Note: Many of the predicates of this expression require unboxing, resulting in some performance loss.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedBooleanValue extends BaseObjectValue<Boolean, BoxedBooleanValue> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedBooleanValue(final Boolean subject) {
        super(subject, "Boolean value");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is true.
     *
     * @return This expression (fluent interface).
     */
    public BoxedBooleanValue isTrue() {
        return append(
            expression(Boolean::booleanValue)
                .name("isTrue")
                .expected("true")
                .actual((s, passed) -> String.valueOf(s))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is false.
     *
     * @return This expression (fluent interface).
     */
    public BoxedBooleanValue isFalse() {
        return append(
            expression(b -> !b)
                .name("isFalse")
                .expected("false")
                .actual((s, passed) -> String.valueOf(s))
        );
    }
}
