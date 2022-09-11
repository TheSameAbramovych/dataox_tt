package com.dataox_tt.bohdan.abramovych.generator;

import com.dataox_tt.bohdan.abramovych.entity.People;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PeopleListGenerator implements Generator<Set<People>, Integer> {

    int minSize;
    int maxSize;
    PeopleGenerator peopleGenerator;

    @Override
    public Set<People> generate(Integer currentFloor) {
        int peopleCount = (int) ((Math.random() * (maxSize + 1 - minSize)) + minSize);
        return IntStream.range(0, peopleCount)
                .mapToObj(value -> peopleGenerator.generate(currentFloor))
                .collect(Collectors.toSet());
    }
}
