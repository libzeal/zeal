package io.github.libzeal.zeal.logic.util;

import java.time.Duration;
import java.time.Instant;

public class StopWatch {

    private Instant start;

    private StopWatch() {}

    public static StopWatch unstarted() {
        return new StopWatch();
    }

    public static StopWatch started() {
        return new StopWatch().start();
    }

    public StopWatch start() {
        this.start = Instant.now();
        return this;
    }

    public Duration stop() {
        return Duration.between(start, Instant.now());
    }
}
