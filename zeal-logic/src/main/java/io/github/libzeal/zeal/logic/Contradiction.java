package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.*;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

/**
 * An expression that is always false.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
class Contradiction implements Expression {

    private static final String NAME = "Contradiction";
    private static final Evaluation EVALUATION = new ContradictoryEvaluation();

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
        return TerminalEvaluation.ofSkipped(name(), cause);
    }

    private static final class ContradictoryEvaluation implements Evaluation {

        public static final String FALSE = "false";

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
            return new SimpleRationale(FALSE, FALSE);
        }

        @Override
        public Cause rootCause() {
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
