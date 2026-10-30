package io.github.libzeal.zeal.values.sdk;

import java.util.Arrays;
import java.util.Collection;

import static io.github.libzeal.zeal.logic.format.ObjectFormatter.stringify;

/**
 * A standard set of rationales used by value during evaluation.
 *
 * @author Justin Albano
 */
public class StandardRationales {

    private StandardRationales() {
    }

    /**
     * A standard set of operators.
     */
    public enum Operators {

        /**
         * The equality operator.
         */
        EQ("="),

        /**
         * The not equals operator.
         */
        NE("!="),

        /**
         * The greater than operator.
         */
        GT(">"),

        /**
         * The greater than or equals operator.
         */
        GTE(">="),

        /**
         * The less than operator.
         */
        LT("<"),

        /**
         * The less than or equals operator.
         */
        LTE("<=");

        private static final String SPACE = " ";
        private final String op;

        Operators(final String op) {
            this.op = op;
        }

        @Override
        public String toString() {
            return op;
        }

        /**
         * Obtains the symbol of the operator (i.e., "==" for equality).
         *
         * @return The symbol of the operator.
         */
        public String symbol() {
            return op;
        }

        /**
         * Creates a display string by concatenating the supplied values with
         * this operator.
         *
         * @param a
         *         Left hand value.
         * @param b
         *         Right hand value.
         *
         * @return The symbol of the operator.
         */
        public String display(final String a, final String b) {
            return a + SPACE + op + SPACE + b;
        }

        /**
         * Creates a display string by concatenating the supplied values with
         * this operator.
         *
         * @param a
         *         Left hand value.
         * @param b
         *         Right hand value.
         *
         * @return The symbol of the operator.
         */
        public String display(final String a, final int b) {
            return a + SPACE + op + SPACE + b;
        }

        /**
         * Creates a display string by concatenating the supplied values with
         * this operator.
         *
         * @param a
         *         Left hand value.
         * @param b
         *         Right hand value.
         *
         * @return The symbol of the operator.
         */
        public String display(final String a, final long b) {
            return a + SPACE + op + SPACE + b;
        }

        /**
         * Creates a display string by concatenating the supplied values with
         * this operator.
         *
         * @param a
         *         Left hand value.
         * @param b
         *         Right hand value.
         *
         * @return The symbol of the operator.
         */
        public String display(final String a, final double b) {
            return a + SPACE + op + SPACE + b;
        }

        /**
         * Creates a display string by concatenating the supplied values with
         * this operator.
         *
         * @param a
         *         Left hand value.
         * @param b
         *         Right hand value.
         *
         * @return The symbol of the operator.
         */
        public String display(final String a, final float b) {
            return a + SPACE + op + SPACE + b;
        }
    }

    /**
     * A common set of names and operations (such as concatenation) used with
     * names.
     */
    public static final class Names {

        private Names() {
        }

        /**
         * Formats the supplied name.
         *
         * @param name
         *         The name to format.
         *
         * @return The formatted name.
         */
        public static String name(final String name) {
            return name;
        }

        /**
         * Formats the supplied name and value.
         *
         * @param name
         *         The name to format.
         * @param value
         *         The value to format.
         *
         * @return The formatted name and value.
         */
        public static String withValue(final String name, final Object value) {
            return name + "[" + stringify(value) + "]";
        }

        /**
         * Formats the supplied name and values.
         *
         * @param name
         *         The name to format.
         * @param values
         *         The values to format.
         *
         * @return The formatted name and values.
         */
        public static String withValues(
                final String name,
                final Collection<Object> values
        ) {
            return name + values;
        }

        /**
         * Formats the supplied name and values.
         *
         * @param name
         *         The name to format.
         * @param values
         *         The values to format.
         *
         * @return The formatted names.
         */
        public static String withValues(
                final String name,
                final Object... values
        ) {
            return name + Arrays.asList(values);
        }

        /**
         * Formats the supplied name and key-value pair.
         *
         * @param name
         *         The name to format.
         * @param key
         *         The key to format.
         * @param value
         *         The value to format.
         *
         * @return The formatted name and key-value pair.
         */
        public static String withPairs(
                final String name,
                final Object key,
                final Object value
        ) {
            return name + "[" + key + "=" + stringify(value) + "]";
        }

        /**
         * Formats the supplied name and key-value pairs.
         *
         * @param name
         *         The name to format.
         * @param key1
         *         The first key to format.
         * @param value1
         *         The first value to format.
         * @param key2
         *         The second key to format.
         * @param value2
         *         The second value to format.
         *
         * @return The formatted name and key-value pairs.
         */
        public static String withPairs(
                final String name,
                final Object key1,
                final Object value1,
                final Object key2,
                final Object value2
        ) {
            return name + "["
                    + key1 + "=" + stringify(value1) + ", "
                    + key2 + "=" + stringify(value2) + "]";
        }
    }

    /**
     * Formats a string to denote that the supplied value should be included.
     * For example, when creating the rationale for the evaluation of a
     * collection, this method formats an expected value, actual value, etc.
     * that denotes the supplied element should be included.
     *
     * @param value
     *         The value to include.
     * @param <T>
     *         The type of the included value.
     *
     * @return Formatted string denoting that the supplied value should be
     * included.
     */
    public static <T> String includes(final T value) {
        return Names.withValue("includes", value);
    }

    /**
     * Formats a string to denote that the supplied value should be excluded.
     * For example, when creating the rationale for the evaluation of a
     * collection, this method formats an expected value, actual value, etc.
     * that denotes the supplied element should be excluded.
     *
     * @param value
     *         The value to exclude.
     * @param <T>
     *         The type of the excluded value.
     *
     * @return Formatted string denoting that the supplied value should be
     * excluded.
     */
    public static <T> String excludes(final T value) {
        return Names.withValue("excludes", value);
    }

    /**
     * Formats a string that denotes that the supplied element should be found
     * at the supplied index (common with collection-based values).
     *
     * @param index
     *         The desired index.
     * @param element
     *         The desired element.
     * @param <T>
     *         The type of the desired element.
     *
     * @return Formatted string denoting that the supplied element should be
     * found at the supplied index.
     */
    public static <T> String needleInHaystackHint(final int index, final T element) {

        if (index != -1) {
            return "'" + stringify(element) + "' found at index " + index;
        }
        else {
            return "Value does not include '" + stringify(element) + "'";
        }
    }
}
