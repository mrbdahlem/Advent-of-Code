package days.days0x.day8;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day8 extends AocDay {
    
    private final String[] pgm;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day8(String input, PrintStream output) {
        super(input, output);

        pgm = input.split("\n");
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
        Computer comp = new Computer(pgm);
        comp.runToLoop();
        return "" + comp.getAcc();
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
        // Loop through all instructions in the program
        for (int i = 0; i < pgm.length;) {
            String[] alteredPgm = Arrays.copyOf(pgm, pgm.length);

            // skip over any instructions that aren't nop or jmp
            while (!(alteredPgm[i].startsWith("nop") || alteredPgm[i].startsWith("jmp"))) {
                i++;
            }

            // If the next instruction is a nop, make it a jmp.
            // If it's a jmp, make it a nop.
            if (alteredPgm[i].startsWith("nop")) {
                alteredPgm[i] = alteredPgm[i].replace("nop", "jmp");
            }
            else {
                alteredPgm[i] = alteredPgm[i].replace("jmp", "nop");
            }
            
            // Try the altered program
            Computer comp = new Computer(alteredPgm);
            if (comp.runToLoop()) {
                return "" + comp.getAcc();
            }

            // Go to the next instruction
            i++;
        }
        return "";
    }
}