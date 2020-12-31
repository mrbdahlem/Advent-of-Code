package days.days1x.day19;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day19  extends AocDay {
    private final String[] data;
    private final String[] rules;
    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day19(String input, PrintStream output) {
        super(input, output);

        String[] d = input.split("\n\n");
        rules = d[0].split("\n");
        data = d[1].split("\n");
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
        Matcher m = new Matcher(rules);
        long count = Arrays.stream(data)
                        .filter(dat -> m.matches(dat))
                        .count();
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
        Matcher m = new Matcher(rules);
        m.replaceRule("8: 42 | 42 8");
        m.replaceRule("11: 42 31 | 42 11 31");
        long count = Arrays.stream(data)
                        .filter(dat -> m.matches(dat))
                        .count();
        return "" + count;
    }
}