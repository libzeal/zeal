package com.zeal.expression;

import java.util.Optional;

public interface Explanation {

    String description();
    String expected();
    String actual();

    default Optional<String> hint() {
        return Optional.empty();
    }

    default boolean checksForNotNull() {
        return false;
    }
}
