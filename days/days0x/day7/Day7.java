package days.days0x.day7;

import days.AocDay;
import java.io.*;
import java.util.*;

public class Day7 extends AocDay {
    
    private final Set<BagRule> rules;

    private static final String TARGET="shiny gold";

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day7(String input, PrintStream output) {
        super(input, output);

        rules = new HashSet<>();
        
        String[] ruleData = input.split("\n");

        for (String rule : ruleData) {
            String[] part = rule.split(" contain ");

            BagRule br = new BagRule();
            br.bag = (part[0].substring(0, part[0].length() - 5));

            br.contents = new ArrayList<>();
            if (!part[1].equals("no other bags.")) {
                for (String sb : part[1].split("bag,|bags,")) {
                    if (sb.endsWith("bag.")) {
                        sb = sb.substring(0, sb.length() - 4);
                    }
                    else if (sb.endsWith("bags.")) {
                        sb = sb.substring(0, sb.length() - 5);
                    }
                    sb = sb.trim();

                    int count = Integer.parseInt(sb.substring(0, sb.indexOf(" ")));
                    String type = sb.substring(sb.indexOf(" ") + 1);

                    br.contents.add(new BagRule.BagCount(count, type));
                }
            }

            rules.add(br);
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
        List<String> bags = new ArrayList<>();  
        bags.add(TARGET);

        Set<String> containers = new HashSet<>();

        canContain(bags, containers);
        return "" + containers.size();
    }

    /**
     * Solve part 2 of the day's challenge using the prepared input (prepare 
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    public String part2() {
        Map<String, BagRule> ruleMap = new HashMap<>();
        for (BagRule rule : rules) {
            ruleMap.put(rule.bag, rule);
        }

        int count = contentsOf(TARGET, ruleMap);
        return "" + count;
    }

    private void canContain(List<String> bags,
                                            Set<String> found) {
        List<String> containers = new ArrayList<>();

        for (String bag : bags) {
            for (BagRule rule : rules) {
                if (rule.canContain(bag) && !found.contains(rule.bag)) {
                    containers.add(rule.bag);
                    found.add(rule.bag);
                }
            }    
        }

        if (!containers.isEmpty()) {
            canContain(containers, found);
        }
    }

    private static int contentsOf(String bagType, Map<String, BagRule> rules) {
        int count = 0;

        List<BagRule.BagCount> contents = rules.get(bagType).contents;
        for (BagRule.BagCount bag : contents) {            
            count = count + (bag.count * (1 + contentsOf(bag.type, rules)));
        }

        return count;
    }
}