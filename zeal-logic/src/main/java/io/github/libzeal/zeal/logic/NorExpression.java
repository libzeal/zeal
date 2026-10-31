package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.CompoundEvaluator.Tally;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An expression whose children are NORed together.
 *
 * @param name
 *         The name of the expression.
 * @param children
 *         The sub-expressions that are NORed together. If no children are
 *         provided, the NOR expression evaluates to true.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public record NorExpression(
        String name,
        List<Expression> children
) implements CompoundExpression {

    static final String DEFAULT_NAME = "NOR";
    static final String EXPECTED = "true = 0";

    /**
     * Creates a new NOR expression.
     *
     * @param name
     *         The name of the expression.
     * @param children
     *         The sub-expressions to be NORed together.
     *
     * @throws NullPointerException
     *         The supplied name or children is {@code null} or the children
     *         contains any {@code null}s.
     */
    public NorExpression {
        requireNonNull(name);
        children = List.copyOf(children);
    }

    /**
     * Creates an unnamed NOR expression.
     *
     * @param children
     *         The sub-expressions to be NORed together.
     *
     * @return A NOR expression with a default name.
     *
     * @throws NullPointerException
     *         The supplied children list is {@code null} or the list contains
     *         any {@code null}s.
     */
    public static NorExpression unnamed(final List<Expression> children) {
        return new NorExpression(DEFAULT_NAME, children);
    }

    /**
     * Creates an unnamed NOR expression.
     *
     * @param children
     *         The sub-expressions to be NORed together.
     *
     * @return A NOR expression with a default name.
     *
     * @throws NullPointerException
     *         The supplied children list is {@code null} or the list contains
     *         any {@code null}s.
     */
    public static NorExpression unnamed(final Expression... children) {
        return new NorExpression(DEFAULT_NAME, List.of(children));
    }

    @Override
    public CompoundEvaluation evaluate() {
        return CompoundEvaluator.evaluate(
                this,
                EXPECTED,
                Evaluation::isTrue,
                Tally::hasNoTrue
        );
    }
}
