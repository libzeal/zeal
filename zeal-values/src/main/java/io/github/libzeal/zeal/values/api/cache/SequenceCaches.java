package io.github.libzeal.zeal.values.api.cache;

public class SequenceCaches {

    private SequenceCaches() {
    }

    public static SizeCache size(final long size) {
        return new SizeCache(size);
    }

    public static IndexCache index(final int index) {
        return new IndexCache(index);
    }

    public static final class SizeCache {

        private final long size;

        private SizeCache(long size) {
            this.size = size;
        }

        public long size() {
            return size;
        }
    }

    public static final class IndexCache {

        private final int index;

        private IndexCache(int index) {
            this.index = index;
        }

        public int index() {
            return index;
        }
    }
}
