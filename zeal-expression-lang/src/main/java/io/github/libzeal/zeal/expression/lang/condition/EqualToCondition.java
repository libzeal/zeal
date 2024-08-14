package io.github.libzeal.zeal.expression.lang.condition;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.rationale.Hint;
import io.github.libzeal.zeal.expression.lang.rationale.RationaleGenerator;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationaleGenerator;
import io.github.libzeal.zeal.expression.lang.unary.TerminalUnaryExpression;

import java.util.Objects;

import static io.github.libzeal.zeal.expression.lang.util.Formatter.stringify;
import static java.util.Objects.requireNonNull;

class EqualToCondition<T> implements Condition<T> {

    private final T desired;
    private final String name;

    public EqualToCondition(final T desired, final String name) {
        this.desired = desired;
        this.name = requireNonNull(name);
    }

    public EqualToCondition(final T desired) {
        this(desired, "isEqualTo[" + stringify(desired) + "]");
    }


    @Override
    public Expression create(T subject) {

        final RationaleGenerator<T> generator = new SimpleRationaleGenerator<>(
            (s, passed) -> stringify(desired),
            (s, passed) -> stringify(s),
            (s, passed) -> new Hint(
                "Subject should be equal to " + desired + " (using subject.equals(" + desired + "))",
                "Subject should not be equal to " + desired + " (using !subject.equals(" + desired + "))"
            )
        );

        return TerminalUnaryExpression.ofNullable(
            name,
            subject,
            o -> Objects.equals(o, desired),
            generator
        );
    }
}
