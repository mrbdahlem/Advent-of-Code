package days.days0x.day9;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day9 extends AocDay {
    
    private long[] nums;
    private long val;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day9(String input, PrintStream output) {
        super(input, output);
        
        nums = Arrays.stream(input.split("\n"))
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
        for(int i = 25; i < nums.length; i++) {
            boolean found = false;
            for (int j = i - 25; !found && j < i - 1; j++) {
                for (int k = j + 1; k < i; k++) {
                    if (nums[j] + nums[k] == nums[i]) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                val = nums[i];
                return "" + nums[i];
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
    @Override
    public String part2() {
        int first = -1;
        int last = -1;
        for (int i = 0; i < nums.length - 1; i++) {
            long sum = nums[i];
            last = i + 1;
            for (int j = i + 1;
                 sum < val && j < nums.length;
                 last = j, j++) {                     
                sum = sum + nums[j];
            }
            if (sum == val) {
                first = i;
                break;
            }
        }
        long min = nums[first];
        long max = nums[first];
        for (int i = first + 1; i<= last; i++) {
            min = Math.min(nums[i], min);
            max = Math.max(nums[i], max);
        }

        return "" + (min + max);
    }
}