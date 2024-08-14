package io.github.libzeal.zeal.expression.lang.evaluation;

import io.github.libzeal.zeal.expression.lang.rationale.Rationale;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An {@link Evaluation} that resulted from an expression..
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class SimpleEvaluation implements Evaluation {

    private final String name;
    private final Result result;
    private final Rationale rationale;
    private final List<Evaluation> children;

    public static SimpleEvaluation skipped(final String name, final Rationale rationale) {
        return SimpleEvaluation.skipped(name, rationale, Collections.emptyList());
    }

    public static SimpleEvaluation skipped(final String name, final Rationale rationale,
                                           List<Evaluation> children) {
        return new SimpleEvaluation(name, Result.SKIPPED, rationale, children);
    }

    public SimpleEvaluation(final String name, final Result result, final Rationale rationale) {
        this(name, result, rationale, Collections.emptyList());
    }

    public SimpleEvaluation(final String name, final Result result, final Rationale rationale,
                            final List<Evaluation> children) {
        this.result = requireNonNull(result);
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
        this.children = requireNonNull(children);
    }

    @Override
    public Result result() {
        return result;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Rationale rationale() {
        return rationale;
    }

    @Override
    public List<Evaluation> children() {
        return children;
    }

    @Override
    public Evaluation withName(final String name) {
        return new SimpleEvaluation(name, result(), rationale(), children());
    }
}
