package io.github.libzeal.zeal.logic.unary;

import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A helper class that wraps a value that was observed during an observation
 * created by a {@link ConditionBuilder#observing(Function)} call. This helper
 * class provides methods that clients can use to query whether the observed
 * value is missing (is {@code null}) and methods to handle when the observed
 * value is missing or present.
 *
 * @param <T>
 *         The type of the wrapped (observed) value.
 *
 * @author Justin Albano
 */
public class Observed<T extends @Nullable Object> {

    private final @Nullable T value;
    private final boolean missing;

    /**
     * Creates a new empty observed value (an observed value with a missing
     * wrapped value).
     *
     * @param <T>
     *         The type of the observed value.
     *
     * @return An empty observed value.
     */
    public static <T> Observed<T> missing() {
        return new Observed<>(null, true);
    }

    /**
     * Creates a new observed value populated with the supplied value.
     *
     * @param value
     *         The observed (wrapped) value.
     * @param <T>
     *         The type of the observed value.
     *
     * @return An observed value populated with the supplied value.
     */
    public static <T> Observed<T> found(final T value) {
        return new Observed<>(value, false);
    }

    private Observed(final @Nullable T value, final boolean missing) {
        this.value = value;
        this.missing = missing;
    }

    /**
     * Checks if the value is missing.
     *
     * @return True if the value is missing; false otherwise.
     */
    public boolean isMissing() {
        return missing;
    }

    /**
     * Checks if the value is present (not missing).
     *
     * @return True if the value is present; false otherwise.
     */
    public boolean isPresent() {
        return !isMissing();
    }

    /**
     * Obtains the observed value.
     *
     * @return An {@link Optional} populated with the observed value if the
     * observed value is present; empty {@link Optional} otherwise.
     */
    public Optional<T> value() {
        return Optional.ofNullable(value);
    }

    /**
     * Checks if the observed value matches the supplied value. If the observed
     * value is missing, this method will always return false (a missing value
     * cannot match any supplied value, including {@code null}).
     *
     * @param other
     *         The value to compare the observed value to.
     *
     * @return True if the observed value matches the supplied value; false
     * otherwise.
     */
    public boolean matches(final @Nullable T other) {
        return isPresent() && Objects.equals(value, other);
    }

    /**
     * Returns a value based on whether the observed value is present.
     *
     * @param ifFound
     *         The value to return if the observed value is present.
     * @param orElse
     *         The value to return if the observed value is missing.
     * @param <A>
     *         The type of the returned value (which may differ from the type of
     *         the observed value).
     *
     * @return The desired value based on whether the observed value is present
     * or missing.
     */
    public <A> A isPresentOrElse(final A ifFound, final A orElse) {
        return isPresent() ? ifFound : orElse;
    }

    /**
     * Returns a value based on whether the observed value is present.
     *
     * @param ifFound
     *         The function to transform the observed value if the observed
     *         value is present.
     * @param orElse
     *         The supplier used to create a default value if the observed value
     *         is missing.
     * @param <A>
     *         The type of the returned value (which may differ from the type of
     *         the observed value).
     *
     * @return The desired value based on whether the observed value is present
     * or missing.
     */
    // A present value is only null when it was found as null, which requires T to be nullable
    @SuppressWarnings("NullAway")
    public <A> A ifPresentOrElseGet(final Function<? super T, ? extends A> ifFound, final Supplier<? extends A> orElse) {
        return isPresent() ? ifFound.apply(value) : orElse.get();
    }
}
