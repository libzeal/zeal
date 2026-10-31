package io.github.libzeal.zeal.values.sdk;

/**
 * A standard set of messages used for expected values, actual values, hints,
 * and other string-based notes.
 *
 * @author Justin Albano
 */
public class StandardMessages {

    /**
     * Common message when a condition is satisfied.
     */
    public static final String CONDITION_SATISFIED = "Condition satisfied";

    /**
     * Common message when a condition is unsatisfied.
     */
    public static final String CONDITION_UNSATISFIED = "Condition unsatisfied";

    /**
     * Common message when a value is compared to a {@code null} type.
     */
    public static final String ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE = "Always fail: cannot compare to (null) type";

    /**
     * Common hint when a value is compared to a {@code null} type.
     */
    public static final String EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE =
            "Evaluation will always fail when compared to a (null) type";

    /**
     * Standard message when a value is checked against a {@code null}
     * condition.
     */
    public static final String ALWAYS_FAIL_CANNOT_CHECK_TO_NULL_CONDITION = "Always fail: cannot compare to (null) condition";

    /**
     * Standard message when a value is checked against a {@code null}
     * comparator.
     */
    public static final String CANNOT_COMPARE_USING_NULL_COMPARATOR =
            "Evaluation will always fail when using (null) comparator";

    /**
     * Standard hint when a value is checked against a {@code null} condition.
     */
    public static final String CANNOT_CHECK_USING_NULL_CONDITION =
            "Evaluation will always fail when checking a (null) condition";

    private StandardMessages() {
    }
}
