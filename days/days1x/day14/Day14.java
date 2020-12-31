package days.days1x.day14;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day14 extends AocDay {
    private String[] pgm;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day14(String input, PrintStream output) {
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
        Memory mem = new Memory();  

        for (String cmd : pgm) {
            String[] parts = cmd.split("=");
            cmd = parts[0].trim();
            String data = parts[1].trim();

            if (cmd.startsWith("mem")) {
                int addr = Integer.parseInt(cmd.substring(cmd.indexOf('[') + 1,
                                                          cmd.indexOf(']')));
                mem.set(addr, Long.parseLong(data));
            }
            else if (cmd.startsWith("mask")) {
                mem.setMask(data);
            }
        }

        // System.out.println("Memory sum: " + mem.sumMemory());
        return "" + mem.sumMemory();
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
        Memory2 mem = new Memory2();

        for (String cmd : pgm) {
            String[] parts = cmd.split("=");
            cmd = parts[0].trim();
            String data = parts[1].trim();

            if (cmd.startsWith("mem")) {
                long addr = Long.parseLong(cmd.substring(cmd.indexOf('[') + 1,
                                                         cmd.indexOf(']')));
                mem.set(addr, Long.parseLong(data));
            }
            else if (cmd.startsWith("mask")) {
                mem.setMask(data);
            }
        }

        // System.out.println("Memory sum: " + mem.sumMemory());
        return "" + mem.sumMemory();
    }
}