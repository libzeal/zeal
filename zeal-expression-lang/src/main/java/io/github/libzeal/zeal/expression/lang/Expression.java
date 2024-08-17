package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.RootCause;

/**
 * An expression that can be evaluated.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface Expression {

    /**
     * Obtains the name of the expression.
     *
     * @return The name of the expression.
     *
     * @since 0.2.1
     */
    String name();

    /**
     * Evaluates the expression.
     *
     * @return The evaluated expression. This evaluation must not be {@code null}.
     */
    Evaluation evaluate();

    /**
     * Performs a skipped evaluation.
     *
     * @return An evaluation where the execution of the evaluation has been skipped.
     *
     * @since 0.2.1
     */
    Evaluation skip(RootCause rootCause);
}
