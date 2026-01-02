package io.github.libzeal.zeal.values.api;

import static io.github.libzeal.zeal.logic.util.Formatter.stringify;

public class CommonRationale {

    private CommonRationale() {
    }

    public static <T> String includes(final T value) {
        return "includes[" + stringify(value) + "]";
    }

    public static <T> String excludes(final T value) {
        return "excludes[" + stringify(value) + "]";
    }

    public static <T> String needleInHaystackHint(final String type, final int index, final T element) {

        if (index != -1) {
            return "'" + stringify(element) + "' found at index " + index;
        }
        else {
            return type + " does not include '" + stringify(element) + "'";
        }
    }
}
