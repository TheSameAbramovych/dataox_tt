package com.dataox_tt.bohdan.abramovych;

import com.dataox_tt.bohdan.abramovych.entity.Elevator;
import com.dataox_tt.bohdan.abramovych.entity.Floor;
import com.dataox_tt.bohdan.abramovych.entity.People;
import com.dataox_tt.bohdan.abramovych.generator.FloorListGenerator;
import com.dataox_tt.bohdan.abramovych.generator.IntFromRangeGenerator;
import com.dataox_tt.bohdan.abramovych.generator.PeopleGenerator;
import com.dataox_tt.bohdan.abramovych.generator.PeopleListGenerator;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

    public static final int MAX_FLOOR = 20;
    public static final int MIN_FLOOR = 5;
    public static final int MIN_PEOPLE = 0;
    public static final int MAX_PEOPLE = 10;

    @SneakyThrows
    public static void main(String[] args) {
        printLegend();

        Elevator elevator = new Elevator(5);

        IntFromRangeGenerator intFromRangeGenerator = new IntFromRangeGenerator();
        Integer maxFloorNumber = intFromRangeGenerator.generate(Map.entry(MIN_FLOOR, MAX_FLOOR));

        PeopleGenerator peopleGenerator = new PeopleGenerator(maxFloorNumber);
        List<Floor> floors = new FloorListGenerator(maxFloorNumber)
                .generate(new PeopleListGenerator(MIN_PEOPLE, MAX_PEOPLE, peopleGenerator));

        int step = 0;
        StepPrinter.print(floors, elevator, ++step);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in), 1);
        while (reader.readLine().isBlank()) {

            Floor floor = floors.get(elevator.getFloorNumber() - 1);
            int peopleLeave = elevator.getPeopleForFloor(floor.getNumber());

            Set<People> peopleByVector = getPeopleForElevator(floor, elevator);
            elevator.addPassengers(peopleByVector);

            floor.addPeople(generateNewPeopleOnFloor(floor, peopleLeave, peopleGenerator));
            elevator.setVector(getVector(floor.getNumber(), floors.size(), elevator));

            StepPrinter.print(floors, elevator, ++step);
            elevator.go();
        };

    }

    private static int getVector(int currentFloor, int maxFloor, Elevator elevator) {
        if (currentFloor == maxFloor) {
            return -1;
        }
        if (currentFloor == 1) {
            return 1;
        }
        OptionalInt targetFloor = elevator.getPeople().stream()
                .mapToInt(People::getDesiredFloorNumber)
                .map(floor -> floor * elevator.getVector())
                .max();

        return targetFloor.isPresent() ?
                Integer.signum(Math.abs(targetFloor.getAsInt()) - currentFloor) :
                elevator.getVector();
    }

    private static List<People> generateNewPeopleOnFloor(Floor floor, int peopleLeave, PeopleGenerator peopleGenerator) {
        return IntStream.range(0, peopleLeave)
                .mapToObj(value -> peopleGenerator.generate(floor.getNumber()))
                .toList();
    }

    private static Set<People> getPeopleForElevator(Floor floor, Elevator elevator) {
        int vector = elevator.getVector();
        int freePlace = elevator.getFreePlace();

        if (elevator.getSize() - freePlace == 0) {
            long peopleByVectorUp = floor.countPeopleByVector(vector);
            long peopleByVectorDown = floor.countPeopleByVector(-vector);
            if (peopleByVectorDown > peopleByVectorUp) {
                return floor.getPeopleForElevator(-vector, freePlace);
            }
        }

        return floor.getPeopleForElevator(vector, freePlace);
    }

    private static void printLegend() {
        System.out.println("Press Enter key to next step.");
        System.out.println("Enter any symbol to exit!");
    }
}
