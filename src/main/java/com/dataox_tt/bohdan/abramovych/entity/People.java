package com.dataox_tt.bohdan.abramovych.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class People {
    int desiredFloorNumber;

    public People(int maxFloor) {
        desiredFloorNumber = (int) (Math.random() * maxFloor + 1);
    }
}
