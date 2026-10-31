package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.CompoundEvaluator.Tally;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An expression whose children are NANDed together.
 *
 * @param name
 *         The name of the expression.
 * @param children
 *         The sub-expressions that are NANDed together. If no children are
 *         provided, the NANDed expression evaluates to false.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public record NandExpression(
        String name,
        List<Expression> children
) implements CompoundExpression {

    static final String DEFAULT_NAME = "NAND";
    static final String EXPECTED = "false >= 1";

    /**
     * Creates a new NAND expression.
     *
     * @param name
     *         The name of the expression.
     * @param children
     *         The sub-expressions to be NANDed together.
     *
     * @throws NullPointerException
     *         The supplied name or children is {@code null} or the children
     *         contains any {@code null}s.
     */
    public NandExpression {
        requireNonNull(name);
        children = List.copyOf(children);
    }

    /**
     * Creates a NAND expression with a default name.
     *
     * @param children
     *         The sub-expressions to be NANDed together.
     *
     * @return A NAND expression with a default name.
     *
     * @throws NullPointerException
     *         The supplied children list is {@code null} or the list contains
     *         any {@code null}s.
     */
    public static NandExpression unnamed(final List<Expression> children) {
        return new NandExpression(DEFAULT_NAME, children);
    }

    /**
     * Creates a NAND expression with a default name.
     *
     * @param children
     *         The sub-expressions to be NANDed together.
     *
     * @return A NAND expression with a default name.
     *
     * @throws NullPointerException
     *         The supplied children list is {@code null} or the list contains
     *         any {@code null}s.
     */
    public static NandExpression unnamed(final Expression... children) {
        return new NandExpression(DEFAULT_NAME, List.of(children));
    }

    @Override
    public CompoundEvaluation evaluate() {
        return CompoundEvaluator.evaluate(
                this,
                EXPECTED,
                Evaluation::isFalse,
                Tally::hasAtLeastOneFalse
        );
    }
}
