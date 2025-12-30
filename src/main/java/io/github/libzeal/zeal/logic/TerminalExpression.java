package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Reason;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.util.StopWatch;

import java.time.Duration;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public record TerminalExpression(String name, BooleanSupplier expression, Supplier<Reason> reason)
    implements Expression {

    @Override
    public Evaluation evaluate() {

        final StopWatch stopWatch = StopWatch.started();

        final Result result = Result.from(expression.getAsBoolean());

        final Duration elapsedTime = stopWatch.stop();

        return new TerminalEvaluation(name, result, reason.get(), elapsedTime);
    }
}
