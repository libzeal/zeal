package io.github.libzeal.zeal.expression.operation.unary;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Rationale;
import io.github.libzeal.zeal.expression.operation.EvaluatedOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * An operation that is composed of any number of sub-operation. The nature of the evaluation depends on the nature of
 * the specific compound operation. For example, if the compound operation is conjunctive, then all sub-operation must
 * be true for the compound operation to be true; other implementations may have different behavior.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface CompoundUnaryOperation<T> extends UnaryOperation<T> {

    /**
     * Appends the supplied sub-operation to this compound operation.
     *
     * @param operation
     *     The operation to append.
     */
    void append(UnaryOperation<T> operation);

    /**
     * Prepends the supplied sub-operation to this compound operation.
     *
     * @param operation
     *     The operation to prepend.
     */
    void prepend(UnaryOperation<T> operation);

    /**
     * Obtains the children of the operation.
     *
     * @return The children of the operation.
     */
    List<UnaryOperation<T>> children();

    @Override
    default Evaluation evaluateAsSkipped(T subject) {

        final List<UnaryOperation<T>> children = children();
        final List<Evaluation> evaluatedChildren = new ArrayList<>(children.size());

        for (UnaryOperation<T> child : children) {
            evaluatedChildren.add(child.evaluateAsSkipped(subject));
        }

        return EvaluatedOperation.skipped(name(), Rationale::skipped, evaluatedChildren);
    }
}
