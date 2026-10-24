package io.github.libzeal.zeal.logic.evaluation.traverse;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepthFirstTraverserTest {

    @Test
    void givenTerminal_whenTraverse_thenOnlyTerminalVisitedAtDepthZero() {

        final Evaluation terminal = TestExpressions.evaluation("terminal", Result.TRUE);

        assertEquals(List.of("terminal@0"), visit(terminal));
    }

    @Test
    void givenNestedCompound_whenTraverse_thenVisitedDepthFirstWithDepths() {

        final Evaluation evaluation = compound("root",
            compound("left",
                TestExpressions.evaluation("a", Result.TRUE),
                TestExpressions.evaluation("b", Result.TRUE)
            ),
            TestExpressions.evaluation("c", Result.TRUE)
        );

        assertEquals(List.of("root@0", "left@1", "a@2", "b@2", "c@1"), visit(evaluation));
    }

    @Test
    void givenCompoundWithoutChildren_whenTraverse_thenOnlyCompoundVisited() {
        assertEquals(List.of("empty@0"), visit(compound("empty")));
    }

    private static List<String> visit(final Evaluation evaluation) {

        final List<String> visited = new ArrayList<>();

        new DepthFirstTraverser().traverse(evaluation,
            (current, context) -> visited.add(current.name() + "@" + context.depth()));

        return visited;
    }

    private static Evaluation compound(final String name, final Evaluation... children) {
        return new CompoundEvaluation(Result.TRUE, name, new SimpleRationale("expected", "actual"), Duration.ZERO,
            Cause.self(), List.of(children));
    }
}
