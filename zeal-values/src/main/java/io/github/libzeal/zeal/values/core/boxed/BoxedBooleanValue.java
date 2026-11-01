package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.sdk.BaseValue;

/**
 * A value used to evaluate {@link Boolean}s.
 *
 * @author Justin Albano
 */
public class BoxedBooleanValue extends BaseValue<Boolean, BoxedBooleanValue> {

    /**
     * Creates a new Boolean expression.
     *
     * @param subject
     *         The desired subject.
     */
    public BoxedBooleanValue(final Boolean subject) {
        super(subject, "Boolean value");
    }

    /**
     * Creates a new Boolean value.
     *
     * @return This value (fluent interface).
     */
    public BoxedBooleanValue isTrue() {
        return append(
                expression(Boolean::booleanValue)
                        .name("isTrue")
                        .expected("true")
                        .actual(context -> String.valueOf(context.subject()))
                        .build()
        );
    }

    /**
     * Checks if the subject is false.
     *
     * @return This value (fluent interface).
     */
    public BoxedBooleanValue isFalse() {
        return append(
                expression(b -> !b)
                        .name("isFalse")
                        .expected("false")
                        .actual(context -> String.valueOf(context.subject()))
                        .build()
        );
    }
}
