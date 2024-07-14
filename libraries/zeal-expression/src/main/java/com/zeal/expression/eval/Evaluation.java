package com.zeal.expression.eval;

@FunctionalInterface
public interface Evaluation<T> {

    boolean evaluate(T subject);
}
