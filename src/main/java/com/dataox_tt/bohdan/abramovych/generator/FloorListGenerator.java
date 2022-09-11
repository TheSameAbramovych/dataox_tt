package com.dataox_tt.bohdan.abramovych.generator;

import com.dataox_tt.bohdan.abramovych.entity.Floor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FloorListGenerator implements Generator<List<Floor>, PeopleListGenerator> {

    private int floorCount;

    @Override
    public List<Floor> generate(PeopleListGenerator peopleGenerator) {
        return IntStream.range(0, floorCount)
                .mapToObj(value -> {
                    int floor = getFloor(value);
                    return new Floor(floor, peopleGenerator.generate(floor));
                })
                .collect(Collectors.toList());
    }

    private int getFloor(int value) {
        return value + 1;
    }
}
