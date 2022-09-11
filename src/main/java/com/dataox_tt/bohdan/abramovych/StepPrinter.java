package com.dataox_tt.bohdan.abramovych;

import com.dataox_tt.bohdan.abramovych.entity.Elevator;
import com.dataox_tt.bohdan.abramovych.entity.Floor;
import com.dataox_tt.bohdan.abramovych.entity.People;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StepPrinter {
    private static final String TEMPLATE = "%2d|%s %s |%s%n";

    public static void print(List<Floor> floors, Elevator elevator, int step) {
        String peopleString = getPeopleString(elevator.getPeople(), elevator.getSize());
        String whitespaceLine = getWhitespaceLine(peopleString.length());

        System.out.println("-----Step " + step + "-----");
        floors.stream()
                .sorted(Comparator.comparingInt(Floor::getNumber).reversed())
                .forEach(floor -> {
                    boolean elevatorFloor = isElevatorFloor(elevator, floor);
                    String vector = elevatorFloor ? elevator.getVector() > 0 ? "↑" : "↓" : " ";
                    String peopleInElevator = elevatorFloor ? peopleString : whitespaceLine;
                    String peopleInFloor = getPeopleString(floor.getPeople(), floor.getPeople().size());

                    System.out.printf(TEMPLATE, floor.getNumber(), vector, peopleInElevator, peopleInFloor);
                });
    }

    private static boolean isElevatorFloor(Elevator elevator, Floor floor) {
        return floor.getNumber() == elevator.getFloorNumber();
    }

    private static String getPeopleString(Set<People> people, int size) {
        List<People> peopleList = new ArrayList<>(people);
        return IntStream.range(0, size)
                .mapToObj(value -> value < people.size() ? Optional.ofNullable(peopleList.get(value))
                        .map(People::getDesiredFloorNumber)
                        .map(String::valueOf)
                        .orElse(" ") : " ")
                .collect(Collectors.joining(" "));
    }

    private static String getWhitespaceLine(int size) {
        return IntStream.range(0, size)
                .mapToObj(value -> " ")
                .collect(Collectors.joining(""));
    }
}
