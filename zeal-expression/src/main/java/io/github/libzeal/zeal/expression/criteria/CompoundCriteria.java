package io.github.libzeal.zeal.expression.criteria;

public interface CompoundCriteria<T> extends Criteria<T> {

    void append(Criteria<T> criteria);
    void prepend(Criteria<T> criteria);
}
