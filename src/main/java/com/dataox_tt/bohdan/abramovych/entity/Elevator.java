package com.dataox_tt.bohdan.abramovych.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Elevator {

    int size;
    Set<People> people;

    @NonFinal
    int floorNumber;
    @NonFinal
    int vector;


    public Elevator(int size) {
        this.size = size;
        this.vector = 1;
        this.floorNumber = 1;
        this.people = new HashSet<>(size);
    }

    public int getPeopleForFloor(int floorNumber) {
        List<People> peopleOnFloor = people.stream()
                .filter(p -> p.getDesiredFloorNumber() == floorNumber)
                .toList();
        peopleOnFloor.forEach(people::remove);
        return peopleOnFloor.size();
    }

    public int getFreePlace() {
        return size - people.size();
    }

    public void addPassengers(Set<People> peopleByVector) {
        people.addAll(peopleByVector);
        assert people.size() >= size;
    }

    public int getVector() {
        return Integer.signum(vector);
    }

    public void setVector(int vector) {
        this.vector = vector;
        assert getVector() != 0;
    }

    public void go() {
        floorNumber += getVector();
    }
}
