package io.github.libzeal.zeal.expression.evaluation;

import java.util.List;

import io.github.libzeal.zeal.expression.Expression;

/**
 * An {@link Expression} that has been evaluated. An evaluated expression contains a state, the name of the evaluation
 * that was performed, and a rationale that describes how the state was reached (the rationale used to reach the
 * state).
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface EvaluatedExpression {

    /**
     * Obtains the state of the evaluation (i.e., pass or fail).
     *
     * @return The state of the evaluation.
     */
    Result result();

    /**
     * Obtains the name of the evaluation that was preformed.
     *
     * @return The name of the evaluation that was performed.
     */
    String name();

    /**
     * Obtains the rationale used to reach the result.
     *
     * @return The rationale used to reach the result.
     */
    Rationale rationale();

    List<EvaluatedExpression> children();
}
