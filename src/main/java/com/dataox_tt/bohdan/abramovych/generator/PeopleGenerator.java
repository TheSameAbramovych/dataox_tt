package com.dataox_tt.bohdan.abramovych.generator;

import com.dataox_tt.bohdan.abramovych.entity.People;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PeopleGenerator implements Generator<People, Integer> {

    int maxFloor;

    @Override
    public People generate(Integer currentFloor) {
        People people = new People(maxFloor);
        while (currentFloor != 0 && currentFloor == people.getDesiredFloorNumber()) {
            people = new People(maxFloor);
        }
        return people;
    }
}
