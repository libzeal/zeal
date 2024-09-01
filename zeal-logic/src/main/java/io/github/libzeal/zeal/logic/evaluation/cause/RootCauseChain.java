package io.github.libzeal.zeal.logic.evaluation.cause;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class RootCauseChain implements Iterable<Cause> {

    static final int MAX_DEPTH = 1024;
    private static final int INITIAL_CAPACITY = 5;
    private final List<Cause> chain;

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

    public void append(final Cause cause) {

        final int existingIndex = chain.indexOf(cause);

        if (existingIndex != -1) {
            throw new CycleDetectedException(cause, existingIndex);
        }

        chain.add(cause);
    }

    public int length() {
        return chain.size();
    }

    Cause last() {
        return chain.get(chain.size() - 1);
    }

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
