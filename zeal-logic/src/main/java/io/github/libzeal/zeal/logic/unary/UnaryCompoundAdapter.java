package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Rationale;
import io.github.libzeal.zeal.logic.evaluation.SimpleRationale;
import org.jspecify.annotations.Nullable;

import java.util.List;

record UnaryCompoundAdapter<T>(
        @Nullable T subject,
        CompoundExpression delegate,
        @Nullable String hint,
        @Nullable String negatedHint
) implements CompoundUnaryExpression<T> {

    UnaryCompoundAdapter(final @Nullable T subject, final CompoundExpression delegate) {
        this(subject, delegate, null, null);
    }

    @Override
    public String name() {
        return delegate.name();
    }

    @Override
    public List<Expression> children() {
        return delegate.children();
    }

    @Override
    public CompoundEvaluation evaluate() {

        final CompoundEvaluation evaluation = delegate.evaluate();

        if (hint == null && negatedHint == null) {
            return evaluation;
        }

        final Rationale rationale = evaluation.rationale();

        return new CompoundEvaluation(
                evaluation.isTrue(),
                evaluation.name(),
                new SimpleRationale(
                        rationale.expected(),
                        rationale.negatedExpected(),
                        rationale.actual(),
                        hint,
                        negatedHint
                ),
                evaluation.duration(),
                evaluation.directCause(),
                evaluation.children()
        );
    }
}
