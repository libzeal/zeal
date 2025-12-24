package io.github.libzeal.zeal.test;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.Expressions;
import io.github.libzeal.zeal.logic.OrExpression;
import io.github.libzeal.zeal.logic.evaluation.ShortCircuitEvaluator;
import io.github.libzeal.zeal.logic.format.SimpleEvaluationFormatter;

import java.util.List;

public class Main {

    void main() {

        final var a = Expressions.tautology();
        final var b = Expressions.contradiction();
        final var c = Expressions.tautology();
        final var list = List.of(a, b, c);

        final var and = new AndExpression(list);
        final var or = new OrExpression(list);

        final var evaluator = new ShortCircuitEvaluator();

        System.out.println(new SimpleEvaluationFormatter().format(evaluator.evaluate(and)));
        System.out.println(new SimpleEvaluationFormatter().format(evaluator.evaluate(or)));
    }
}
