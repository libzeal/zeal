package io.github.libzeal.zeal.expression.evaluation;

import java.util.Optional;

/**
 * The rationale for why an {@link EvaluatedExpression} evaluated to the result that it did. A rationale is made up of
 * at least three parts:
 * <ol>
 *     <li><strong>Expected Value</strong>: The value that the expression should have evaluated to/li>
 *     <li><strong>Actual Value</strong>: The value that the expression actually evaluated to</li>
 *     <li><strong>Hint</strong>: An optional hint that provides additional information about why the actual value
 *     does not match the expected value or what can be done to ensure that the two values match in future
 *     evaluations</li>
 * </ol>
 * <p>
 * Hints are provided for evaluations that fail for a common reason or are very particular about the expected value.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class Rationale {

    private final String expected;
    private final String actual;
    private final String hint;

    /**
     * Creates an empty rationale, where the expected value, actual value, and hint are all blank strings. This is
     * common when an evaluation is skipped so that no computation is spent deriving the rationale for an evaluation
     * that did not occur.
     *
     * @return An empty rationale.
     */
    public static Rationale empty() {
        return new Rationale("", "");
    }

    /**
     * Creates a new rationale without a hint.
     *
     * @param expected
     *     The expected value.
     * @param actual
     *     The actual value.
     */
    public Rationale(String expected, String actual) {
        this(expected, actual, null);
    }

    /**
     * Creates a new rationale without a hint.
     *
     * @param expected
     *     The expected value.
     * @param actual
     *     The actual value.
     * @param hint
     *     A hint as to how the expected and actual values should match.
     */
    public Rationale(String expected, String actual, String hint) {
        this.expected = expected;
        this.actual = actual;
        this.hint = hint;
    }

    /**
     * Obtains the expected value for the evaluation.
     *
     * @return The expected value for the evaluation.
     */
    public String expected() {
        return expected;
    }

    /**
     * Obtains the actual value for the evaluation.
     *
     * @return The actual value for the evaluation.
     */
    public String actual() {
        return actual;
    }

    /**
     * Obtains the optional hint for the evaluation.
     *
     * @return An {@link Optional} populated with the hint if a hint is available; an empty {@link Optional} otherwise.
     */
    public Optional<String> hint() {
        return Optional.ofNullable(hint);
    }
}
