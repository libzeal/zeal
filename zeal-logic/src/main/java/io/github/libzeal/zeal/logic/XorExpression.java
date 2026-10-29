package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An expression whose children are XORed together. This expression evaluates to
 * true if there are an odd number of children that evaluate to true.
 * <p>
 * This expression evaluates using standard XOR logic for two children, where
 * the XOR expression evaluates to true if exactly one child evaluates to true.
 * For three or more children, the expression is treated as successive XOR
 * operations. For example, {@code A ^ B ^ C} is treated as {@code (A ^ B) ^ C}.
 * Therefore, if an odd number of children evaluate to true, the XOR expression
 * will evaluate to true.
 *
 * @param name
 *         The name of the expression.
 * @param children
 *         The sub-expressions that are XORed together. If no children are
 *         provided, the XOR expression evaluates to false.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public record XorExpression(
        String name,
        List<Expression> children
) implements CompoundExpression {

    static final String DEFAULT_NAME = "XOR";
    static final String EXPECTED = "true is odd";

    /**
     * Creates a new XOR expression.
     *
     * @param name
     *         The name of the expression.
     * @param children
     *         The sub-expressions that are XORed together. If no children are
     *         provided, the XOR expression evaluates to false.
     *
     * @throws NullPointerException
     *         The supplied name or children are {@code null}, or if the
     *         supplied children list contains any {@code null}s.
     */
    public XorExpression {
        requireNonNull(name);
        children = List.copyOf(children);
    }

    /**
     * Creates an XOR expression with a default name.
     *
     * @param children
     *         The sub-expressions to be XORed together.
     *
     * @return An XOR expression with a default name.
     *
     * @throws NullPointerException
     *         The supplied children list is {@code null} or the list contains
     *         any {@code null}s.
     */
    public static XorExpression unnamed(final List<Expression> children) {
        return new XorExpression(DEFAULT_NAME, children);
    }

    /**
     * Creates an XOR expression with a default name.
     *
     * @param children
     *         The sub-expressions to be XORed together.
     *
     * @return An XOR expression with a default name.
     *
     * @throws NullPointerException
     *         The supplied children list is {@code null} or the list contains
     *         any {@code null}s.
     */
    public static XorExpression unnamed(final Expression... children) {
        return new XorExpression(DEFAULT_NAME, List.of(children));
    }

    @Override
    public CompoundEvaluation evaluate() {
        return CompoundEvaluator.evaluate(
                this, EXPECTED,
                child -> false,
                tally -> tally.trueCount() % 2 == 1
        );
    }
}
