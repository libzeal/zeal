package io.github.libzeal.zeal.values.api.cache;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.EvaluatedTerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;
import io.github.libzeal.zeal.logic.util.StopWatch;
import io.github.libzeal.zeal.values.api.ValueBuilder;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

public class CachedValueBuilder<T, C> implements ValueBuilder<T> {

    private final CacheablePredicate<T, C> test;
    private String name = "<unnamed>";
    private CachedComputableField<T, C> expected = context -> "<not set>";
    private CachedComputableField<T, C> actual = CachedComputableRationaleContext::stringifiedSubject;
    private CachedComputableField<T, C> hint = null;

    public static <T, C> CachedValueBuilder<T, C> of(final CacheablePredicate<T, C> test) {
        return new CachedValueBuilder<>(test);
    }

    private CachedValueBuilder(final CacheablePredicate<T, C> test) {
        this.test = test;
    }

    /**
     * Sets the name of the evaluation.
     *
     * @param name
     *         The name of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public CachedValueBuilder<T, C> name(final String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *         The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public CachedValueBuilder<T, C> expected(final CachedComputableField<T, C> expected) {
        this.expected = expected;
        return this;
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *         The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public CachedValueBuilder<T, C> expected(final String expected) {
        return expected(context -> expected);
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *         The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public CachedValueBuilder<T, C> expected(final long expected) {
        return expected(context -> String.valueOf(expected));
    }

    /**
     * Sets the actual valued of the evaluation.
     *
     * @param actual
     *         The actual value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public CachedValueBuilder<T, C> actual(final CachedComputableField<T, C> actual) {
        this.actual = actual;
        return this;
    }

    /**
     * Sets the actual valued of the evaluation.
     *
     * @param actual
     *         The actual value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public CachedValueBuilder<T, C> actual(final String actual) {
        return actual(context -> actual);
    }

    /**
     * Sets the hint for the evaluation.
     *
     * @param hint
     *         The hint for the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public CachedValueBuilder<T, C> hint(final CachedComputableField<T, C> hint) {
        this.hint = hint;
        return this;
    }

    /**
     * Sets the hint for the evaluation.
     *
     * @param hint
     *         The hint for the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public CachedValueBuilder<T, C> hint(final String hint) {
        return hint(context -> hint);
    }

    @Override
    public ComputableExpression<T> build() {

        final CachedComputableRationale<T, C> computableRationale = new CachedComputableRationale<>(expected, actual, hint);

        return new CachedComputableExpression<>(name, test, computableRationale);
    }

    private static class CachedComputableExpression<T, C> implements ComputableExpression<T> {

        private final String name;
        private final CacheablePredicate<T, C> predicate;
        private final CachedComputableRationale<T, C> computableRationale;

        public CachedComputableExpression(final String name, final CacheablePredicate<T, C> predicate,
                                          final CachedComputableRationale<T, C> computableRationale) {
            this.name = requireNonNull(name);
            this.predicate = requireNonNull(predicate);
            this.computableRationale = requireNonNull(computableRationale);
        }

        @Override
        public Expression compute(T subject) {
            return new CachedExpression<>(name, subject, predicate, computableRationale);
        }
    }

    private static class CachedExpression<T, C> implements Expression {

        private final String name;
        private final T subject;
        private final CacheablePredicate<T, C> predicate;
        private final CachedComputableRationale<T, C> computableRationale;

        public CachedExpression(final String name, final T subject, final CacheablePredicate<T, C> predicate,
                                final CachedComputableRationale<T, C> computableRationale) {
            this.name = requireNonNull(name);
            this.subject = subject;
            this.predicate = requireNonNull(predicate);
            this.computableRationale = requireNonNull(computableRationale);
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public Evaluation evaluate() {

            final StopWatch stopWatch = StopWatch.started();
            final CacheableResult<C> predicateResult = predicate.apply(subject);
            final boolean passed = predicateResult.passed();
            final C cache = predicateResult.cache();
            final Duration elapsedTime = stopWatch.stop();
            final CachedComputableRationaleContext<T, C> context = new CachedComputableRationaleContext<>(subject, passed, cache);

            final Rationale rationale = computableRationale.compute(context);
            final Result result = Result.from(passed);

            return EvaluatedTerminalEvaluation.of(result, name, rationale, elapsedTime);
        }
    }

    private static class CachedComputableRationale<T, C> {

        private final CachedComputableField<T, C> expected;
        private final CachedComputableField<T, C> actual;
        private final CachedComputableField<T, C> hint;

        public CachedComputableRationale(final CachedComputableField<T, C> expected,
                                         final CachedComputableField<T, C> actual,
                                         final CachedComputableField<T, C> hint) {
            this.expected = requireNonNull(expected);
            this.actual = requireNonNull(actual);
            this.hint = hint;
        }

        public Rationale compute(final CachedComputableRationaleContext<T, C> context) {

            final String generatedExpected = expected.compute(context);
            final String generatedActual = actual.compute(context);
            final String generatedHint = hint == null ? null : hint.compute(context);

            return new SimpleRationale(generatedExpected, generatedActual, generatedHint);
        }
    }
}
