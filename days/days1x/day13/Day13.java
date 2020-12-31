package days.days1x.day13;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day13 extends AocDay {
    private String[] data;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day13(String input, PrintStream output) {
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
// The time arriving at the bus depot
        int time = Integer.parseInt(data[0]);

        // Split the busses into an array of their ids, ignoring "x"s
        int[] busses = Arrays.stream(data[1].split(","))
                .filter(ts -> !ts.equals("x"))
                .mapToInt(s -> Integer.parseInt(s))
                .toArray();

        // Find the bus with the minimum wait time after arriving at depot
        int min = Integer.MAX_VALUE;
        int minBus = 0;
        for (int b : busses) {
            // Bus b departs every b ticks

            // Find the next time the bus will arrive, after t
            int nextTime = (int)Math.ceil((double)time / b) * b;

            // If this is the new minimum, record it
            if (nextTime < min) {
                min = nextTime;
                minBus = b;
            }
        }

        // the solution key - the busId * the wait time for it to depart
        return "" + (minBus * (min - time));
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
        // Split the busses into an array of their ids. Missing busses
        // will have id -1.
        int[] busses = Arrays.stream(data[1].split(","))
                .mapToInt(s -> s.equals("x") ? -1 : Integer.parseInt(s))
                .toArray();

        // the departure pattern has busses departing starting at t, with one
        // bus leaving at t+i where i is the bus's position in the data
        // Store this departure offset
        Map<Integer, Integer> busOffset = new HashMap<>();
        for (int i = 0; i < busses.length; i++) {
            if (busses[i] != -1) {
                busOffset.put(busses[i], i);
            }
        }

        // Compact the bus array, removing the missing busses
        busses = Arrays.stream(busses)
                .filter(bus -> bus != -1)
                .toArray();
        // System.out.println(Arrays.toString(busses));

        /*
         * Part 2 requires finding the first timestamp that fulfills the req
         * that bus[0] departs at t, bus[1] departs at t+1, bus[2] departs
         * at t+2, etc. (the departure pattern).
         * Busses depart every bus[i] ticks.
         *
         * This is an iterative solution that for every bus[i]
         * finds the first time (t) that meets the requirement for 
         * busses[0..i] (in the loop, the departure of each bus is at 
         * t + busOffset(bus[i])).
         * 
         * We know that for any set of busses, their departure pattern will
         * repeat at the least common multiple of their ids (since they 
         * each cycle based on their id). So if we can find the first time
         * the set of busses match the departure pattern, they will match it
         * again after lcm(ids) ticks. So we find the first time the set
         * matches the departure pattern. Then we add a new bus by finding
         * the first repetition of the set's departures where the new bus
         * fits the pattern, and note that the pattern will now repeat every
         * lcm(new set of bus ids).
         */
        
        // The first bus will always meet the requirement
        long t = busses[0];
        long interval = busses[0];

        // Iteratively add each bus to the set being considered
        for (int i = 1; i < busses.length; i++) {
            int bus = busses[i];
            // System.out.println((i + 1) + " of " + busses.length + ": " + bus);
            // System.out.println(String.format("interval = %,d", interval));

            // Find the first repetition of the previous set's departures
            // that meets the departure pattern with this bus included
            while ((t + busOffset.get(bus)) % bus != 0) {
                t += interval;
            }

            // find the lcm of all bus ids including this one's...
            // since all busses are prime, just multiply this one in
            interval *= busses[i];
        }
        // System.out.println(String.format("%,d", t));
        return "" + t;
    }
}