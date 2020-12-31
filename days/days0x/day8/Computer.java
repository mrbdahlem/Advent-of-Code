package days.days0x.day8;

import java.util.*;

public class Computer {
    private int acc = 0;
    private int ip = 0;
    private String[] pgm;
    private int[] visited;

    public Computer(String[] pgm) {
        this.pgm = Arrays.copyOf(pgm, pgm.length);
        visited = new int[pgm.length];
    }

    /**
     * Run the computer's program until it loops.
     * @return true if the program successfully terminates, false if the program looped
     */
    public boolean runToLoop() {
        visited = new int[pgm.length];
        acc = 0;

        while (ip < pgm.length) {
            if (visited[ip] > 0) {
                return false;
            }
            step();
        }
        return true;
    }

    public void run() {
        visited = new int[pgm.length];
        acc = 0;

        while (ip < pgm.length) {
            step();
        }
    }

    public void step() {
        String[] inst = pgm[ip].split("\\s+");
        visited[ip]++;

        switch (inst[0]) {
            case "acc":
                acc += Integer.parseInt(inst[1]);
                ip++;
                break;
            case "jmp":
                ip += Integer.parseInt(inst[1]);
                break;
            case "nop":
                ip++;
                break;
        }
    }

    public int getAcc() {
        return acc;
    }
}