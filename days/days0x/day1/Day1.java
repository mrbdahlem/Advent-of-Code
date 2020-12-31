package days.days0x.day1;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day1 extends AocDay {
    
    private final int[] exp;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day1(String input, PrintStream out) {
        super(input, out);
        
        exp = Arrays.stream(input.split("\n"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
    }

    /**
     * Solve part 1 of the day's challenge using the prepared input (prepare
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    public String part1() {
        for (int i = 0; i < exp.length; i++) {
            for (int j = i + 1; j < exp.length; j++) {
                if (exp[i] + exp[j] == 2020) {
                    return "" + (exp[i] * exp[j]);
                }
            }
        }
        return "";
    }

    /**
     * Solve part 2 of the day's challenge using the prepared input (prepare 
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    public String part2() {

        for (int i = 0; i < exp.length; i++) {
            for (int j = i + 1; j < exp.length; j++) {
                for (int k = j + 1; k < exp.length; k++) {
                    if (exp[i] + exp[j] + exp[k] == 2020) {
                        return "" + (exp[i] * exp[j] * exp[k]);
                    }    
                }
            }
        }
        return "";
    }
}