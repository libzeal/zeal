package io.github.libzeal.zeal.expression.evalulation;

@FunctionalInterface
public interface ActualValueComputer<T> {

    String compute(T actual);
}
