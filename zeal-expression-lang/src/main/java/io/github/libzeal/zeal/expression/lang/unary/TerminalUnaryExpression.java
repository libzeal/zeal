package io.github.libzeal.zeal.expression.lang.unary;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.SimpleEvaluation;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;
import io.github.libzeal.zeal.expression.lang.rationale.RationaleGenerator;

import java.util.function.Predicate;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.TRUE;
import static java.util.Objects.requireNonNull;

/**
 * A unary expression that does not contain children.
 *
 * @param <T>
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class TerminalUnaryExpression<T> implements UnaryExpression<T> {

    private final String name;
    private final T subject;
    private final Predicate<T> predicate;
    private final RationaleGenerator<T> rationaleGenerator;

    /**
     * Creates an expression where the predicate fails if the supplied subject is {@code null} (a non-nullable
     * predicate).
     *
     * @param name
     *     The name of the expression.
     * @param subject
     *     The subject of the expression.
     * @param predicate
     *     The predicate for the expression. This predicate fails if the supplied subject is {@null} (a non-nullable
     *     predicate).
     * @param rationaleGenerator
     *     A generator used to create a rationale for the expression when it is evaluated.
     * @param <S>
     *     The type of the subject.
     *
     * @return A unary expression without children.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static <S> TerminalUnaryExpression<S> of(final String name, final S subject, final Predicate<S> predicate,
                                                    final RationaleGenerator<S> rationaleGenerator) {

        requireNonNull(predicate, "Predicate cannot be null");

        return new TerminalUnaryExpression<>(
            name,
            subject,
            predicate,
            rationaleGenerator
        );
    }

    /**
     * Creates an expression where the predicate can accept {@code null} values (a nullable predicate).
     *
     * @param name
     *     The name of the expression.
     * @param subject
     *     The subject of the expression.
     * @param predicate
     *     The predicate for the expression. This predicate accepts a {@code null} subject (a nullable predicate). Note
     *     that the predicate itself cannot be {@code null}.
     * @param rationaleGenerator
     *     A generator used to create a rationale for the expression when it is evaluated.
     * @param <S>
     *     The type of the subject.
     *
     * @return A unary expression without children.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static <S> TerminalUnaryExpression<S> ofNullable(final String name, final S subject,
                                                            final Predicate<S> predicate,
                                                            final RationaleGenerator<S> rationaleGenerator) {
        return new TerminalUnaryExpression<>(name, subject, predicate, rationaleGenerator);
    }

    private TerminalUnaryExpression(final String name, final T subject, final Predicate<T> predicate,
                                    final RationaleGenerator<T> rationaleGenerator) {
        this.name = requireNonNull(name);
        this.subject = subject;
        this.predicate = requireNonNull(predicate);
        this.rationaleGenerator = requireNonNull(rationaleGenerator);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Evaluation evaluate() {

        final boolean passed = predicate.test(subject);
        final Result result = passed ? TRUE : FALSE;
        final Rationale generate = rationaleGenerator.generate(subject, passed);

        return new SimpleEvaluation(name, result, generate);
    }

    @Override
    public T subject() {
        return subject;
    }
}
