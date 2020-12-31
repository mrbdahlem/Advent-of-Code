package days.days1x.day17;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day17 extends AocDay {

    private final String[][] cells;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day17(String input, PrintStream output) {
        super(input, output);

        cells = Arrays.stream(input.split("\n"))
                    .map(s -> s.split(""))
                    .toArray(size -> new String[size][]);
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
        ConwayNSpace cc = new ConwayNSpace(3, cells);

        for (int i = 0; i < 6; i++) {
            cc.step();
        }

        // out.println(cc.numActive() + " active cells after 6 cycles.");
        return "" + cc.numActive();
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
        ConwayNSpace cc = new ConwayNSpace(4, cells);

        for (int i = 0; i < 6; i++) {
            cc.step();
        }

        // out.println(cc.numActive() + " active cells after 6 cycles.");
        return "" + cc.numActive();
    }
}