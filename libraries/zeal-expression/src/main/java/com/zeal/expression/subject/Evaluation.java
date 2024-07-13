package com.zeal.expression.subject;

@FunctionalInterface
public interface Evaluation<T> {

    boolean evaluate(T subject);
}
