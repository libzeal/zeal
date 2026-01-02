package io.github.libzeal.zeal.values.collection;

import java.util.List;

public class ListValue<T> extends BaseCollectionValue<T, List<T>, ListValue<T>> {

    public ListValue(final List<T> subject) {
        super(subject, "List value");
    }

    @Override
    protected int findIndex(final List<T> haystack, final T needle) {
        return haystack.indexOf(needle);
    }
}
