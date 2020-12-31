package days.days2x.day22;

import days.AocDay;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day22 extends AocDay {
    private final List<Integer> p1Deck;
    private final List<Integer> p2Deck;

    private int game;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day22(String input, PrintStream output) {
        super(input, output);

        String[] playerData = input.split("\n\n");

        p1Deck = Arrays.stream(playerData[0].split("\n"))
                        .filter(s -> !s.startsWith("Player"))
                        .mapToInt(Integer::parseInt)
                        .boxed()
                        .collect(Collectors.toList());
                            
        p2Deck = Arrays.stream(playerData[1].split("\n"))
                        .filter(s -> !s.startsWith("Player"))
                        .mapToInt(Integer::parseInt)
                        .boxed()
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
        return combat(new ArrayList<>(p1Deck), 
                      new ArrayList<>(p2Deck),
                      false);
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
        game = 0;
        return "" + recursiveCombat(p1Deck, p2Deck, true, false);
    }

    private String combat(List<Integer> p1Deck,
                        List<Integer> p2Deck,
                        boolean pbp) {
        int round = 1;
        while (p1Deck.size() > 0 && p2Deck.size() > 0) {
            if (pbp) {
                out.println("\n-- Round " + round + " --");
                out.println("Player 1's deck:" + p1Deck);
                out.println("Player 2's deck:" + p2Deck);
            }

            Integer p1 = p1Deck.remove(0);
            Integer p2 = p2Deck.remove(0);

            if (pbp) {
                out.println("Player 1 plays: " + p1);
                out.println("Player 2 plays: " + p2);
            }

            if (p1.compareTo(p2) > 0) {
                if (pbp) {
                    out.println("Player 1 wins the round!");
                }
                p1Deck.add(p1);
                p1Deck.add(p2);
            }
            else if (p2.compareTo(p1) > 0) {
                if (pbp) {
                    out.println("Player 2 wins the round!");
                }
                p2Deck.add(p2);
                p2Deck.add(p1);
            }

            round++;
        }

        // out.println("== Post-game results ==");
        // out.println("Player 1's deck:" + p1Deck);
        // out.println("Player 2's deck:" + p2Deck);

        int score = score(p1Deck.size() > 0 ? p1Deck : p2Deck);
        // out.println("Winner's score: " + score);

        return "" + score;
    }

    private static int score(List<Integer> winner) {
        int numCards = winner.size();
        int score = 0;
        for (int i = 1; i <= numCards; i++) {
            score += i * winner.get(numCards - i);
        }
        return score;
    }

    private int recursiveCombat(List<Integer> p1Deck,
                                List<Integer> p2Deck,
                                boolean originalGame, 
                                boolean pbp) {
                                
        Set<List<List<Integer>>> states = new HashSet<>();

        game++;

        int round = 1;
        while (p1Deck.size() > 0 && p2Deck.size() > 0) {
            List<List<Integer>> state = buildState(p1Deck, p2Deck);
            if (states.contains(state)) {
                if (pbp) {
                    out.println("State already occurred, p1 wins.");
                }
                return 1;
            }
            states.add(state);

            if (pbp) {
                out.println("\n-- Round " + round + 
                                   " (GAME " + game + ") --");
                out.println("Player 1's deck:" + p1Deck);
                out.println("Player 2's deck:" + p2Deck);
            }

            int p1 = p1Deck.remove(0);
            int p2 = p2Deck.remove(0);

            if (pbp) {
                out.println("Player 1 plays: " + p1);
                out.println("Player 2 plays: " + p2);
            }

            int winner;

            if (p1 <= p1Deck.size() && p2 <= p2Deck.size()) {
                if (pbp) {
                    out.println("Playing a sub-game to determine the winner...");
                }
                winner = recursiveCombat(
                            new ArrayList<>(p1Deck.subList(0, p1)),
                            new ArrayList<>(p2Deck.subList(0, p2)),
                            false,
                            pbp);
            }
            else {
                winner = p1 > p2 ? 1 : 2;
            }


            if (winner == 1) {
                if (pbp) {
                    out.println("Player 1 wins the round!");
                }
                p1Deck.add(p1);
                p1Deck.add(p2);
            }
            else if (winner == 2) {
                if (pbp) {
                    out.println("Player 2 wins the round!");
                }
                p2Deck.add(p2);
                p2Deck.add(p1);
            }

            round++;
        }


        // if (originalGame || pbp) {
        //     out.println("== Post-game results ==");
        //     out.println("Player 1's deck:" + p1Deck);
        //     out.println("Player 2's deck:" + p2Deck);
        // }

        if (originalGame) {
            int score = score(p1Deck.size() > 0 ? p1Deck : p2Deck);
            // out.println("Winner's score: " + score);
            return score;
        }

        if (p1Deck.size() > p2Deck.size()) {
            return 1;
        }
        return 2;
    }

    private static List<List<Integer>> buildState(List<Integer> p1, 
                                                  List<Integer> p2) {

        List<List<Integer>> state = new ArrayList<>();
        state.add(new ArrayList<>(p1));
        state.add(new ArrayList<>(p2));
        return state;
    }
}