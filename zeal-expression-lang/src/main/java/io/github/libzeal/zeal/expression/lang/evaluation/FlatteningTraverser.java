package io.github.libzeal.zeal.expression.lang.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlatteningTraverser implements Traverser {

    private final List<Evaluation> evaluations;

    public FlatteningTraverser() {
        this.evaluations = new ArrayList<>();
    }

    @Override
    public void on(final Evaluation evaluation, final TraversalContext context) {
        evaluations.add(evaluation);
    }

    public List<Evaluation> evaluations() {
        return evaluations;
    }

    public List<Evaluation> children() {
        return evaluations.subList(1, evaluations.size());
    }

    public Optional<Evaluation> get(final int index) {
        return Optional.ofNullable(evaluations.get(index));
    }
}
