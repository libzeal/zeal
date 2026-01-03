package io.github.libzeal.zeal.values.api.cache;

import io.github.libzeal.zeal.logic.util.Formatter;

import static java.util.Objects.requireNonNull;

@FunctionalInterface
public interface CachedComputableField<T, C> {

    String compute(EvaluationContext<T, C> context);

    final class EvaluationContext<T, C> {

        private final T subject;
        private final boolean passed;
        private final C cache;

        EvaluationContext(final T subject, final boolean passed, final C cache) {
            this.subject = requireNonNull(subject);
            this.passed = passed;
            this.cache = requireNonNull(cache);
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

        public C cache() {
            return cache;
        }
    }
}
