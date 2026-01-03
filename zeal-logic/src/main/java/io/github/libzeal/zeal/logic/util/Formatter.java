package io.github.libzeal.zeal.logic.util;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    /**
     * Formats all supplied objects as a string.
     *
     * @param o
     *     The objects to format.
     *
     * @since 0.5.0
     *
     * @return A list of formatted objects (as strings).
     */
    public static <T> List<String> stringifyAll(Collection<T> o) {
        return o.stream()
                .map(Formatter::stringify)
                .collect(toList());
    }
}
