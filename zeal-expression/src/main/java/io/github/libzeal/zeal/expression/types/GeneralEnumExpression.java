package io.github.libzeal.zeal.expression.types;

public class GeneralEnumExpression<T extends Enum<T>> extends EnumExpression<T, GeneralEnumExpression<T>> {

    public GeneralEnumExpression(T subject) {
        super(subject);
    }
}
