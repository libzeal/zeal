package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * An evaluation that results from a terminal expression.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class TerminalEvaluation implements Evaluation {

    private final Result result;
    private final String name;
    private final Rationale rationale;
    private final Duration elapsedTime;
    private final CauseGenerator causeGenerator;

    private TerminalEvaluation(final Result result, final String name, final Rationale rationale,
                               final Duration elapsedTime, final CauseGenerator causeGenerator) {
        this.result = requireNonNull(result);
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
        this.elapsedTime = requireNonNull(elapsedTime);
        this.causeGenerator = requireNonNull(causeGenerator);
    }

    /**
     * Creates a true evaluation.
     *
     * @param name
     *     The name of the evaluation.
     * @param rationale
     *     The rationale for the evaluation.
     *
     * @return The true evaluation.
     */
    public static TerminalEvaluation ofTrue(final String name, final Rationale rationale, final Duration elapsedTime) {
        return new TerminalEvaluation(Result.TRUE, name, rationale, elapsedTime, CauseGenerator.self());
    }

    /**
     * Creates a false evaluation.
     *
     * @param name
     *     The name of the evaluation.
     * @param rationale
     *     The rationale for the evaluation.
     *
     * @return The false evaluation.
     */
    public static TerminalEvaluation ofFalse(final String name, final Rationale rationale, final Duration elapsedTime) {
        return new TerminalEvaluation(Result.FALSE, name, rationale, elapsedTime, CauseGenerator.self());
    }

    /**
     * Creates a skipped evaluation.
     *
     * @param name
     *     The name of the evaluation.
     * @param causeGenerator
     *     The generator for the cause for the evaluation.
     *
     * @return The skipped evaluation.
     */
    public static TerminalEvaluation ofSkipped(final String name, final CauseGenerator causeGenerator) {

        final SimpleRationale rationale = SimpleRationale.skipped();

        return new TerminalEvaluation(Result.SKIPPED, name, rationale, Duration.ZERO, causeGenerator);
    }

    @Override
    public Result result() {
        return result;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Rationale rationale() {
        return rationale;
    }

    @Override
    public Duration elapsedTime() {
        return elapsedTime;
    }

    @Override
    public Cause cause() {
        return causeGenerator.generate(this);
    }

    @Override
    public void traverseDepthFirst(final Traverser traverser) {
        traverseDepthFirst(traverser, TraversalContext.create());
    }

    @Override
    public void traverseDepthFirst(final Traverser traverser, final TraversalContext context) {

        if (traverser != null) {
            traverser.on(this, context);
        }
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) return true;
        if (!(o instanceof TerminalEvaluation)) return false;

        final TerminalEvaluation that = (TerminalEvaluation) o;

        return result == that.result &&
            Objects.equals(name, that.name) &&
            Objects.equals(rationale, that.rationale) &&
            Objects.equals(cause(), that.cause());
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, name, rationale, cause());
    }
}
