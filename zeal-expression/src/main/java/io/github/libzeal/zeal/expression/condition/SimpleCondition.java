package io.github.libzeal.zeal.expression.condition;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class SimpleCondition<T> implements Condition<T> {

    private final String name;
    private final Predicate<T> predicate;

    public SimpleCondition(String name, Predicate<T> predicate) {
        this.name = requireNonNull(name);
        this.predicate = requireNonNull(predicate);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean test(T t) {
        return predicate.test(t);
    }

    @Override
    public String toString() {
        return "Condition[" + name() + "]";
    }
}
