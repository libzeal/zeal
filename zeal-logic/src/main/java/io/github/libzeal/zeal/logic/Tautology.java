package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.*;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;

/**
 * An expression that is always true.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
class Tautology implements Expression {

    static final String NAME = "Tautology";
    private static final Evaluation EVALUATION = new TautologicalEvaluation();

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Evaluation evaluate() {
        return EVALUATION;
    }

    @Override
    public Evaluation skip(final Cause cause) {
        return TerminalEvaluation.ofSkipped(name(), CauseGenerator.withUnderlyingCause(cause));
    }

    private static final class TautologicalEvaluation implements Evaluation {

        public static final String TRUE = "true";

        @Override
        public Result result() {
            return Result.TRUE;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public Rationale rationale() {
            return new SimpleRationale(TRUE, TRUE);
        }

        @Override
        public Duration elapsedTime() {
            return Duration.ZERO;
        }

        @Override
        public Cause cause() {
            return new Cause(this);
        }

        @Override
        public void traverseDepthFirst(final Traverser traverser) {
            traverseDepthFirst(traverser, TraversalContext.create());
        }

        @Override
        public void traverseDepthFirst(final Traverser traverser, final TraversalContext context) {

            if (traverser != null) {
                traverser.on(this, context);
            }
        }
    }
}
