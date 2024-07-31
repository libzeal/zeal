package io.github.libzeal.zeal.expression.operation.unary;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.operation.EvaluatedOperation;
import io.github.libzeal.zeal.expression.operation.RationaleGenerator;

import java.util.function.Predicate;

import static io.github.libzeal.zeal.expression.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.evaluation.Result.PASSED;
import static java.util.Objects.requireNonNull;

/**
 * A unary operation that does not contain children.
 *
 * @param <T>
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class TerminalUnaryOperation<T> implements UnaryOperation<T> {

    private final String name;
    private final Predicate<T> predicate;
    private final RationaleGenerator<T> rationaleGenerator;

    /**
     * Creates an operation where the predicate fails if the supplied subject is {@code null} (a non-nullable
     * predicate).
     *
     * @param name
     *     The name of the operation.
     * @param predicate
     *     The predicate for the operation. This predicate fails is the supplied subject is {@null} (a non-nullable
     *     predicate).
     * @param rationaleGenerator
     *     A generator used to create a rationale for the operation when it is evaluated.
     * @param <S>
     *     The type of the subject.
     *
     * @return A unary operation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static <S> TerminalUnaryOperation<S> of(String name, Predicate<S> predicate,
                                                   RationaleGenerator<S> rationaleGenerator) {
        return new TerminalUnaryOperation<>(
            name,
            s -> s != null && predicate.test(s),
            rationaleGenerator
        );
    }

    /**
     * Creates an operation where the predicate can accept {@code null} values (a nullable predicate).
     *
     * @param name
     *     The name of the operation.
     * @param predicate
     *     The predicate for the operation. This predicate accepts a {@code null} subject (a nullable predicate). Note
     *     that the predicate itself cannot be {@code null}.
     * @param rationaleGenerator
     *     A generator used to create a rationale for the operation when it is evaluated.
     * @param <S>
     *     The type of the subject.
     *
     * @return A unary operation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static <S> TerminalUnaryOperation<S> ofNullable(String name, Predicate<S> predicate, RationaleGenerator<S> rationaleGenerator) {
        return new TerminalUnaryOperation<>(name, predicate, rationaleGenerator);
    }

    private TerminalUnaryOperation(String name, Predicate<T> predicate, RationaleGenerator<T> rationaleGenerator) {
        this.name = requireNonNull(name);
        this.predicate = requireNonNull(predicate);
        this.rationaleGenerator = requireNonNull(rationaleGenerator);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Evaluation evaluate(final T subject) {
        return new EvaluatedOperation(
            name,
            predicate.test(subject) ? PASSED : FAILED,
            () -> rationaleGenerator.generate(subject)
        );
    }
}
