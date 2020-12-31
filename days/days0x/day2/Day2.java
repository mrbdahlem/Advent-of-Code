package days.days0x.day2;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day2 extends AocDay {
    
    private final String[] pwds;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day2(String input, PrintStream out) {
        super(input, out);

        pwds = input.split("\n");
    }

    /**
     * Solve part 1 of the day's challenge using the prepared input (prepare
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    public String part1() {
        long count = Arrays.stream(pwds)
                        .filter(s->isValid1(s))
                        .count();
        return "" + count;
    }

    /**
     * Solve part 2 of the day's challenge using the prepared input (prepare 
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    public String part2() {
        long count = Arrays.stream(pwds)
                        .filter(s->isValid2(s))
                        .count();
        return "" + count;
    }

    private static boolean isValid1(String pw) {
        String[] parts = pw.split(":");
        String[] rule = parts[0].trim().split(" ");
        String letter = rule[1];
        String[] counts = rule[0].split("-");
        
        int min = Integer.parseInt(counts[0]);
        int max = Integer.parseInt(counts[1]);

        String pwd = parts[1].trim();

        int count = 0;
        for (int i = 0; i < pwd.length(); i++) {
            if (pwd.substring(i, i+1).equals(letter)) {
                count++;
            }
        }

        return count >= min && count <= max;
    }

    private static boolean isValid2(String pw) {
        String[] parts = pw.split(":");
        String[] rule = parts[0].trim().split(" ");
        String letter = rule[1];
        String[] counts = rule[0].split("-");
        
        int first = Integer.parseInt(counts[0]) - 1;
        int second = Integer.parseInt(counts[1]) - 1;

        String pwd = parts[1].trim();

        int count = 0;
        if (pwd.substring(first).startsWith(letter)) {
            count++;
        }
        if (pwd.substring(second).startsWith(letter)) {
            count++;
        }

        return count == 1;
    }
}