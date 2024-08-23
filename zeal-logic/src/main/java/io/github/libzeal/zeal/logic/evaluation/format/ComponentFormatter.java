package io.github.libzeal.zeal.logic.evaluation.format;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;

/**
 * A formatter responsible for formatting the various components of an {@link Evaluation}, as {@link Rationale} and
 * {@link Cause}.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public interface ComponentFormatter {

    /**
     * Formats the supplied rationale.
     *
     * @param rationale
     *     The rationale to format.
     * @param context
     *     The context to use when formatting the supplied rationale.
     *
     * @return The formatted rationale.
     */
    String format(Rationale rationale, ComponentContext context);

    /**
     * Formats the supplied cause.
     *
     * @param cause
     *     The cause to format.
     * @param context
     *     The context to use when formatting the supplied cause.
     *
     * @return The formatted cause.
     */
    String format(Cause cause, ComponentContext context);

    /**
     * Formats the supplied evaluation.
     *
     * @param evaluation
     *     The evaluation to format.
     * @param context
     *     The context to use when formatting the supplied evaluation.
     *
     * @return The formatted evaluation.
     */
    String format(Evaluation evaluation, ComponentContext context);
}
