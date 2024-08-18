package io.github.libzeal.zeal.logic.condition;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.rationale.RationaleGenerator;
import io.github.libzeal.zeal.logic.rationale.SimpleRationaleGenerator;
import io.github.libzeal.zeal.logic.unary.TerminalUnaryExpression;

import static io.github.libzeal.zeal.logic.util.Formatter.stringify;
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
            (s, passed) -> "Actual should be identical to " + desired + " (using ==)"
        );

        return TerminalUnaryExpression.ofNullable(
            name,
            subject,
            o -> o == desired,
            generator
        );
    }
}
