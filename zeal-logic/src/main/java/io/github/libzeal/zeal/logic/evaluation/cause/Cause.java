package io.github.libzeal.zeal.logic.evaluation.cause;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * A cause for the failure of an evaluation.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class Cause {

    private final Evaluation evaluation;
    private final Cause underlyingCause;

    /**
     * Creates a cause with an underlying cause.
     *
     * @param evaluation
     *     The evaluation that is the cause.
     * @param underlyingCause
     *     The underlying cause.
     *
     * @throws NullPointerException
     *     The supplied evaluation is {@code null}.
     */
    public Cause(final Evaluation evaluation, final Cause underlyingCause) {
        this.evaluation = requireNonNull(evaluation);
        this.underlyingCause = underlyingCause;
    }

    /**
     * Creates a cause without an underlying assumption.
     *
     * @param evaluation
     *     The evaluation that is the cause.
     *
     * @throws NullPointerException
     *     The supplied evaluation is {@code null}.
     */
    public Cause(final Evaluation evaluation) {
        this(evaluation, null);
    }

    /**
     * Checks if the supplied evaluation is the cause.
     *
     * @param other
     *     The evaluation to check.
     *
     * @return True if the supplied evaluation is the cause; false otherwise.
     */
    public boolean is(final Evaluation other) {
        return Objects.equals(evaluation, other);
    }

    /**
     * Obtains the evaluation that is the cause.
     *
     * @return The evaluation that is the cause.
     */
    public Evaluation evaluation() {
        return evaluation;
    }

    /**
     * Obtains the root cause of this cause. If this cause has an underlying cause, the underlying cause is followed
     * until it terminates (transitive underlying causes are traversed), at which point, the terminal cause is
     * considered the root cause. If this cause does not have an underlying cause, then this cause is concerned its own
     * root cause (this cause is returned).
     *
     * @return The root cause of this cause.
     */
    public Cause rootCause() {
        return rootCauseChain().rootCause();
    }

    /**
     * Obtains the underlying cause.
     *
     * @return An {@link Optional} populated with the underlying cause if there is an underlying cause; an empty
     *     {@link Optional} otherwise.
     */
    public Optional<Cause> underlyingCause() {
        return Optional.ofNullable(underlyingCause);
    }

    /**
     * Obtains a root cause chain for this cause. The chain is created by traversing the underlying cause (if one
     * exists) and finding its transitive underlying causes until a terminal cause is found. If there is no underlying
     * cause, a chain containing only this cause is returned.
     *
     * @return The root cause chain for this cause.
     */
    public RootCauseChain rootCauseChain() {

        final RootCauseChain chain = RootCauseChain.with(this);

        try {
            while (true) {
                final Cause tail = chain.last();
                final Optional<Cause> next = tail.underlyingCause();

                if (next.isPresent()) {
                    chain.append(next.get());
                }
                else {
                    return chain;
                }
            }
        }
        catch (final MaximumDepthExceededException | CycleDetectedException e) {
            return chain;
        }
    }

    @Override
    public String toString() {
        return evaluation.name();
    }
}
