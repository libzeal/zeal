package io.github.libzeal.zeal.values.api;

import java.util.Arrays;
import java.util.Collection;

import static io.github.libzeal.zeal.logic.util.Formatter.stringify;

public class StandardRationales {

    private StandardRationales() {
    }

    public enum Operators {

        EQ("="),
        NE("!="),
        GT(">"),
        GTE(">="),
        LT("<"),
        LTE("<=");

        public static final String SPACE = " ";
        private final String op;

        Operators(final String op) {
            this.op = op;
        }

        @Override
        public String toString() {
            return op;
        }

        public String symbol() {
            return op;
        }

        public String display(final String a, final String b) {
            return a + SPACE + op + SPACE + b;
        }

        public String display(final String a, final int b) {
            return a + SPACE + op + SPACE + b;
        }

        public String display(final String a, final long b) {
            return a + SPACE + op + SPACE + b;
        }

        public String display(final String a, final double b) {
            return a + SPACE + op + SPACE + b;
        }

        public String display(final String a, final float b) {
            return a + SPACE + op + SPACE + b;
        }
    }

    public static final class Names {

        private Names() {}

        public static String name(final String name) {
            return name;
        }

        public static String withValue(final String name, final Object value) {
            return name + "[" + stringify(value) + "]";
        }

        public static String withValues(final String name, final Collection<Object> values) {
            return name + values;
        }

        public static String withValues(final String name, final Object... values) {
            return name + Arrays.asList(values);
        }

        public static String withPairs(final String name, final Object key, final Object value) {
            return name + "[" + key + "=" + stringify(value) + "]";
        }

        public static String withPairs(final String name, final Object key1, final Object value1, final Object key2, final Object value2) {
            return name + "[" + key1 + "=" + stringify(value1) + ", " + key2 + "=" + stringify(value2) + "]";
        }
    }

    public static <T> String includes(final T value) {
        return Names.withValue("includes", value);
    }

    public static <T> String excludes(final T value) {
        return Names.withValue("excludes", value);
    }

    public static <T> String needleInHaystackHint(final int index, final T element) {

        if (index != -1) {
            return "'" + stringify(element) + "' found at index " + index;
        }
        else {
            return "Value does not include '" + stringify(element) + "'";
        }
    }
}
