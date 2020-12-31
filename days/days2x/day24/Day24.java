package days.days2x.day24;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day24 extends AocDay {

    private final String[] data;
    private Set<HexPoint> tiles;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day24(String input, PrintStream output) {
        super(input, output);

        data = input.split("\n");
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
        Set<HexPoint> blackTiles = new HashSet<>();

        for (String trail : data) {
            HexPoint p = new HexPoint(trail);

            if (blackTiles.contains(p)) {
                blackTiles.remove(p);
            }
            else {
                blackTiles.add(p);
            }

            // out.print(p + ": ");
            // out.println(color == BLACK ? "B" : "W");
        }

        // out.println(blackTiles.size());
        tiles = blackTiles;
        return "" + blackTiles.size();
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
        Set<HexPoint> currTiles = tiles;

        for (int day = 1; day <= 100; day++) {
            
            Set<HexPoint> nextTiles = new HashSet<>();

            // look at all black tiles
            for (HexPoint p : currTiles) {
                HexPoint[] neighbors = p.neighbors();
                // if a black tile is adjacent to 1 or 2 other black tiles,
                // it remains
                int adjBlack = 0;
                for (HexPoint neighbor : neighbors) {
                    if (currTiles.contains(neighbor)) {
                        adjBlack++;
                    }
                }
                if (adjBlack == 1 || adjBlack == 2) {
                    nextTiles.add(p);
                }

                // look at the tile's neighbors
                for (HexPoint neighbor : neighbors) {
                    // if the neighbor is white (isn't in the currTiles) and
                    // hasn't been flipped yet
                    if (!currTiles.contains(neighbor) &&
                        !nextTiles.contains(neighbor)) {
                        // if white tiles have 2 adjacent black tiles, they
                        // flip to black
                        adjBlack = 0;
                        for (HexPoint nn : neighbor.neighbors()) {
                            if (currTiles.contains(nn)) {
                                adjBlack++;
                            }
                        }
                        if (adjBlack == 2) {
                            nextTiles.add(neighbor);
                        }
                    }
                }
            }

            currTiles = nextTiles;
        }

        // out.println(currTiles.size());
        return "" + currTiles.size();
    }
}