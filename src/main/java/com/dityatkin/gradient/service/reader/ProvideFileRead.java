package com.dityatkin.gradient.service.reader;

public interface ProvideFileRead<T, U> {
    U read(T t);
}
