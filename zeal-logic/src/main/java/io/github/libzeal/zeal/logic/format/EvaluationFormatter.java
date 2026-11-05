package io.github.libzeal.zeal.logic.format;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;

/**
 * A service responsible for formatting an {@link Evaluation}.
 *
 * @author Justin Albano
 */
public interface EvaluationFormatter {

    /**
     * Formats the supplied evaluation.
     *
     * @param evaluation
     *         The evaluation to format.
     *
     * @return The formatted evaluation.
     */
    String format(Evaluation evaluation);
}
