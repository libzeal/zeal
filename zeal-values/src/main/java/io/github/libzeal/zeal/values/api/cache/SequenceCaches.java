package io.github.libzeal.zeal.values.api.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class SequenceCaches {

    private SequenceCaches() {
    }

    public static LongCache size(final long size) {
        return new LongCache(size);
    }

    public static LongCache count(final long size) {
        return new LongCache(size);
    }

    public static IntCache index(final int index) {
        return new IntCache(index);
    }

    public static <T> ObjectCache<T> element(final T element) {
        return new ObjectCache<>(element);
    }

    public static <T> ListCache<T> found(final List<T> elements) {
        return new ListCache<>(elements);
    }

    public static <T> ListCache<T> nonFound() {
        return new ListCache<>(new ArrayList<>(0));
    }

    public static final class LongCache {

        private final long value;

        private LongCache(final long value) {
            this.value = value;
        }

        public long value() {
            return value;
        }
    }

    public static final class IntCache {

        private final int value;

        private IntCache(final int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public static final class ObjectCache<T> {

        private final T value;

        private ObjectCache(final T value) {
            this.value = value;
        }

        public Value<T> value() {
            return new Value<>(value);
        }

        public static final class Value<T> {

            private final T value;

            private Value(final T value) {
                this.value = value;
            }

            public <A> A getOrElse(final A ifFound, final A orElse) {
                return value != null ? ifFound : orElse;
            }

            public <A> A getOrElseGet(final Function<T, A> ifFound, final Supplier<A> orElse) {
                return value != null ? ifFound.apply(value) : orElse.get();
            }
        }
    }

    public static final class ListCache<T> {

        private final List<T> values;

        private ListCache(final List<T> values) {
            this.values = values;
        }

        public List<T> value() {
            return values;
        }
    }
}
