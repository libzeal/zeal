package io.github.libzeal.zeal.values.api.sequence;

import io.github.libzeal.zeal.values.api.BaseObjectValue;

public interface SizedSequenceValue<T, V extends BaseObjectValue<T, V>> {

    V isEmpty();
    V isNotEmpty();
    V hasLengthOf(int length);
    V doesNotHaveLengthOf(int length);
    V isLongerThan(int length);
    V isLongerThanOrEqualTo(int length);
    V isShorterThan(int length);
    V isShorterThanOrEqualTo(int length);
}
