package days.days1x.day16;

import days.AocDay;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Day16  extends AocDay {

    private final int[] myTicket;
    private final String[] ruleData;
    private final String[] ticketData;
    private final Field[] fields;

    private final List<int[]> tickets;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day16(String input, PrintStream output) {
        super(input, output);

        String[] data = input.split("\n\n");
        ruleData = data[0].split("\n");
        myTicket = Arrays.stream(data[1].split("\n")[1].split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray();
        
        ticketData = (data[2].split(":\n"))[1].split("\n");
        
        fields = new Field[ruleData.length];

        for (int i = 0; i < ruleData.length; i++) {
            fields[i] = new Field(ruleData[i]);
        }

        tickets = Arrays.stream(ticketData)
                        .map(ticket -> Arrays.stream(ticket.split(","))
                                        .mapToInt(Integer::parseInt)
                                        .toArray())
                        .collect(Collectors.toList());
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

        int total = 0;

        ListIterator<int[]> ti = tickets.listIterator();

        while(ti.hasNext()) {
            int[] ticketValues = ti.next();

            int val = isValid(ticketValues, fields);
            if (val != -1) {
                total += val;
                ti.remove();
            }
        }
        
        out.println("Error rate: " + total);
        out.println("Valid tickets: " + tickets.size() + "/" + 
            ticketData.length);
        return "" + total;
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
        Field[][] slots = new Field[fields.length][];

        // Eliminate the impossible fields for each slot in the ticket by
        // filtering out any that don't match a known valid ticket
        for (int i = 0; i < slots.length; i++) {
            final int pos = i;
            slots[i] = Arrays.stream(fields)
                        .filter(field -> {
                            boolean invalid = false;
                            for (int[] ticket : tickets) {
                                if (!field.matches(ticket[pos])) {
                                    invalid = true;
                                    break;
                                }
                            }
                            return !invalid;
                        })
                        .toArray(size -> new Field[size]);
        }

        Integer[] slotPos = new Integer[slots.length];
        for (int i = 0; i < slots.length; i++) {
            slotPos[i] = i;
        }
        
        int[] sortedSlotPos = Arrays.stream(slotPos)
                                    // .filter(i -> slots[i].length > 1)
                                    .sorted((a, b) -> slots[a].length - slots[b].length)
                                    .mapToInt(Integer::intValue)
                                    .toArray();

        for (int i = 0; i < sortedSlotPos.length; i++) {
            Field[] slot = slots[sortedSlotPos[i]];
            if (slot.length > 1) {
                // out.println("!!!");
                break;
            }

            for (int j = i + 1; j < sortedSlotPos.length; j++) {
                slots[sortedSlotPos[j]] = Arrays.stream(slots[sortedSlotPos[j]])
                                                .filter(field -> field != slot[0])
                                                .toArray(size -> new Field[size]);
            }
        }

        long product = 1;
        for (int i = 0; i < slots.length; i++) {
            if (slots[i][0].getName().contains("departure")) {
                product *= myTicket[i];
            }
        }
        // out.println(product);
        return "" + product;
    }

    /**
     * @return the total of the invalid values in this ticket, -1 if all 
     * values are valid
     */
    private static int isValid(int[] ticketValues, Field[] fields) {
        int total = 0;
        boolean foundBad = false;
        for (int val : ticketValues) {
            boolean good = false;

            for (Field field : fields) {
                if (field.matches(val)) {
                    good = true;
                    break;
                }
            }
        
            if (!good) {
                foundBad = true;
                total += val;
            }
        }

        if (foundBad)
            return total;
        else 
            return -1;
    }
}