package days.days0x.day6;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day6 extends AocDay {
    
    private final String[] allAnswers;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day6(String input, PrintStream output) {
        super(input, output);

        allAnswers = input.split("\n");
    }

    /**
     * Solve part 1 of the day's challenge using the prepared input (prepare
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    @Override
    public String part1() {

        List<Set<String>> groupAnswers = new ArrayList<>();
        Set<String> group = new HashSet<>();
        groupAnswers.add(group);
        for (String answers : allAnswers) {
            if (answers.isBlank()) {
                group = new HashSet<>();
                groupAnswers.add(group);
            }
            else {
                for (String answer : answers.split("")) {
                    group.add(answer);
                }
            }
        }

        int count = groupAnswers.stream()
                    .mapToInt(a->a.size())
                    .reduce(0, (a,b)->a + b);

        return "" + count;
    }

    /**
     * Solve part 2 of the day's challenge using the prepared input (prepare 
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    @Override
    public String part2() {

        List<Set<String>> groupAnswers = new ArrayList<>();

        Set<String> group = new HashSet<>();
        groupAnswers.add(group);
        boolean firstPerson = true;
        for (String answers : allAnswers) {
            if (answers.isBlank()) {
                group = new HashSet<>();
                groupAnswers.add(group);
                firstPerson = true;
            }
            else {
                Set<String> person = new HashSet<>();
                for (String answer : answers.split("")) {
                    person.add(answer);
                }
                if (firstPerson) {
                    group.addAll(person);
                }
                else {
                    group.retainAll(person);
                }
                firstPerson = false;
            }
        }


        int count = groupAnswers.stream()
                    .mapToInt(a->a.size())
                    .reduce(0, (a,b)->a + b);

        return "" + count;
    }
}