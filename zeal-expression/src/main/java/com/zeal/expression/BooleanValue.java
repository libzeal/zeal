package com.zeal.expression;

public enum BooleanValue implements BooleanExpression {

    TRUE(true),
    FALSE(false);

    private final boolean value;

    private BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public BooleanResult result() {
        return new BooleanResult(() -> value);
    }
}
