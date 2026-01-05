package io.github.libzeal.zeal.logic.evaluation.traverse;

/**
 * The context used for {@link DepthFirstTraverser} traverses a traversable evaluation.
 *
 * @author Justin Albano
 */
public class TraversalContext {

    private final int depth;

    /**
     * Creates a new context with the supplied initial depth.
     *
     * @param depth
     *     The initial depth.
     */
    private TraversalContext(final int depth) {

        if (depth < 0) {
            throw new IllegalArgumentException("Depth cannot be negative");
        }

        this.depth = depth;
    }

    /**
     * Creates a new context with the supplied initial depth.
     *
     * @param depth
     *     The initial depth.
     *
     * @return A context with the supplied initial depth.
     *
     * @throws IllegalArgumentException
     *     The supplied depth is negative.
     */
    public static TraversalContext withDepth(final int depth) {
        return new TraversalContext(depth);
    }

    /**
     * Creates a context with an initial depth of 0.
     *
     * @return A context with an initial depth of 0.
     */
    public static TraversalContext create() {
        return new TraversalContext(0);
    }

    /**
     * Creates a new context with a depth one larger than the current depth.
     *
     * @return A context with an incremented depth.
     */
    public TraversalContext withIncrementedDepth() {
        return new TraversalContext(depth + 1);
    }

    /**
     * The current depth.
     *
     * @return The current depth.
     */
    public int depth() {
        return depth;
    }
}
