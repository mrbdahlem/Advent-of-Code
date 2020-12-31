package days.days1x.day18;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day18 extends AocDay {
    
    private final String[] homework;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day18(String input, PrintStream output) {
        super(input, output);

        homework = input.split("\n");
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
        long total = 0;

        // evaluate each problem in the homework and add up the results
        for (String problem : homework) {
            total += Expression.eval(problem, false);
        }

        return "" + total;
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
        long total = Arrays.stream(homework)
                        .mapToLong(problem -> Expression.eval(problem, true))
                        .reduce(0, (acc, val) -> acc + val);
        return "" + total;
    }
}