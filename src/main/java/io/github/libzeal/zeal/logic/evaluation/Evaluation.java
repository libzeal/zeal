package io.github.libzeal.zeal.logic.evaluation;

import java.time.Duration;

public interface Evaluation {

    String name();
    Result result();
    Reason reason();
    Duration elapsedTime();
}
