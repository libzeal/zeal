package io.github.libzeal.zeal.logic.format;

import org.jspecify.annotations.Nullable;

import java.util.Arrays;

/**
 * A utility class containing methods that format values.
 *
 * @author Justin Albano
 */
public final class ObjectFormatter {

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

        if (o == null) {
            return "(null)";
        }
        else if (o.getClass().isArray()) {
            // Wrapping the array allows deepToString to format both object
            // and primitive arrays (including nested arrays)
            final String wrapped = Arrays.deepToString(new Object[]{o});
            return wrapped.substring(1, wrapped.length() - 1);
        }

        return o.toString();
    }
}
