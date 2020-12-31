package days.days0x.day4;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day4 extends AocDay {
    
    private final List<Passport> passports;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day4(String input, PrintStream out) {
        super(input, out);

        passports = new ArrayList<>();

        String[] lines = input.split("\n");
        
        String passport = "";
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].trim().isEmpty()) {
                if (!passport.isEmpty()) {
                    passports.add(new Passport(passport.trim()));
                    passport = "";
                }
            }
            else {
                passport = passport + lines[i] + " ";
            }
        }

        if (!passport.isEmpty()) {
            passports.add(new Passport(passport.trim()));
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
        int count = 0;
        for (Passport p : passports) {
            if (p.isComplete(true)) {
                count++;
            }            
        }
        return "" + count;
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
        int count = 0;  

        for (Passport p : passports) {
            if (p.isValid(true)) {
                count++;
            }            
        }
        return "" + count;
    }
}