package io.github.libzeal.zeal.logic.evaluation;

import java.time.Duration;

public record TerminalEvaluation(String name, Result result, Reason reason, Duration elapsedTime)
    implements Evaluation {

}
