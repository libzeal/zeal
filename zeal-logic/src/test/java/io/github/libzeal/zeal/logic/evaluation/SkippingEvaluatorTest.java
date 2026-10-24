package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.BooleanExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.NotExpression;
import io.github.libzeal.zeal.logic.OrExpression;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class SkippingEvaluatorTest {

    private static final Evaluation DECIDER = TerminalEvaluation.of(Result.FALSE, "decider",
        new SimpleRationale("expected", "actual"), Duration.ZERO);
    private SkippingEvaluator evaluator;
    private AtomicInteger evaluations;

    @BeforeEach
    void setUp() {
        evaluator = new SkippingEvaluator();
        evaluations = new AtomicInteger();
    }

    @Test
    void givenTerminalExpression_whenSkip_thenSkippedTerminalReturned() {

        final Evaluation evaluation = evaluator.skip(terminal("t"), DECIDER);

        assertEquals(TerminalEvaluation.skipped("t", DECIDER), evaluation);
        assertEquals(0, evaluations.get());
    }

    @Test
    void givenNotExpression_whenSkip_thenNotAndWrappedSkipped() {

        final Evaluation evaluation = evaluator.skip(new NotExpression("NOT", terminal("t")), DECIDER);

        final CompoundEvaluation expected = CompoundEvaluation.skipped("NOT", DECIDER,
            List.of(TerminalEvaluation.skipped("t", DECIDER)));

        assertEquals(expected, evaluation);
        assertEquals(0, evaluations.get());
    }

    @Test
    void givenAndExpression_whenSkip_thenAndAndChildrenSkipped() {

        final Expression expression = new AndExpression("AND", List.of(terminal("t1"), terminal("t2")));

        final Evaluation evaluation = evaluator.skip(expression, DECIDER);

        final CompoundEvaluation expected = CompoundEvaluation.skipped("AND", DECIDER, List.of(
            TerminalEvaluation.skipped("t1", DECIDER),
            TerminalEvaluation.skipped("t2", DECIDER)
        ));

        assertEquals(expected, evaluation);
        assertEquals(0, evaluations.get());
    }

    @Test
    void givenNestedExpression_whenSkip_thenEveryDescendantSkippedWithDecider() {

        final Expression expression = new OrExpression("OR", List.of(
            new NotExpression("NOT", new AndExpression("AND", List.of(terminal("t1")))),
            terminal("t2")
        ));

        final Evaluation evaluation = evaluator.skip(expression, DECIDER);

        final CompoundEvaluation expected = CompoundEvaluation.skipped("OR", DECIDER, List.of(
            CompoundEvaluation.skipped("NOT", DECIDER, List.of(
                CompoundEvaluation.skipped("AND", DECIDER, List.of(TerminalEvaluation.skipped("t1", DECIDER)))
            )),
            TerminalEvaluation.skipped("t2", DECIDER)
        ));

        assertEquals(expected, evaluation);
        assertEquals(0, evaluations.get());
    }

    private Expression terminal(final String name) {

        final BooleanSupplier counting = () -> {
            evaluations.incrementAndGet();
            return true;
        };

        return new BooleanExpression(name, counting);
    }
}
