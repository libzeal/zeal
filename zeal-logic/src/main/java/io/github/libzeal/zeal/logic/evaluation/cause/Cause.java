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
     * Creates a new cause with the
     * @param evaluation
     * @param underlyingCause
     */
    public Cause(final Evaluation evaluation, final Cause underlyingCause) {
        this.evaluation = requireNonNull(evaluation);
        this.underlyingCause = underlyingCause;
    }

    public Cause(final Evaluation evaluation) {
        this(evaluation, null);
    }

    public boolean is(final Evaluation other) {
        return Objects.equals(evaluation, other);
    }

    public Evaluation evaluation() {
        return evaluation;
    }

    public Cause rootCause() {
        return rootCauseChain().rootCause();
    }

    public Optional<Cause> underlyingCause() {
        return Optional.ofNullable(underlyingCause);
    }

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
