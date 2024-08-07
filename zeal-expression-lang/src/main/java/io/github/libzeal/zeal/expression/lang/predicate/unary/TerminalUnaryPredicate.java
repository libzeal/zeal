package io.github.libzeal.zeal.expression.lang.predicate.unary;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Rationale;
import io.github.libzeal.zeal.expression.lang.predicate.EvaluatedPredicate;

import java.util.function.Predicate;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;
import static java.util.Objects.requireNonNull;

/**
 * A unary operation that does not contain children.
 *
 * @param <T>
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class TerminalUnaryPredicate<T> implements UnaryPredicate<T> {

    private final String name;
    private final Predicate<T> predicate;
    private final Rationale rationale;

    /**
     * Creates an operation where the predicate fails if the supplied subject is {@code null} (a non-nullable
     * predicate).
     *
     * @param name
     *     The name of the operation.
     * @param predicate
     *     The predicate for the operation. This predicate fails is the supplied subject is {@null} (a non-nullable
     *     predicate).
     * @param rationale
     *     A generator used to create a rationale for the operation when it is evaluated.
     * @param <S>
     *     The type of the subject.
     *
     * @return A unary operation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static <S> TerminalUnaryPredicate<S> of(String name, Predicate<S> predicate,
                                                   Rationale rationale) {
        return new TerminalUnaryPredicate<>(
            name,
            s -> s != null && predicate.test(s),
            rationale
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
     * @param rationale
     *     A generator used to create a rationale for the operation when it is evaluated.
     * @param <S>
     *     The type of the subject.
     *
     * @return A unary operation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static <S> TerminalUnaryPredicate<S> ofNullable(String name, Predicate<S> predicate, Rationale rationale) {
        return new TerminalUnaryPredicate<>(name, predicate, rationale);
    }

    private TerminalUnaryPredicate(String name, Predicate<T> predicate, Rationale rationale) {
        this.name = requireNonNull(name);
        this.predicate = requireNonNull(predicate);
        this.rationale = requireNonNull(rationale);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Evaluation evaluate(final T subject) {
        return new EvaluatedPredicate(
            name,
            predicate.test(subject) ? PASSED : FAILED,
            rationale
        );
    }
}
