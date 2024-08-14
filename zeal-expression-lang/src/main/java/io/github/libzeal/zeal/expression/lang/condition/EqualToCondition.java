package io.github.libzeal.zeal.expression.lang.condition;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.rationale.RationaleGenerator;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationaleGenerator;
import io.github.libzeal.zeal.expression.lang.unary.TerminalUnaryExpression;

import java.util.Objects;

import static io.github.libzeal.zeal.expression.lang.util.Formatter.stringify;

class EqualToCondition<T> implements Condition<T> {

    private final T desired;

    public EqualToCondition(final T desired) {
        this.desired = desired;
    }

    @Override
    public Expression create(T subject) {

        final RationaleGenerator<T> generator = new SimpleRationaleGenerator<>(
            (s, passed) -> stringify(desired),
            (s, passed) -> stringify(s),
            (s, passed) -> "Subject should be equal to " + desired + " (using subject.equals(" + desired + "))"
        );

        return TerminalUnaryExpression.ofNullable(
            "isEqualTo[" + stringify(desired) + "]",
            subject,
            o -> Objects.equals(o, desired),
            generator
        );
    }
}
