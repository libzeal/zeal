package io.github.libzeal.zeal.logic.util;

/**
 * A utility class containing methods that format values.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class Formatter {

    private Formatter() {
    }

    /**
     * Formats an object as a string.
     *
     * @param o
     *     The object to format.
     *
     * @return A formatted object (as a string).
     */
    public static String stringify(Object o) {
        return o == null ? "(null)" : o.toString();
    }
}
