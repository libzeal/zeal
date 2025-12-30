package io.github.libzeal.zeal.logic.evaluation;

import java.util.Optional;

public interface Reason {

    String expected();
    String actual();
    Optional<String> hint();
}
