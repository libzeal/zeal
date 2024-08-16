package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.compound.ConjunctiveExpression;
import io.github.libzeal.zeal.expression.lang.compound.DisjunctiveExpression;
import io.github.libzeal.zeal.expression.lang.compound.NonConjunctiveExpression;
import io.github.libzeal.zeal.expression.lang.compound.NonDisjunctiveExpression;
import io.github.libzeal.zeal.expression.lang.unary.NegatedUnaryExpression;
import io.github.libzeal.zeal.expression.lang.unary.UnaryExpression;

import java.util.Arrays;

public class Operations {

    private Operations() {}

    public static Expression not(final Expression expression) {
        return new NegatedExpression(expression);
    }

    public static <T> UnaryExpression<T> not(final UnaryExpression<T> expression) {
        return new NegatedUnaryExpression<>(expression);
    }

    public static Expression and(final Expression... expressions) {
        return ConjunctiveExpression.withDefaultName(Arrays.asList(expressions));
    }

    public static Expression or(final Expression... expressions) {
        return DisjunctiveExpression.withDefaultName(Arrays.asList(expressions));
    }

    public static Expression nand(final Expression... expressions) {
        return NonConjunctiveExpression.withDefaultName(Arrays.asList(expressions));
    }

    public static Expression nor(final Expression... expressions) {
        return NonDisjunctiveExpression.withDefaultName(Arrays.asList(expressions));
    }
}
