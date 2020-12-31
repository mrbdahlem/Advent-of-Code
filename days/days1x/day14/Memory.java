package days.days1x.day14;

import java.util.*;

public class Memory {
    private long orMask;
    private long andMask;
    private Map<Integer, Long> mem;

    public Memory() {
        orMask = 0;
        andMask = Long.MAX_VALUE;
        mem = new HashMap<>();
    }

    public void setMask(String mask) {
        andMask = Long.parseLong(mask.replace("X", "1"), 2);
        orMask = Long.parseLong(mask.replace("X", "0"), 2);

        // System.out.println("\n&" + Long.toBinaryString(andMask));
        // System.out.println("|" + Long.toBinaryString(orMask));
    }

    public void set(int address, long val) {
        val &= andMask;
        val |= orMask;

        // System.out.println(address + ": " + val);
        mem.put(address, val);
    }

    public long get(int address) {
        return mem.get(address);
    }

    public long sumMemory() {
        long total = 0;
        for (Integer addr : mem.keySet()) {
            total += mem.get(addr);
        }

        return total;
    }
}