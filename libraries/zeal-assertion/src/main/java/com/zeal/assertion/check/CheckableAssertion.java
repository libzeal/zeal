package com.zeal.assertion.check;

import java.util.function.Function;
import java.util.function.Supplier;

public interface CheckableAssertion {

    <T extends Throwable> void orThrow(Supplier<T> throwable) throws T;
}
