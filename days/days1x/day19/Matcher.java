package days.days1x.day19;

import java.util.*;

public class Matcher {
    private Map<Integer, Rule> ruleSet;

    public Matcher(String[] rules) {
        ruleSet = new HashMap<>();

        Arrays.stream(rules)
                .map(rule -> new Rule(rule))
                .forEach(rule -> ruleSet.put(rule.id, rule));
    }

    public boolean matches(String str) {
        return ruleSet.get(0).match(str).stream()
                    .anyMatch(len -> len.equals(str.length()));
    }

    public void replaceRule(String ruleDesc) {
        Rule r = new Rule(ruleDesc);
        ruleSet.put(r.id, r);
    }

    private class Rule {
        private RuleNode[][] options;
        public int id;

        public Rule(String desc) {
            String[] parts = desc.split(": ");
            id = Integer.parseInt(parts[0]);
            parts = parts[1].split(" \\| ");

            options = Arrays.stream(parts)
                        .map(rule -> Arrays.stream(rule.split(" "))
                                .map(part -> new RuleNode(part))
                                .toArray(size -> new RuleNode[size]))
                        .toArray(size -> new RuleNode[size][]);
        }

        /**
         * Determines how much of text this rule can capture
         * @return all possible lengths that this rule can capture from the
         *         beginning of text
         */
        public List<Integer> match(String text) {
            List<Integer> matchlen = new ArrayList<>();

            for (RuleNode[] option : options) {
                List<Integer> earlierLen = new ArrayList<>();
                earlierLen.add(0);

                // Loop through each part of this rule
                for (int i = 0; i < option.length; i++) {
                    List<Integer> captureLen = new ArrayList<>();

                    // Starting at every previous match's length
                    for (int pos : earlierLen) {
                        String rest = text.substring(pos);

                        // See if the next rule part is a literal
                        if (option[i].type == RuleNode.Type.LITERAL) {
                            // and the literal appears
                            if (rest.startsWith(option[i].literal)) {
                                // add the length of the literal capture
                                captureLen.add(pos + option[i].literal.length());
                            }
                        }
                        else /* Option is a SubRule */ {
                            Rule subRule = ruleSet.get(option[i].subId);

                            // Add any captures this rule makes
                            subRule.match(rest).stream()
                                .filter(len -> len > 0)
                                .forEach(capture->{
                                    captureLen.add(pos + capture);
                                    // System.out.println(capture);
                                }); 
                        }
                    }

                    earlierLen.clear();

                    // if the subrule didn't match anything, this option isn't
                    // valid
                    if (captureLen.size() == 0) {
                        break;
                    }

                    // add all of the captures to use in the next part
                    captureLen.stream()
                        .filter(len -> len.compareTo(0) > 0)
                        .forEach(len -> earlierLen.add(len));
                }

                // add any captures made by all parts in this option
                earlierLen.forEach(len -> {
                    if (len > 0) {
                        matchlen.add(len);
                    }
                });
            }

            // return a list of all the possible capture lengths for this rule
            return matchlen;
        }

        public String toString() {
            String s = id + ": ";

            s = s + Arrays.deepToString(options);

            return s;
        }
    }
    
    public static class RuleNode {
        public Type type;
        public String literal;
        public Integer subId;

        public RuleNode(String desc) {
            if (desc.startsWith("\"")) {
                type = Type.LITERAL;
                int end = desc.indexOf("\"", 1);
                literal = desc.substring(1, end);
            }
            else {
                type = Type.RULE;
                subId = Integer.parseInt(desc);
            }
        }

        public String toString() {
            if (type == Type.LITERAL) {
                return "\"" + literal + "\"";
            }
            return "(" + subId + ")";
        }

        public enum Type {
            LITERAL,
            RULE
        }
    }
}