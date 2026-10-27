package io.github.libzeal.zeal.logic.evaluation.traverse;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.logic.test.NamedExpressions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class DepthFirstTraverserTest {

    @Test
    void givenTerminal_whenTraverse_thenOnlyTerminalVisitedAtDepthZero() {

        final Evaluation terminal = TestExpressions.evaluation("terminal", true);

        assertEquals(List.of("terminal@0"), visit(terminal));
    }

    @Test
    void givenNestedCompound_whenTraverse_thenVisitedDepthFirstWithDepths() {

        final Evaluation evaluation = compound("root",
            compound("left",
                TestExpressions.evaluation("a", true),
                TestExpressions.evaluation("b", true)
            ),
            TestExpressions.evaluation("c", true)
        );

        assertEquals(List.of("root@0", "left@1", "a@2", "b@2", "c@1"), visit(evaluation));
    }

    @Test
    void givenCompoundWithoutChildren_whenTraverse_thenOnlyCompoundVisited() {
        assertEquals(List.of("empty@0"), visit(compound("empty")));
    }

    @Test
    void givenSkippedTerminal_whenTraverse_thenSkippedVisitedInOrder() {

        final Evaluation decider = TestExpressions.evaluation("decider", false);
        final Evaluation evaluation = TestExpressions.compoundOf(false, "root", Cause.of(decider),
            new Evaluated(decider),
            new Skipped(t("skipped"), Cause.of(decider))
        );

        assertEquals(List.of("root@0", "decider@1", "skipped@1 (skipped)"), visit(evaluation));
    }

    @Test
    void givenSkippedCompound_whenTraverse_thenDescendantsVisitedAsSkippedWithInheritedCause() {

        final Evaluation decider = TestExpressions.evaluation("decider", false);
        final Cause.Delegated cause = Cause.of(decider);
        final Evaluation evaluation = TestExpressions.compoundOf(false, "root", cause,
            new Evaluated(decider),
            new Skipped(or("OR", t("t1"), not("NOT", t("t2"))), cause)
        );
        final List<Skipped> skipped = new ArrayList<>();

        assertEquals(
            List.of("root@0", "decider@1", "OR@1 (skipped)", "t1@2 (skipped)", "NOT@2 (skipped)", "t2@3 (skipped)"),
            visit(evaluation, skipped)
        );

        for (final Skipped descendant : skipped) {
            assertSame(cause, descendant.cause());
        }
    }

    private static List<String> visit(final Evaluation evaluation) {
        return visit(evaluation, new ArrayList<>());
    }

    private static List<String> visit(final Evaluation evaluation, final List<Skipped> skipped) {

        final List<String> visited = new ArrayList<>();

        new DepthFirstTraverser().traverse(evaluation, new TraverserAction() {

            @Override
            public void on(final Evaluation current, final TraversalContext context) {
                visited.add(current.name() + "@" + context.depth());
            }

            @Override
            public void onSkipped(final Skipped current, final TraversalContext context) {
                visited.add(current.name() + "@" + context.depth() + " (skipped)");
                skipped.add(current);
            }
        });

        return visited;
    }

    private static Evaluation compound(final String name, final Evaluation... children) {
        return TestExpressions.compound(true, name, Cause.self(), children);
    }
}
