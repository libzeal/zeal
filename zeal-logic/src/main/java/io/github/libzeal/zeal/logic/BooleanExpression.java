package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.function.BooleanSupplier;

import static java.util.Objects.requireNonNull;

public class BooleanExpression implements Expression {

    private final String name;
    private final BooleanSupplier predicate;

    public BooleanExpression(final String name, final BooleanSupplier predicate) {
        this.name = name;
        this.predicate = requireNonNull(predicate);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Evaluation evaluate() {

        final StopWatch stopWatch = StopWatch.started();
        final boolean passed = predicate.getAsBoolean();
        final Rationale rationale = new SimpleRationale("true", String.valueOf(passed));
        final Duration elapsedTime = stopWatch.stop();

        if (passed) {
            return TerminalEvaluation.ofTrue(name, rationale, elapsedTime);
        }
        else {
            return TerminalEvaluation.ofFalse(name, rationale, elapsedTime);
        }
    }
}
