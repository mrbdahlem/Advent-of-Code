package days.days0x.day3;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day3 extends AocDay {

    private final String[] map;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day3(String input, PrintStream out) {
        super(input, out);

        map = input.split("\n");
    }

    /**
     * Solve part 1 of the day's challenge using the prepared input (prepare
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    public String part1() {
        int dx = 3;
        int dy = 1;

        int trees = treeCount(map, dx, dy);
        return "" + trees;
    }

    /**
     * Solve part 2 of the day's challenge using the prepared input (prepare 
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    public String part2() {
        int trees1 = treeCount(map, 1, 1);
        int trees2 = treeCount(map, 3, 1);
        int trees3 = treeCount(map, 5, 1);
        int trees4 = treeCount(map, 7, 1);
        int trees5 = treeCount(map, 1, 2);
        return "" + (trees1 * trees2 * trees3 * trees4 * trees5);
    }

    private static int treeCount(String[] map, int dx, int dy) {
        int x = 0;
        int y = 0;
        int trees = 0;

        while (y < map.length - dy) {
            x += dx;
            y += dy;

            if (map[y].charAt(x % map[y].length()) == '#') {
                trees++;
            }
        }

        return trees;
    }
}