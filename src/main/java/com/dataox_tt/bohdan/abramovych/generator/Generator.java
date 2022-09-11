package com.dataox_tt.bohdan.abramovych.generator;

public interface Generator<T, R> {
    T generate(R param);
}
