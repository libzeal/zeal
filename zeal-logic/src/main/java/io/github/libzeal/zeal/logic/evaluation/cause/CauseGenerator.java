package io.github.libzeal.zeal.logic.evaluation.cause;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;

/**
 * A generator used to defer the creation of {@link Cause} instances. This is used since the cause of an evaluation may
 * refer to the evaluation (a circular creation if deferral is not available).
 *
 * @author Justin Albano
 * @since 0.2.1
 */
@FunctionalInterface
public interface CauseGenerator {

    /**
     * Creates a new cause with the supplied evaluation at the cause evaluation.
     *
     * @param evaluation
     *     The evaluation to create a cause for.
     *
     * @return A new cause.
     */
    Cause generate(Evaluation evaluation);

    /**
     * A generator that will create causes that refer to the evaluation only (no underlying cause).
     *
     * @return A new cause.
     */
    static CauseGenerator self() {
        return Cause::new;
    }

    /**
     * A generator that will create causes that refer to the evaluation with an underlying cause matching the supplied
     * cause.
     *
     * @param cause
     *     The underlying cause.
     *
     * @return A new cause.
     */
    static CauseGenerator withUnderlyingCause(final Cause cause) {
        return evaluation -> new Cause(evaluation, cause);
    }
}
