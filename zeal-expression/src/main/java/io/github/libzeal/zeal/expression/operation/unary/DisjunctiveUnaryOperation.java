package io.github.libzeal.zeal.expression.operation.unary;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Rationale;
import io.github.libzeal.zeal.expression.evaluation.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static io.github.libzeal.zeal.expression.evaluation.Result.*;
import static io.github.libzeal.zeal.expression.operation.unary.SkippableCompoundUnaryOperationEvaluator.EvaluationContext;
import static java.util.Objects.requireNonNull;

/**
 * A compound operation where at least one sub-operations must be pass for this operation to pass.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class DisjunctiveUnaryOperation<T> implements CompoundUnaryOperation<T> {

    private final String name;
    private final List<UnaryOperation<T>> operations;

    /**
     * Creates a new disjunctive criteria.
     *
     * @param name
     *     The name of the operation.
     * @param operations
     *     The sub-criteria used initialize the compound operation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public DisjunctiveUnaryOperation(String name, List<UnaryOperation<T>> operations) {
        this.name = requireNonNull(name);
        this.operations = requireNonNull(operations);
    }

    /**
     * Creates a new disjunctive operation with an empty default sub-operation list.
     *
     * @param name
     *     The name of the operation.
     *
     * @implNote The default sub-operation list has an initial capacity of 1.
     */
    public DisjunctiveUnaryOperation(String name) {
        this(name, new ArrayList<>(1));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void append(UnaryOperation<T> operation) {
        this.operations.add(operation);
    }

    @Override
    public void prepend(UnaryOperation<T> operation) {
        this.operations.add(0, operation);
    }

    @Override
    public List<UnaryOperation<T>> children() {
        return operations;
    }

    @Override
    public Evaluation evaluate(final T subject) {

        final SkippableCompoundUnaryOperationEvaluator<T> evaluator = new SkippableCompoundUnaryOperationEvaluator<>(
            name,
            (passed, failed, skipped, total) -> passed > 0,
            this::computeResult,
            this::computeRationale
        );

        return evaluator.evaluate(operations, subject);
    }

    private Result computeResult(final EvaluationContext<T> context) {

        if (context.total() == 0) {
            return PASSED;
        }
        else if (context.passed() > 0) {
            return PASSED;
        }
        else if (context.failed() > 0 && context.failed() == context.total()) {
            return FAILED;
        }
        else {
            return SKIPPED;
        }
    }

    private Supplier<Rationale> computeRationale(final EvaluationContext<T> context) {
        return () -> {

            final String expected = "At least one child must pass";
            final String actual =
                "Passed: " + context.passed() + ", Failed: " + context.failed() + ", Skipped: " + context.skipped();

            return new Rationale(expected, actual);
        };
    }
}
