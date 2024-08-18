package io.github.libzeal.zeal.logic.evaluation;

public class TraversalContext {

    private final int depth;

    private TraversalContext(final int depth) {
        this.depth = depth;
    }

    public TraversalContext withDepth(final int depth) {
        return new TraversalContext(depth);
    }

    public TraversalContext withIncrementedDepth() {
        return new TraversalContext(depth + 1);
    }

    public int depth () {
        return depth;
    }

    public static TraversalContext create() {
        return new TraversalContext(0);
    }
}
