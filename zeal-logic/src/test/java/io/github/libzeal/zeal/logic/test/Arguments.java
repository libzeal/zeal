package io.github.libzeal.zeal.logic.test;

import io.github.libzeal.zeal.logic.Expression;

import java.util.ArrayList;
import java.util.List;

public class Arguments {

    private Arguments() {
    }

    public static List<Expression> listWithSingleNull() {

        final List<Expression> expressions = new ArrayList<>();

        expressions.add(null);

        return expressions;
    }
}
