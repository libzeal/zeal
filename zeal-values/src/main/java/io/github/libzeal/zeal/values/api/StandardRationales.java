package io.github.libzeal.zeal.values.api;

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

    public static <T> String includes(final T value) {
        return "includes[" + stringify(value) + "]";
    }

    public static <T> String excludes(final T value) {
        return "excludes[" + stringify(value) + "]";
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
