package io.github.libzeal.zeal.logic.evaluation;

import java.util.function.BooleanSupplier;

public record TerminalEvaluation(String name, Result result) implements Evaluation {

    static TerminalEvaluation fromBoolean(final String name, final boolean b) {
        return new TerminalEvaluation(name, Result.from(b));
    }

    static TerminalEvaluation fromBooleanSupplier(final String name, final BooleanSupplier supplier) {
        return fromBoolean(name, supplier.getAsBoolean());
    }
}
