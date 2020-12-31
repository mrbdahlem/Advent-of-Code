package days.days1x.day11;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day11 extends AocDay {
    private String[][] cells;

    private static final int[] DR = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] DC = {-1, 0, 1, -1, 1, -1, 0, 1};

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day11(String input, PrintStream output) {
        super(input, output);

        String[] data = input.split("\n");

        cells = new String[data.length][];
        for (int i = 0; i < data.length; i++) {
            cells[i] = data[i].split("");
        }
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
        String[][] cur = arrayCopy(cells);
        String[][] last;
    
        int curOccupied = 0;
        int lastOccupied;

        while (true) {

            last = arrayCopy(cur);
            lastOccupied = curOccupied;
            curOccupied = 0;

            // printArray(cur);
            // System.out.println();

            for (int r = 0; r < cells.length; r++) {
                for (int c = 0; c < cells[r].length; c++) {
                    if (cur[r][c].equals(".")) continue; // dont worry about floor

                    int occupied = countOccupied(last, r, c, true);
                    
                    if (occupied == 0) {
                        cur[r][c] = "#";
                    }
                    else if (occupied >= 4) {
                        cur[r][c] = "L";
                    }
                }
            }

            curOccupied = count(cur, "#"); 
            if (curOccupied == lastOccupied && arraysEqual(cur, last)) {
                return "" + curOccupied;
                // System.out.println("Stabilized at " + curOccupied + " seats occupied.");
                // return;
            }
        }
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
        String[][] cur = arrayCopy(cells);
        String[][] last;
    
        int curOccupied = 0;
        int lastOccupied;

        while (true) {
            last = arrayCopy(cur);
            lastOccupied = curOccupied;
            curOccupied = 0;

            // printArray(cur);
            // System.out.println();

            for (int r = 0; r < cells.length; r++) {
                for (int c = 0; c < cells[r].length; c++) {
                    if (cur[r][c].equals(".")) continue; // dont worry about floor

                    int occupied = countOccupied(last, r, c, false);
                    
                    if (occupied == 0) {
                        cur[r][c] = "#";
                    }
                    else if (occupied >= 5) {
                        cur[r][c] = "L";
                    }
                }
            }

            curOccupied = count(cur, "#"); 
            if (curOccupied == lastOccupied && arraysEqual(cur, last)) {
                return "" + curOccupied; 
                // System.out.println("Stabilized at " + curOccupied + " seats occupied.");
                // return;
            }
        }
    }

    private static int countOccupied(String[][] arr, int r, int c,
                                     boolean bounded) {
        int occupied = 0;
        
        for (int i = 0; i <  DR.length; i++) {
            boolean seat = false;
            int d = 1;
            do {
                int dr = DR[i] * d;
                int dc = DC[i] * d;

                if (r + dr < 0 || r + dr >= arr.length) break;
                if (c + dc < 0 || c + dc >= arr[r].length) break;
                
                String cell = arr[r + dr][c + dc];

                if (!cell.equals(".")) seat = true;
                if (cell.equals("#")) occupied++;
                
                d++;
            } while (!bounded && !seat);
        }
        return occupied;
    }

    private static void printArray (String[][] orig) {
        for (int r = 0; r < orig.length; r++) {
            for (int c = 0; c < orig[r].length; c++) {
                System.out.print(orig[r][c]);
            }
            System.out.println();
        }

    }

    private static int count (String[][] orig, String target) {
        int count = 0;

        for (int r = 0; r < orig.length; r++) {
            for (int c = 0; c < orig[r].length; c++) {
                if (orig[r][c].equals(target)) count++;
            }
        }

        return count;
    }
    
    private static String[][] arrayCopy(String[][] orig) {
        String[][] copy = new String[orig.length][orig[0].length];

        for (int r = 0; r < orig.length; r++) {
            for (int c = 0; c < orig[r].length; c++) {
                copy[r][c] = orig[r][c];
            }
        }

        return copy;
    }

    private static boolean arraysEqual(String[][] a, String[][] b) {
        for (int r = 0; r < a.length; r++) {
            for (int c = 0; c < a[r].length; c++) {
                if (!a[r][c].equals(b[r][c])) return false;
            }
        }

        return true;
    }
}