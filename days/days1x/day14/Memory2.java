package days.days1x.day14;

import java.util.*;
import java.util.stream.*;
import java.math.BigInteger;

public class Memory2 {
    private long orMask;
    private String floatMask;
    private Map<Long, Long> mem;

    public Memory2 () {
        orMask = 0;
        floatMask = "";
        mem = new HashMap<>();
    }

    public void setMask(String mask) {
        floatMask = mask.toUpperCase();
        orMask = Long.parseLong(mask.replace("X", "0"), 2);
    }

    public void set(long address, long val) {
        address |= orMask;
        
        for (long addr : getFloatedAddresses(address)) {
            mem.put(addr, val);
        }
    }

    private List<Long> getFloatedAddresses(long address) {
        List<Long> addresses = new ArrayList<>();
        addresses.add(address);
        addresses = getFloatedAddresses(addresses, 0);
        return addresses;
    }

    private List<Long> getFloatedAddresses(List<Long> addresses, int pos) {
        if (pos >= floatMask.length()) {
            return addresses;
        }

        List<Long> newAddresses = new ArrayList<>();
        String filterChar = floatMask.substring(pos, pos+1);
        if (filterChar.equals("X")) {
            long set = 1L << (floatMask.length() - pos - 1);
            long clear = ~(set);

            for (long address : addresses) {
                newAddresses.add(address & clear);
                newAddresses.add(address | set);
            }
        }
        else {
            newAddresses = addresses;
        }

        return getFloatedAddresses(newAddresses, pos+1);
    }

    public long get(long address) {
        return mem.get(address);
    }

    public long sumMemory() {
        long total = mem.entrySet().stream()
            .mapToLong(entry->entry.getValue())
            .reduce(0, (acc, val)->acc+val);

        return total;
    }
}