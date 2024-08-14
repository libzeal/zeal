package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.SimpleEvaluation;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;

/**
 * An expression that can be evaluated.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
@FunctionalInterface
public interface Expression {

    /**
     * Evaluates the expression.
     *
     * @return The evaluated expression. This evaluation must not be {@code null}.
     */
    Evaluation evaluate();

    /**
     * Obtains the name of the expression.
     *
     * @return The name of the expression.
     *
     * @since 0.2.1
     */
    default String name() {
        return "unnamed";
    }

    /**
     * Performs a skipped evaluation.
     *
     * @return An evaluation where the execution of the evaluation has been skipped.
     *
     * @since 0.2.1
     */
    default Evaluation skip() {
        return SimpleEvaluation.skipped(name(), SimpleRationale.skipped());
    }
}
