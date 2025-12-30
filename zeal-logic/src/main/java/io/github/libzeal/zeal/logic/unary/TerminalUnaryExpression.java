package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.RationaleGenerator;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * A unary expression that does not contain children.
 *
 * @param <T>
 *     The type of the subject.
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
     * Creates a new unary expression.
     *
     * @param name
     *     The name of the expression.
     * @param subject
     *     The subject of the expression.
     * @param predicate
     *     The predicate for the expression. This predicate fails if the supplied subject is {@code null} (a
     *     non-nullable predicate).
     * @param rationaleGenerator
     *     A generator used to create a rationale for the expression when it is evaluated.
     * @param <S>
     *     The type of the subject.
     *
     * @return A unary expression without children.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments, other than the subject, are {@code null}.
     */
    public static <S> TerminalUnaryExpression<S> of(final String name, final S subject, final Predicate<S> predicate,
                                                    final RationaleGenerator<S> rationaleGenerator) {

        requireNonNull(predicate, "Predicate cannot be null");

        return new TerminalUnaryExpression<>(name, subject, predicate, rationaleGenerator);
    }

    private TerminalUnaryExpression(final String name, final T subject, final Predicate<T> predicate,
                                    final RationaleGenerator<T> rationaleGenerator) {
        this.name = requireNonNull(name);
        this.subject = subject;
        this.predicate = requireNonNull(predicate);
        this.rationaleGenerator = requireNonNull(rationaleGenerator);
    }

    public String name() {
        return name;
    }

    @Override
    public T subject() {
        return subject;
    }

    @Override
    public Expression expression() {
        return new Expression() {

            @Override
            public String name() {
                return name;
            }

            @Override
            public Evaluation evaluate() {

                final StopWatch stopWatch = StopWatch.started();
                final boolean passed = predicate.test(subject);
                final Rationale rationale = rationaleGenerator.generate(subject, passed);
                final Duration elapsedTime = stopWatch.stop();

                if (passed) {
                    return TerminalEvaluation.ofTrue(name, rationale, elapsedTime);
                }
                else {
                    return TerminalEvaluation.ofFalse(name, rationale, elapsedTime);
                }
            }
        };
    }
}
