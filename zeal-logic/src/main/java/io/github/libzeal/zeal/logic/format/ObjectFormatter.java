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
        return switch (o) {
            case null -> "(null)";
            case Object[] a -> Arrays.deepToString(a);
            case int[] a -> Arrays.toString(a);
            case long[] a -> Arrays.toString(a);
            case double[] a -> Arrays.toString(a);
            case float[] a -> Arrays.toString(a);
            case short[] a -> Arrays.toString(a);
            case byte[] a -> Arrays.toString(a);
            case char[] a -> Arrays.toString(a);
            case boolean[] a -> Arrays.toString(a);
            default -> o.toString();
        };
    }
}
