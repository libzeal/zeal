package io.github.libzeal.zeal.logic.format;

import org.jspecify.annotations.Nullable;

/**
 * A utility class containing methods that format values.
 *
 * @author Justin Albano
 */
public class ObjectFormatter {

    private ObjectFormatter() {
    }

    /**
     * Formats an object as a string.
     *
     * @param o
     *         The object to format.
     *
     * @return A formatted object (as a string).
     */
    public static String stringify(@Nullable Object o) {
        return o == null ? "(null)" : o.toString();
    }
}
