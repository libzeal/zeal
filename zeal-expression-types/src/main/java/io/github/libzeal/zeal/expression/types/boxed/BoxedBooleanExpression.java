package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.types.ObjectExpression;

/**
 * An expression used to evaluate {@link Boolean} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedBooleanExpression extends ObjectExpression<Boolean, BoxedBooleanExpression> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedBooleanExpression(final Boolean subject) {
        super(subject, "Boolean expression");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is true.
     *
     * @return This expression (fluent interface).
     */
    public BoxedBooleanExpression isTrue() {
        return newPredicate(Boolean::booleanValue)
            .name("isTrue")
            .expectedValue("true")
            .actualValue(value -> String.valueOf(value.booleanValue()))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is false.
     *
     * @return This expression (fluent interface).
     */
    public BoxedBooleanExpression isFalse() {
        return newPredicate(b -> !b)
            .name("isFalse")
            .expectedValue("false")
            .actualValue(value -> String.valueOf(value.booleanValue()))
            .append();
    }
}
