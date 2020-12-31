package days.days0x.day5;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day5 extends AocDay {
    
    private final int[] seats;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day5(String input, PrintStream output) {
        super(input, output);
        
        seats = Arrays.stream(input.split("\n"))
            .mapToInt(s->parseSeat(s))
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
        int maxSeat = Arrays.stream(seats) 
            .reduce(0, (a, b)->Math.max(a,b));
        
        return "" + maxSeat;
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
        Arrays.sort(seats);   

        int last = seats[0];
        for (int i = 1; i < seats.length; i++) {
            if (seats[i] != (last + 1)) {
                return "" + (last + 1);
            }
            last = seats[i];
        }
        return "";
    }

    private static int parseSeat(String seat) {
        String rowSel = seat.substring(0,7);
        String colSel = seat.substring(7);

        int min = 0;
        int max = 127;
        for (String half : rowSel.split("")) {
            switch (half) {
                case "F":
                    max = min + ((max - min) / 2);
                    break;
                case "B":
                    min = max - ((max - min) / 2);
                    break;

            }
        }

        int row = max;

        min = 0;
        max = 7;
        for (String half : colSel.split("")) {
            switch (half) {
                case "L":
                    max = min + ((max - min) / 2);
                    break;
                case "R":
                    min = max - ((max - min) / 2);
                    break;
            }
        }

        int col = max;

        int id = row * 8 + col;
        return id;
    }
}