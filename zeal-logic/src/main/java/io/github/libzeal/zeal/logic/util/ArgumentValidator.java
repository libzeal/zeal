package io.github.libzeal.zeal.logic.util;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A utility class containing methods that validate arguments.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class ArgumentValidator {

    private ArgumentValidator() {
    }

    /**
     * Asserts that the supplied list does not contain any {@code null} values.
     *
     * @param list
     *     The list to check.
     * @param <T>
     *     The type of the elements of the list.
     *
     * @return The supplied list if the list does not contain any {@code null} values.
     */
    public static <T> List<T> requireDoesNotContainNulls(final List<T> list) {

        requireNonNull(list);

        if (list.contains(null)) {
            throw new NullPointerException("List cannot contain nulls");
        }

        return list;
    }
}
