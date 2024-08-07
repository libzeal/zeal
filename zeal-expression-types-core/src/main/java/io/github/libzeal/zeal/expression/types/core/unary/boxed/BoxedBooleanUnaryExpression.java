package io.github.libzeal.zeal.expression.types.core.unary.boxed;

import io.github.libzeal.zeal.expression.types.core.unary.ObjectUnaryExpression;

/**
 * An expression used to evaluate {@link Boolean} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class BoxedBooleanUnaryExpression extends ObjectUnaryExpression<Boolean, BoxedBooleanUnaryExpression> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public BoxedBooleanUnaryExpression(final Boolean subject) {
        super(subject, "Boolean expression");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is true.
     *
     * @return This expression (fluent interface).
     */
    public BoxedBooleanUnaryExpression isTrue() {
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
    public BoxedBooleanUnaryExpression isFalse() {
        return newPredicate(b -> !b)
            .name("isFalse")
            .expectedValue("false")
            .actualValue(value -> String.valueOf(value.booleanValue()))
            .append();
    }
}
