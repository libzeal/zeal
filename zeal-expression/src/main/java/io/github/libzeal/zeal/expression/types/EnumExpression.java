package io.github.libzeal.zeal.expression.types;

public abstract class EnumExpression<T extends Enum<T>, B extends EnumExpression<T, B>>
    extends ObjectExpression<T, B> {

    protected EnumExpression(T subject) {
        super(subject, "Enum evaluation");
    }

    public B ordinalIs(final int ordinal) {
        return newEvaluation(s -> s.ordinal() == ordinal)
            .name("ordinalIs[" + ordinal + "]")
            .expectedValue(ordinal)
            .actualValue(value -> String.valueOf(value.ordinal()))
            .append();
    }
}
