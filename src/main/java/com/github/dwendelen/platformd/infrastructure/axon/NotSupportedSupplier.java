package com.github.dwendelen.platformd.infrastructure.axon;

import java.util.function.Supplier;

public class NotSupportedSupplier<T> implements Supplier<T> {
    public static <T> Supplier<T> create() {
        return new NotSupportedSupplier<>();
    }

    @Override
    public T get() {
        throw new UnsupportedOperationException();
    }
}
