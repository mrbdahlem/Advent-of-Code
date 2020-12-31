package days.days2x.day25;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day25 extends AocDay {

    private final long[] data;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day25(String input, PrintStream output) {
        super(input, output);

        data = Arrays.stream((input).split("\n"))
                        .mapToLong(Long::parseLong)
                        .toArray();
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
        Encryptor key = new Encryptor(data[0]);
        Encryptor door = new Encryptor(data[1]);

        long keyKey = key.getKey(door.getLoopSize());
        // long doorKey = door.getKey(key.getLoopSize());

        // out.println(keyKey + ": " + (keyKey == doorKey));
        return "" + keyKey;
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

        return "";
    }
}