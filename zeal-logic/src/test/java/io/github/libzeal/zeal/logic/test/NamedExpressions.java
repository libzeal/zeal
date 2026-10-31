package io.github.libzeal.zeal.logic.test;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.BooleanExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.NandExpression;
import io.github.libzeal.zeal.logic.NorExpression;
import io.github.libzeal.zeal.logic.NotExpression;
import io.github.libzeal.zeal.logic.OrExpression;
import io.github.libzeal.zeal.logic.XorExpression;

import java.util.List;

public class NamedExpressions {

    private NamedExpressions() {}

    public static Expression t(final String name) {
        return new BooleanExpression(name, () -> true);
    }

    public static Expression f(final String name) {
        return new BooleanExpression(name, () -> false);
    }

    public static Expression and(final String name, final Expression... children) {
        return new AndExpression(name, List.of(children));
    }

    public static Expression or(final String name, final Expression... children) {
        return new OrExpression(name, List.of(children));
    }

    public static Expression not(final String name, final Expression wrapped) {
        return new NotExpression(name, wrapped);
    }

    public static Expression nand(final String name, final Expression... children) {
        return new NandExpression(name, List.of(children));
    }

    public static Expression nor(final String name, final Expression... children) {
        return new NorExpression(name, List.of(children));
    }

    public static Expression xor(final String name, final Expression... children) {
        return new XorExpression(name, List.of(children));
    }
}
