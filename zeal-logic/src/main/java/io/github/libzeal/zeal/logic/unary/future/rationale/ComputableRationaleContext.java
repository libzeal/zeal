package io.github.libzeal.zeal.logic.unary.future.rationale;

import io.github.libzeal.zeal.logic.util.Formatter;

import java.util.function.Supplier;

public class ComputableRationaleContext<T> {

    private final T subject;
    private final boolean passed;

    public ComputableRationaleContext(final T subject, final boolean passed) {
        this.subject = subject;
        this.passed = passed;
    }

    public T subject() {
        return subject;
    }

    public String stringifiedSubject() {
        return Formatter.stringify(subject);
    }

    public boolean passed() {
        return passed;
    }

    public <A> A ifPassedOrElse(final A ifPassed, final A orElse) {
        return passed ? ifPassed : orElse;
    }

    public <A> A ifPassedOrElse(final Supplier<A> action, final Supplier<A> orElse) {

        if (passed) {
            return action.get();
        }
        else {
            return orElse.get();
        }
    }
}
