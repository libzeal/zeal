package io.github.libzeal.zeal.logic.util;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class ArgumentValidator {

    private ArgumentValidator() {
    }

    public static <T> List<T> requireDoesNotContainNulls(final List<T> list) {

        requireNonNull(list);

        if (list.contains(null)) {
            throw new NullPointerException("List cannot contain nulls");
        }

        return list;
    }
}
