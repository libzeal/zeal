package io.github.libzeal.zeal.values.sdk.sequence;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public interface SequenceOperations<T, S> {

    int size(S haystack);

    boolean includes(S haystack, T needle);

    int occurrences(S haystack, T needle);

    default List<T> findAllIn(final S haystack, final Collection<T> needles) {
        return needles.stream()
                .filter(needle -> includes(haystack, needle))
                .collect(toList());
    }
}
