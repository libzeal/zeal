package io.github.libzeal.zeal.expression.lang.condition;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.rationale.RationaleGenerator;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationaleGenerator;
import io.github.libzeal.zeal.expression.lang.unary.TerminalUnaryExpression;

import static io.github.libzeal.zeal.expression.lang.util.Formatter.stringify;
import static java.util.Objects.requireNonNull;

class ExactlyCondition<T> implements Condition<T> {

    private final T desired;
    private final String name;

    public ExactlyCondition(final T desired, final String name) {
        this.desired = desired;
        this.name = requireNonNull(name);
    }

    public ExactlyCondition(final T desired) {
        this(desired, "is[" + stringify(desired) + "]");
    }

    @Override
    public Expression create(T subject) {

        final RationaleGenerator<T> generator = new SimpleRationaleGenerator<>(
            (s, passed) -> stringify(desired),
            (s, passed) -> stringify(s),
            (s, passed) -> "Subject should be identical to " + desired + " (using ==)"
        );

        return TerminalUnaryExpression.ofNullable(
            name,
            subject,
            o -> o == desired,
            generator
        );
    }
}
