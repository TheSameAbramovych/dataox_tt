package com.dataox_tt.bohdan.abramovych.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Floor {
    int number;
    Set<People> people;

    public void addPeople(List<People> newPeople) {
        people.addAll(newPeople);
    }

    public Set<People> getPeopleForElevator(int vector, int freePlace) {
        Set<People> peopleForElevator = people.stream()
                .filter(p -> Integer.signum(p.getDesiredFloorNumber() - number) == vector)
                .limit(freePlace)
                .collect(Collectors.toSet());
        peopleForElevator.forEach(people::remove);
        return peopleForElevator;
    }

    public long countPeopleByVector(int vector) {
        return people.stream()
                .filter(people -> Integer.signum(people.getDesiredFloorNumber() - number) == vector)
                .count();
    }
}
