package days.days1x.day15;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day15 extends AocDay {
    
    private final int[] nums;

    private static final int MB = 1024 * 1024; 
    private static final Runtime INSTANCE = Runtime.getRuntime();
    
    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day15(String input, PrintStream output) {
        super(input, output);

        nums = Arrays.stream(input.trim().split(","))
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
        final int turns = 2020;
        out.println(String.format("%,d", turns) + "th number spoken: " + 
            getNthNum(nums, turns));
        return "" + getNthNum(nums, turns);
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
        final int turns = 30_000_000;
        // out.println(String.format("%,d", turns) + "th number spoken: " 
        //     + getNthNum2(nums, turns));
        return "" + getNthNum2(nums, turns);
    }

    private int getNthNum(int[] startingNums, int numTurns) {
        // A map to keep track of the last time a number has been spoken
        // out.println("Used Memory: "
		// 		+ (INSTANCE.totalMemory() - INSTANCE.freeMemory()) / MB);
        Map<Integer, Integer> turnSpoken = new HashMap<>();

        // Initialize the map with the starting nums
        int turn = 1;
        for (; turn <= startingNums.length; turn++) {
            // Remember the turn that the starting number was spoken
            turnSpoken.put(startingNums[turn - 1], turn);
        }

        // loop the given number of turns
        Integer lastNumber = startingNums[startingNums.length - 1];
        int nextNum = 0;
        for (; turn <= numTurns; turn++) {
            // figure out the next number to be spoken by looking at when the last number
            // had been spoken before
            Integer lastSpoken = turnSpoken.getOrDefault(lastNumber, turn - 1);
            if (lastSpoken == null) { // If the last turn was the first time the last num
                nextNum = 0;          // was spoken, the next number is 0
            }
            else { // If the  last number has been spoken at least twice
                nextNum = (turn - 1) - lastSpoken; // the next number is the diff between
            }                                      // the last 2 turns when it was spoken
            turnSpoken.put(lastNumber, turn - 1);

            // "Speak" the next num
            // out.println(nextNum);

            lastNumber = nextNum;
        }
        
        // System.out.println("Used Memory: "
		// 		+ (INSTANCE.totalMemory() - INSTANCE.freeMemory()) / MB);
        out.println(String.format("Unique numbers spoken: %,d", 
            turnSpoken.size()));
        return nextNum;
    }

    // a faster version using a gigantic int array
    private int getNthNum2(int[] startingNums, int numTurns) {
        // System.out.println("Used Memory: "
		// 		+ (INSTANCE.totalMemory() - INSTANCE.freeMemory()) / MB);
		// A map to keep track of the last time a number has been spoken
        int[] turnSpoken = new int[numTurns];
       
        // Initialize the map with the starting nums
        int turn = 1;
        for (; turn <= startingNums.length; turn++) {
            // Remember the turn that the starting number was spoken
            turnSpoken[startingNums[turn - 1]] = turn;
        }

        // loop the given number of turns
        int lastNumber = startingNums[startingNums.length - 1];
        int nextNum = 0;
        for (; turn <= numTurns; turn++) {
            // figure out the next number to be spoken by looking at when the last number
            // had been spoken before
            int lastSpoken = turnSpoken[lastNumber];
            if (lastSpoken == 0) { // If the last turn was the first time the last num
                nextNum = 0;          // was spoken, the next number is 0
            }
            else { // If the  last number has been spoken at least twice
                nextNum = (turn - 1) - lastSpoken; // the next number is the diff between
                
            }                                      // the last 2 turns when it was spoken
            turnSpoken[lastNumber] = turn - 1;

            // "Speak" the next num
            // System.out.println(nextNum);

            lastNumber = nextNum;
        }

        // int maxNum = 0;
        // int numNums = 0;
        // for (int i = 0; i < turnSpoken.length; i++) {
        //     if (turnSpoken[i] != 0) {
        //         maxNum = i;
        //         numNums++;
        //     }
        // }

        // System.out.println("Used Memory: "
		// 		+ (INSTANCE.totalMemory() - INSTANCE.freeMemory()) / MB);
        // out.println(String.format("Unique numbers spoken: %,d", numNums));
        // out.println(String.format("Maximum number spoken: %,d", maxNum));
        return nextNum;
    }
}