package com.zeal.expression;

import java.util.Optional;
import java.util.function.BooleanSupplier;

public class BooleanResult {

    public static final BooleanResult TRUE = new BooleanResult(() -> true);
    public static final BooleanResult FALSE = new BooleanResult(() -> false);
    private final BooleanSupplier result;
    private final Explanation explanation;

    public BooleanResult(BooleanSupplier result, Explanation explanation) {
        this.result = result;
        this.explanation = explanation;
    }

    public BooleanResult(BooleanSupplier result) {
        this(result, null);
    }

    public boolean isTrue() {
        return result.getAsBoolean();
    }

    public boolean isFalse() {
        return !isTrue();
    }

    public Optional<Explanation> explanation() {
        return Optional.ofNullable(explanation);
    }

    public BooleanResult not() {
        return new BooleanResult(
                () -> !result.getAsBoolean(),
                explanation.not()
        );
    }
}
