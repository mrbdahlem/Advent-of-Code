package days.days2x.day21;

import days.AocDay;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day21 extends AocDay {

    private final String[] recipes;

    private Map<String, Set<String>> translations;

    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day21(String input, PrintStream output) {
        super(input, output);

        recipes = input.split("\n");
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
        // create a mapping from known allergens to possible words
        translations = new HashMap<>();
        List<String> allWords = new ArrayList<>();

        // Build the mapping using each recipe
        for (String recipe : recipes) {
            // break the recipe into its ingredients and allergens
            String[] halves = recipe.split(" \\(contains ");

            // get the set of ingredients from the first part of the recipe
            List<String> untranslated = Arrays.asList(halves[0].split(" "));
            allWords.addAll(untranslated);

            // split the second part of the recipe into the allergens it contains
            String[] allergens = halves[1]
                                    .substring(0, halves[1].length() - 1)
                                    .trim()
                                    .split(", ");

            // For every allergen in this recipe
            for (String allergen : allergens) {
                // Check if there are any words that might already equate to this allergen
                Set<String> words = translations.get(allergen);
                // If there aren't already any equivalances
                if (words == null) {
                    // Store the list from this recipe as possibilities
                    words = new HashSet<>(untranslated);
                    translations.put(allergen, words);
                }
                else {
                    // Otherwise, remove any old possibilities that aren't in this list
                    words.retainAll(untranslated);
                }
            }
        }

        // Sort the allergens by number of possible translations
        List<Map.Entry<String, Set<String>>> sortedAllergens =
            translations.entrySet().stream()
                .sorted((a, b) -> a.getValue().size() - b.getValue().size())
                .collect(Collectors.toList());

        // Starting with the word with the fewest possible translations, 
        // looping through all translations
        while(sortedAllergens.size() > 0) {
            // Get the translation
            Map.Entry<String, Set<String>> a1 = sortedAllergens.get(0);
            // And all possible (hopefully only 1) ingredient names
            Set<String> ingredients = a1.getValue();

            // Loop through all other allergen translations
            sortedAllergens = sortedAllergens.stream()
                                .filter(b -> a1 != b)
                                // remove the ingredient name(s) from the other allergens
                                .map(a -> {
                                    a.getValue().removeAll(ingredients);
                                    return a;
                                })
                                // resort the remaining allergens by number of 
                                // translations
                                .sorted((a, b) -> a.getValue().size() -
                                                  b.getValue().size())
                                .collect(Collectors.toList());
        }

        // remove all of the translated allergen words from the list of
        // all ingredients
        for (Set<String> allergens : translations.values()) {
            allWords.removeAll(allergens);
        }
        // out.println(allWords.size() + " words are not allergens");

        return "" + allWords.size();
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
        // Create a new list holding all possible translations for the 
        // allergens
        List<String> allergens = new ArrayList<>();
        translations.entrySet().stream()
                            // with the English allergen names sorted alphabetically
                            .sorted((a, b)->a.getKey().compareTo(b.getKey()))
                            // storing the allergen translations
                            .map(a->a.getValue())
                            .forEach(a->a.stream()
                                        .forEach(d->allergens.add(d)));

        // Print out the list of allergen translations separated by commas
        String s = "";
        for (String name : allergens) {
            if (!s.isEmpty()) {
                s = s + ",";
            }
            s = s + name;
        }
        // out.println("Sorted allergens: " + s);

        return s;
    }
}