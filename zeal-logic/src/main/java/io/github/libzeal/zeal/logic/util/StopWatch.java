package io.github.libzeal.zeal.logic.util;

import java.time.Duration;
import java.time.Instant;

/**
 * A utility class that tracks time.
 *
 * @author Justin Albano
 */
public class StopWatch {

    private Instant start;

    private StopWatch() {
    }

    /**
     * Creates a stop watch that is not started.
     *
     * @return A new unstarted stop watch.
     */
    public static StopWatch unstarted() {
        return new StopWatch();
    }

    /**
     * Creates a stop watch that is started.
     *
     * @return A new started stop watch.
     */
    public static StopWatch started() {
        return new StopWatch().start();
    }

    /**
     * Starts the stop watch.
     *
     * @return This stop watch (fluent interface).
     */
    public StopWatch start() {
        this.start = Instant.now();
        return this;
    }

    /**
     * Stops the stop watch and returns the time elapsed since the last time {@link StopWatch#started()}.
     *
     * @return The time elapsed since the stop watch was started.
     */
    public Duration stop() {
        return Duration.between(start, Instant.now());
    }
}
