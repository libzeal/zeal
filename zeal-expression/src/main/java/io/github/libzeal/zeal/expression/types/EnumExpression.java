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

    public B ordinalIsNot(final int ordinal) {
        return newEvaluation(s -> s.ordinal() != ordinal)
            .name("not[ordinalIs[" + ordinal + "]]")
            .expectedValue(value -> "not[" + ordinal + "]")
            .actualValue(value -> String.valueOf(value.ordinal()))
            .append();
    }

    public B nameIs(final String name) {
        return newEvaluation(s -> s.name().equals(name))
            .name("nameIs[" + name + "]")
            .expectedValue(name)
            .actualValue(Enum::name)
            .append();
    }

    public B nameIsNot(final String name) {
        return newEvaluation(s -> !s.name().equals(name))
            .name("not[nameIs[" + name + "]]")
            .expectedValue("not[" + name + "]")
            .actualValue(Enum::name)
            .append();
    }

    public B caseInsensitiveNameIs(final String name) {
        return newEvaluation(s -> s.name().equalsIgnoreCase(name))
            .name("caseInsensitiveNameIs[" + name + "]")
            .expectedValue("caseInsensitive[" + name + "]")
            .actualValue(Enum::name)
            .append();
    }

    public B caseInsensitiveNameIsNot(final String name) {
        return newEvaluation(s -> !s.name().equalsIgnoreCase(name))
            .name("not[caseInsensitiveNameIs[" + name + "]]")
            .expectedValue("not[caseInsensitive[" + name + "]]")
            .actualValue(Enum::name)
            .append();
    }
}
