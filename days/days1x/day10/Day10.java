package days.days1x.day10;

import days.AocDay;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 extends AocDay {

    private final List<Integer> adapters;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day10(String input, PrintStream output) {
        super(input, output);
        
        // break the input into the stack of adapters
        adapters = Arrays.stream(input.split("\n"))
            .map(Integer::parseInt)
            .collect(Collectors.toList());

        // add the initial wall connection at the beginning of the list
        adapters.add(0, 0); 
        // sort the adapters by their joltage
        adapters.sort((a,b)->a-b);
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
        int d1count = 0;
        int d3count = 0;

        // count the deltas between each adapter in the stack
        int curr = 0;
        for (int next : adapters) {
            int dif = next - curr;
            if (dif == 1) {
                d1count++;
            }
            else if (dif == 3) {
                d3count++;
            }
            curr = next;
        }

        d3count++; // count the +3 in the device

        return "" + (d1count * d3count);
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
        Map<Integer, Long> memo = new HashMap<>();
        int maxJolts = adapters.get(adapters.size() - 1) + 3;
        long arrangements = 
                tryStack(adapters, 0, 0, maxJolts, new ArrayList<>(), memo);

        return "" + arrangements;
    }

    private static long tryStack(List<Integer> adapters,
                                 int pos, int prevJolts,
                                 int maxJolts, List<Integer> stack, Map<Integer, Long> memo) {

        // if we have reached the end of the possible adapters,
        // and have a valid jump in jolts, this is a valid stack
        int jolts = adapters.get(pos);
        if (pos == adapters.size() - 1 && prevJolts >= jolts - 3) {
            return 1;
        }
        
        // If the jump in jolts is too great, this is not a valid stack
        if (jolts > prevJolts + 3) {
            return 0;
        }

        // If we have already stacked up from this point on, use our
        // prior knowledge
        if (memo.containsKey(pos)) {
            return memo.get(pos);
        }

        // Print out the current stack
        // stack = new ArrayList<>(stack);
        // stack.add(jolts);
        // System.out.println(stack);
        
        // If this is the first time stacking up from this adapter,
        // count how many ways we can arrange the remaining adapters to 
        // reach the maxJolts from this point
        long count = 0;
        for (int i = 1; i <= 3 && (pos + i) < adapters.size(); i++) {
            count += 
                    tryStack(adapters, pos + i, jolts, maxJolts, stack, memo);
        }

        // Remember the number of arrangements in case we need it later
        memo.put(pos, count);

        return count;
    }
}