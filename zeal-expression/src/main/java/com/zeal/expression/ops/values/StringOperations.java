package com.zeal.expression.ops.values;

public final class StringOperations {

    private StringOperations() {}

    public static StringOperation each(StringOperation... ops) {
        return s -> {

            String applied = s;

            for (StringOperation op: ops) {
                applied = op.apply(applied);
            }

            return applied;
        };
    }

    public static StringOperation prepend(String prefix) {
        return s -> s == null ? prefix : prefix + s;
    }

    public static StringOperation append(String suffix) {
        return s -> s == null ? suffix : s + suffix;
    }

    public static StringOperation convertToUppercase() {
        return s -> s == null ? null : s.toUpperCase();
    }

    public static StringOperation convertToLowercase() {
        return s -> s == null ? null : s.toLowerCase();
    }
}
