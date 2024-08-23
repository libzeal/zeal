package io.github.libzeal.zeal.logic.evaluation.format;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;

/**
 * A service responsible for formatting an {@link Evaluation}.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public interface Formatter {

    /**
     * Formats the supplied evaluation.
     *
     * @param evaluation
     *     The evaluation to format.
     *
     * @return The formatted evalation.
     */
    String format(Evaluation evaluation);
}
