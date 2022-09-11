package com.dataox_tt.bohdan.abramovych.generator;

import java.util.Map;

public class IntFromRangeGenerator implements Generator<Integer, Map.Entry<Integer, Integer>> {

    @Override
    public Integer generate(Map.Entry<Integer, Integer> minMaxParam) {
        assert minMaxParam.getKey() < minMaxParam.getValue();
        return (int) ((Math.random() * (minMaxParam.getValue() + 1 - minMaxParam.getKey())) + minMaxParam.getKey());
    }
}
