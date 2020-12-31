package days.days1x.day12;

import days.AocDay;
import java.io.*;
import java.util.*;
import java.awt.Point;

public class Day12 extends AocDay {
    private String[] moves;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day12(String input, PrintStream output) {
        super(input, output);
        
        moves = input.split("\n");
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
        Boat b = new Boat();
        for (String command : moves) {
            b.move(command);
        }
        // System.out.println("Ended up at " + b.getPos());
        // System.out.println("Manhattan Distance: " + (Math.abs(b.getPos().x) + 
        //                                              Math.abs(b.getPos().y)));
        return "" + (Math.abs(b.getPos().x) + Math.abs(b.getPos().y));
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
        WaypointNav wn = new WaypointNav(new Point(10, -1));
        for (String command : moves) {
            wn.move(command);
            // System.out.println("Moved to:" + wn.getBoatPos());
        }
        Point boat = wn.getBoatPos();
        // System.out.println("Ended up at " + boat);
        // System.out.println("Manhattan Distance: " + (Math.abs(boat.x) + 
        //                                              Math.abs(boat.y)));
        return "" + (Math.abs(boat.x) + Math.abs(boat.y));
    }
}