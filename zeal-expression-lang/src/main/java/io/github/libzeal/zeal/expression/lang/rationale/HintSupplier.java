package io.github.libzeal.zeal.expression.lang.rationale;

public interface HintSupplier<T> {

    Hint compute(T subject, boolean passed);
}
