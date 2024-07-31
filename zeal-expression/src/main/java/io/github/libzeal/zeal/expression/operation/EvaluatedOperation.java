package io.github.libzeal.zeal.expression.operation;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Rationale;
import io.github.libzeal.zeal.expression.evaluation.Result;
import io.github.libzeal.zeal.expression.operation.unary.UnaryOperation;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * An {@link Evaluation} that resulted from an operation (such as {@link UnaryOperation}).
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class EvaluatedOperation implements Evaluation {

    private final String name;
    private final Result result;
    private final Supplier<Rationale> rationale;
    private final List<Evaluation> children;

    /**
     * Creates a skipped evaluation without any children.
     *
     * @param name
     *     The name of the operation that generated this evaluation.
     * @param rationale
     *     The rationale for the evaluation results. This rationale is supplied using a {@link Supplier} to ensure that
     *     the computation for the rationale is not performed unless the rationale is requested.
     *
     * @return A skipped evaluation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static EvaluatedOperation skipped(final String name, final Supplier<Rationale> rationale) {
        return EvaluatedOperation.skipped(name, rationale, Collections.emptyList());
    }

    /**
     * Creates a skipped evaluation.
     *
     * @param name
     *     The name of the operation that generated this evaluation.
     * @param rationale
     *     The rationale for the evaluation results. This rationale is supplied using a {@link Supplier} to ensure that
     *     the computation for the rationale is not performed unless the rationale is requested.
     * @param children
     *     The children of the evaluation.
     *
     * @return A skipped evaluation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static EvaluatedOperation skipped(final String name, final Supplier<Rationale> rationale,
                                             List<Evaluation> children) {
        return new EvaluatedOperation(name, Result.SKIPPED, rationale, children);
    }

    /**
     * Creates a new evaluation.
     *
     * @param name
     *     The name of the operation that generated this evaluation.
     * @param result
     *     The result of the evaluation.
     * @param rationale
     *     The rationale for the evaluation results. This rationale is supplied using a {@link Supplier} to ensure that
     *     the computation for the rationale is not performed unless the rationale is requested.
     * @param children
     *     The children of the evaluation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public EvaluatedOperation(final String name, final Result result, final Supplier<Rationale> rationale,
                              final List<Evaluation> children) {
        this.result = requireNonNull(result);
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
        this.children = requireNonNull(children);
    }

    /**
     * Creates a new evaluation without any children.
     *
     * @param name
     *     The name of the operation that generated this evaluation.
     * @param result
     *     The result of the evaluation.
     * @param rationale
     *     The rationale for the evaluation results. This rationale is supplied using a {@link Supplier} to ensure that
     *     the computation for the rationale is not performed unless the rationale is requested.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public EvaluatedOperation(final String name, final Result result, final Supplier<Rationale> rationale) {
        this(name, result, rationale, Collections.emptyList());
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
        return rationale.get();
    }

    @Override
    public List<Evaluation> children() {
        return children;
    }
}
