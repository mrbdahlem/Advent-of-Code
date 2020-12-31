package days.days2x.day23;

import days.AocDay;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day23 extends AocDay {
    
    private final int[] cupOrder;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day23(String input, PrintStream output) {
        super(input, output);

        cupOrder = Arrays.stream(input.trim().split(""))
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
    @Override
    public String part1() {
        final int MAX = 9;
        final int MOVES = 100;

        Cup cups = new Cup(cupOrder[0]);
        Cup next = cups;
        for (int i = 1; i < cupOrder.length; i++) {
            next = next.addNext(cupOrder[i]);
        }

        int[] fin = playGameArr(cups, MAX, MOVES, false);

        int cup = fin[1];
        String s = "";
        while(cup != 1) {
            s += cup;
            cup = fin[cup];
        }

        return s;
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
        final int MAX = 1_000_000;
        final int MOVES = 10_000_000;

        Cup cups = new Cup(cupOrder[0]);

        Cup next = cups;
        for (int i = 1; i < cupOrder.length; i++) {
            next = next.addNext(cupOrder[i]);
        }
        for (int i = cupOrder.length + 1; i <= MAX; i++) {
            next = next.addNext(i);
        }

        int[] fin = playGameArr(cups, MAX, MOVES, false);

        int cup1 = fin[1];
        int cup2 = fin[cup1];
        
        // out.println(">> " + cup1 + " * " + cup2);
        
        return "" + ((long)cup1 * cup2);
    }

    private int[] playGameArr(Cup cups, int max, int moves,
                                   boolean disp) {
        int picked;
        int[] lookup = new int[(int)cups.size() + 1];
        int current = cups.value;

        Cup cup = cups;
        do {
            lookup[cup.value] = cup.next().value;
            cup = cup.next();
        } while (cup != cups);

        for (int move = 1; move <= moves; move++) {
            if (disp) {
                out.println("-- move " + move + " --");
                out.print("cups: ");
                out.print("(" + current + ") ");
                int next = lookup[current];
                while (next != current) {
                    out.print(next + " ");
                    next = lookup[next];
                }
                out.println();
            }
            else if (move % 1_000_000 == 0) {
                // out.println("-- move " + move + " --");
            }

            picked = lookup[current];
            int next = lookup[lookup[lookup[picked]]];
            lookup[current] = next;

            if (disp) {
                out.print("picked: ");
                int p1 = picked;
                for (int i = 0; i < 3; i++) {
                    out.print(p1 + " ");
                    p1 = lookup[p1];
                }
                out.println();
            }

            int dest = current - 1;
            while (picked == dest ||
                   lookup[picked] == dest ||
                   lookup[lookup[picked]] == dest ||
                   dest <= 0) {
                dest--;
                if (dest <= 0) {
                    dest = max;
                }
            }

            if (disp) {
                out.println("destination: " + dest);
            }

            int after = lookup[dest];
            lookup[dest] = picked;
            lookup[lookup[lookup[picked]]] = after;

            current = lookup[current];
        }

        if (disp) {
            out.println("-- final --");
            out.print("cups: ");
            out.print("(" + cup.getValue() + ") ");
            Cup dispCup = cup.next();
                while (dispCup != cup) {
                    out.print(dispCup.getValue() + " ");
                    dispCup = dispCup.next();
                }
            out.println();
        }

        return lookup;
    }
}