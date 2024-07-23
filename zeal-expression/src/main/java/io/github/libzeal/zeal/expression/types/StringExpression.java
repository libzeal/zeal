package io.github.libzeal.zeal.expression.types;

/**
 * An expression used to evaluate {@link String} instances.
 */
public class StringExpression extends ObjectExpression<String, StringExpression> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public StringExpression(String subject) {
        super(subject, "String evaluation");
    }
}
