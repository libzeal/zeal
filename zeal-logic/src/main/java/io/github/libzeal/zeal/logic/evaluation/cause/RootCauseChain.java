package io.github.libzeal.zeal.logic.evaluation.cause;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * A chain of causes. This chain is useful when finding a root cause for an evaluation.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class RootCauseChain implements Iterable<Cause> {

    static final int MAX_DEPTH = 1024;
    private static final int INITIAL_CAPACITY = 5;
    private final List<Cause> chain;

    /**
     * A chain with one element populated with the supplied cause.
     *
     * @param cause
     *     The starting cause.
     *
     * @return A new chain with the supplied cause as the only element.
     */
    public static RootCauseChain with(final Cause cause) {
        return new RootCauseChain(cause);
    }

    private RootCauseChain(final Cause cause) {
        this(listOf(cause));
    }

    private static List<Cause> listOf(final Cause cause) {

        final List<Cause> list = new ArrayList<>(INITIAL_CAPACITY);

        list.add(cause);

        return list;
    }

    /**
     * Creates a new chain populated with the supplied causes.
     *
     * @param chain
     *     The initial causes in the chain. The supplied causes are added in order (the first element in the supplied
     *     list is used as the first element in the chain, the second element in the list is used as the second element
     *     in the chain, etc.).
     */
    public RootCauseChain(final List<Cause> chain) {
        this.chain = validate(chain);
    }

    private static List<Cause> validate(final List<Cause> chain) {

        requireNonNull(chain, "Chain must not be null");

        if (chain.isEmpty()) {
            throw new IllegalArgumentException("Chain must contain at least one cause");
        }
        else if (chain.size() > MAX_DEPTH) {
            throw new IllegalArgumentException("Chain is longer than the maximum depth: " + MAX_DEPTH);
        }

        return chain;
    }

    /**
     * Appends the supplied cause to the cause.
     *
     * @param cause
     *     The cause to append.
     *
     * @throws CycleDetectedException
     *     The supplied cause already exists in the chain. Note: the supplied cause is <em>not</em> added to the chain
     *     if a cycle is detected.
     */
    public void append(final Cause cause) {

        if (chain.size() >= MAX_DEPTH) {
            throw new MaximumDepthExceededException(MAX_DEPTH);
        }

        final int existingIndex = chain.indexOf(cause);

        if (existingIndex != -1) {
            throw new CycleDetectedException(cause, existingIndex);
        }

        chain.add(cause);
    }

    /**
     * Obtains the number of elements in the chain.
     *
     * @return The number of elements in the chain.
     */
    public int length() {
        return chain.size();
    }

    /**
     * Obtains the last element in the chain.
     *
     * @return The last element in the chain.
     */
    Cause last() {
        return chain.get(chain.size() - 1);
    }

    /**
     * Obtains the root cause of the chain (the last element in the chain).
     *
     * @return The root cause of the chain.
     */
    public Cause rootCause() {
        return last();
    }

    @Override
    public Iterator<Cause> iterator() {
        return chain.iterator();
    }

    @Override
    public void forEach(final Consumer<? super Cause> action) {
        chain.forEach(action);
    }

    @Override
    public Spliterator<Cause> spliterator() {
        return chain.spliterator();
    }

    /**
     * Creates a stream of causes from this chain. The order of elements in the stream matches the order of the elements
     * in the chain.
     *
     * @return A stream of the causes in this chain.
     */
    public Stream<Cause> stream() {
        return chain.stream();
    }

    @Override
    public String toString() {

        final List<String> chainString = chain.stream()
            .map(Cause::toString)
            .collect(Collectors.toList());

        return String.join(" -> ", chainString);
    }
}
