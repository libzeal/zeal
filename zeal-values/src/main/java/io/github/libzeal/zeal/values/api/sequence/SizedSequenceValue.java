package io.github.libzeal.zeal.values.api.sequence;

public interface SizedSequenceValue<T, V extends SizedSequenceValue<T, V>> {

    V isEmpty();
    V isNotEmpty();
    V hasLengthOf(int length);
    V doesNotHaveLengthOf(int length);
    V isLongerThan(int length);
    V isLongerThanOrEqualTo(int length);
    V isShorterThan(int length);
    V isShorterThanOrEqualTo(int length);
}
