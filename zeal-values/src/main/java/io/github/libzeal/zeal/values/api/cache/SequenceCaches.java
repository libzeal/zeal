package io.github.libzeal.zeal.values.api.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class SequenceCaches {

    private SequenceCaches() {
    }

    public static SizeCache size(final long size) {
        return new SizeCache(size);
    }

    public static IndexCache index(final int index) {
        return new IndexCache(index);
    }

    public static <T> ElementCache<T> element(final T element) {
        return new ElementCache<>(element);
    }

    public static <T> ElementCaches<T> found(final List<T> elements) {
        return new ElementCaches<>(elements);
    }

    public static <T> ElementCaches<T> nonFound() {
        return new ElementCaches<>(new ArrayList<>(0));
    }

    public static final class SizeCache {

        private final long size;

        private SizeCache(final long size) {
            this.size = size;
        }

        public long size() {
            return size;
        }
    }

    public static final class IndexCache {

        private final int index;

        private IndexCache(final int index) {
            this.index = index;
        }

        public int index() {
            return index;
        }
    }

    public static final class ElementCache<T> {

        private final T element;

        private ElementCache(final T element) {
            this.element = element;
        }

        public Element<T> element() {
            return new Element<>(element);
        }

        public static final class Element<T> {

            private final T element;

            private Element(final T element) {
                this.element = element;
            }

            public <A> A getOrElse(final A ifFound, final A orElse) {
                return element != null ? ifFound : orElse;
            }

            public <A> A getOrElse(final Supplier<A> ifFound, final Supplier<A> orElse) {
                return element != null ? ifFound.get() : orElse.get();
            }
        }
    }

    public static final class ElementCaches<T> {

        private final List<T> elements;

        private ElementCaches(final List<T> elements) {
            this.elements = elements;
        }

        public List<T> elements() {
            return elements;
        }
    }
}
