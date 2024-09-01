package io.github.libzeal.zeal.logic.util;

public class Formatter {

    private Formatter() {}

    public static String stringify(Object o) {
        return o == null ? "(null)" : o.toString();
    }
}
