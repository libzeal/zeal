package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An evaluation that results from a compound expression.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class CompoundEvaluation implements Evaluation {

    private final Result result;
    private final String name;
    private final Rationale rationale;
    private final Duration elapsedTime;
    private final CauseGenerator causeGenerator;
    private final List<Evaluation> children;

    private CompoundEvaluation(final Result result, final String name, final Rationale rationale,
                               final Duration elapsedTime, final CauseGenerator causeGenerator,
                               final List<Evaluation> children) {
        this.result = requireNonNull(result);
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
        this.elapsedTime = requireNonNull(elapsedTime);
        this.causeGenerator = requireNonNull(causeGenerator);
        this.children = requireNonNull(children);
    }

    /**
     * Creates a true compound evaluation.
     *
     * @param name
     *     The name of the evaluation.
     * @param rationale
     *     The ration for the evaluation.
     * @param elapsedTime
     *     The elapsed time of the evaluation.
     * @param causeGenerator
     *     The cause generator of the evaluation.
     * @param children
     *     The children of the evaluation.
     *
     * @return A new compound evaluation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static CompoundEvaluation ofTrue(final String name, final Rationale rationale, final Duration elapsedTime,
                                            final CauseGenerator causeGenerator, final List<Evaluation> children) {
        return new CompoundEvaluation(Result.TRUE, name, rationale, elapsedTime, causeGenerator, children);
    }

    /**
     * Creates a false compound evaluation.
     *
     * @param name
     *     The name of the evaluation.
     * @param rationale
     *     The ration for the evaluation.
     * @param elapsedTime
     *     The elapsed time of the evaluation.
     * @param causeGenerator
     *     The cause generator of the evaluation.
     * @param children
     *     The children of the evaluation.
     *
     * @return A new compound evaluation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static CompoundEvaluation ofFalse(final String name, final Rationale rationale, final Duration elapsedTime,
                                             final CauseGenerator causeGenerator, final List<Evaluation> children) {
        return new CompoundEvaluation(Result.FALSE, name, rationale, elapsedTime, causeGenerator, children);
    }

    /**
     * Creates a skipped compound evaluation.
     *
     * @param name
     *     The name of the evaluation.
     * @param elapsedTime
     *     The elapsed time of the evaluation.
     * @param causeGenerator
     *     The cause generator of the evaluation.
     * @param children
     *     The children of the evaluation.
     *
     * @return A new compound evaluation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static CompoundEvaluation ofSkipped(final String name, final Duration elapsedTime, final CauseGenerator causeGenerator,
                                               final List<Evaluation> children) {

        final SimpleRationale rationale = SimpleRationale.skipped();

        return new CompoundEvaluation(Result.SKIPPED, name, rationale, elapsedTime, causeGenerator,
            children);
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

        if (traverser != null) {
            traverseDepthFirst(traverser, TraversalContext.create());
        }
    }

    @Override
    public void traverseDepthFirst(final Traverser traverser, final TraversalContext context) {

        traverser.on(this, context);

        final TraversalContext childContext = context.withIncrementedDepth();

        for (Evaluation child : children) {
            child.traverseDepthFirst(traverser, childContext);
        }
    }
}
