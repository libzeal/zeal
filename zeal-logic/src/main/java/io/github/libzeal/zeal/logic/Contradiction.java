package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.*;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;

/**
 * An expression that is always false.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
class Contradiction implements Expression {

    static final String NAME = "Contradiction";
    private static final Evaluation EVALUATION = new ContradictoryEvaluation();

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Evaluation evaluate() {
        return EVALUATION;
    }

    private static final class ContradictoryEvaluation implements Evaluation {

        private static final String FALSE = "false";

        @Override
        public Result result() {
            return Result.FALSE;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public Rationale rationale() {
            return new SimpleRationale(FALSE, FALSE);
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
