package io.github.libzeal.zeal.values.api.sequence.builder;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class Element<T> {

    private final T value;
    private final boolean missing;

    public static <T> Element<T> missing() {
        return new Element<>(null, true);
    }

    public static <T> Element<T> found(final T value) {
        return new Element<>(value, false);
    }

    private Element(final T value, final boolean missing) {
        this.value = value;
        this.missing = missing;
    }

    public boolean isMissing() {
        return missing;
    }

    public boolean isPresent() {
        return !isMissing();
    }

    public T value() {
        return value;
    }

    public boolean matches(final T other) {
        return isPresent() && Objects.equals(value, other);
    }

    public <A> A isPresentOrElse(final A ifFound, final A orElse) {
        return isPresent() ? ifFound : orElse;
    }

    public <A> A ifPresentOrElseGet(final Function<T, A> ifFound, final Supplier<A> orElse) {
        return isPresent() ? ifFound.apply(value) : orElse.get();
    }
}
