package io.github.libzeal.zeal.logic.evaluation;

import java.util.Optional;

/**
 * The rationale for why an {@link Evaluation} evaluated to the result that it
 * did. A rationale is made up of at least three parts:
 * <ol>
 *     <li><strong>Expected Value</strong>: The value that the expression should
 *      have evaluated to</li>
 *     <li><strong>Actual Value</strong>: The value that the expression actually
 *      evaluated to</li>
 *     <li><strong>Hint</strong>: An optional hint that provides additional
 *      information about why the actual value does not match the expected value
 *      or what can be done to ensure that the two values match in future
 *      evaluations</li>
 * </ol>
 * <p>
 * Hints are provided for evaluations that fail for a common reason or are very
 * particular about the expected value.
 * <p>
 * Rationales also handle negations. A <strong>negation</strong> occurs when an
 * evaluation is wrapped in a compound evaluation that negates the result of an
 * evaluation (such as NOT, NOR, and NAND). Negations can also be canceled out
 * if an evaluation is wrapped by an even number of negations (such as
 * {@code NOT(NOT(evaluation))}). The {@link #negatedExpected()} and
 * {@link #negatedHint()} methods are available to provide fine-grained control
 * over the expected value and hint, respectively, when the evaluation is
 * negated.
 *
 * @author Justin Albano
 */
public interface Rationale {

    /**
     * Obtains the expected value for the evaluation.
     *
     * @return The expected value for the evaluation.
     */
    String expected();

    /**
     * Obtains the expected value for the evaluation when the evaluation is
     * negated, such as when it is wrapped by a NOT, NOR, or NAND expression.
     * <p>
     * By default, the expected value is wrapped as {@code not[expected]}.
     *
     * @return The expected value for the evaluation when the evaluation is
     * negated.
     */
    default String negatedExpected() {
        return "not[" + expected() + "]";
    }

    /**
     * Obtains the actual value for the evaluation.
     *
     * @return The actual value for the evaluation.
     */
    String actual();

    /**
     * Obtains the optional hint for the evaluation.
     *
     * @return An {@link Optional} populated with the hint if a hint is
     * available; an empty {@link Optional} otherwise.
     */
    Optional<String> hint();

    /**
     * Obtains the optional hint for the evaluation when the evaluation is
     * negated, such as when it is wrapped by a NOT, NOR, or NAND expression.
     * <p>
     * By default, the hint is prefixed with {@code Negated: }.
     *
     * @return An {@link Optional} populated with the negated hint if a hint is
     * available; an empty {@link Optional} otherwise.
     */
    default Optional<String> negatedHint() {
        return hint().map(hint -> "Negated: " + hint);
    }
}
