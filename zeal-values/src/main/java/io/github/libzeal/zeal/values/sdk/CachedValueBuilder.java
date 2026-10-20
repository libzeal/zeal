package io.github.libzeal.zeal.values.sdk;

import io.github.libzeal.zeal.logic.unary.cache.*;
import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;

public class CachedValueBuilder<T, C> implements ValueBuilder<T> {

    private final CacheablePredicate<T, C> predicate;
    private String name = "<unnamed>";
    private CachedComputableField<T, C> expected = context -> "<not set>";
    private CachedComputableField<T, C> actual = CachedComputableRationaleContext::stringifiedSubject;
    private CachedComputableField<T, C> hint = null;

    public static <T, C> CachedValueBuilder<T, C> of(final CacheablePredicate<T, C> predicate) {
        return new CachedValueBuilder<>(predicate);
    }

    private CachedValueBuilder(final CacheablePredicate<T, C> predicate) {
        this.predicate = predicate;
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

        return new CachedComputableExpression<>(name, predicate, computableRationale);
    }

}
