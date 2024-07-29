package io.github.libzeal.zeal.expression.evaluation;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An {@link Evaluation} that does not contain children.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class TerminalEvaluation implements Evaluation {

    private final Result result;
    private final String name;
    private final Rationale rationale;

    /**
     * Creates a new terminal evaluated expression.
     *
     * @param result
     *     The desired result of the evaluated expression.
     * @param name
     *     The name of the evaluation that was performed.
     * @param rationale
     *     The rationale for the result of the evaluated expression.
     */
    public TerminalEvaluation(Result result, String name, Rationale rationale) {
        this.result = requireNonNull(result);
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
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
        return new ArrayList<>(0);
    }
}
