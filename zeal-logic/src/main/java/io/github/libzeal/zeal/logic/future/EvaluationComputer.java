package io.github.libzeal.zeal.logic.future;

import io.github.libzeal.zeal.logic.evaluation.EvaluatedTerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.function.BooleanSupplier;

class EvaluationComputer {

    private final String name;
    private final BooleanSupplier predicate;
    private final ResultBasedRationale rationaleSupplier;

    EvaluationComputer(final String name, final BooleanSupplier predicate,
                              final ResultBasedRationale rationaleSupplier) {
        this.name = name;
        this.predicate = predicate;
        this.rationaleSupplier = rationaleSupplier;
    }

    public static EvaluationComputer of(final String name, final BooleanSupplier predicate,
                                        final ResultBasedRationale rationaleSupplier) {
        return new EvaluationComputer(name, predicate, rationaleSupplier);
    }

    public Evaluation compute() {

        final StopWatch stopWatch = StopWatch.started();
        final boolean passed = predicate.getAsBoolean();
        final Duration elapsedTime = stopWatch.stop();

        final Rationale rationale = rationaleSupplier.compute(passed);
        final Result result = Result.from(passed);

        return EvaluatedTerminalEvaluation.of(result, name, rationale, elapsedTime);

    }

    public interface ResultBasedRationale {
        Rationale compute(boolean passed);
    }
}
